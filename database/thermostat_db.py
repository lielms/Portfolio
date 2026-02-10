#
# thermostat_db.py - MongoDB integration module for Thermostat
#
# This module handles all database operations including connection
# management, data logging, and error handling to ensure the
# thermostat continues operating even if the database is unavailable.
#

from pymongo import MongoClient
from pymongo.errors import ConnectionFailure, ServerSelectionTimeoutError
from datetime import datetime
from threading import Thread
import queue

# Database manager for thermostat data logging.
# Handles MongoDB connection and asynchronous data insertion.
class ThermostatDB:
    # Initialize the database connection.
    def __init__(self, connection_string="mongodb://192.168.1.9:27017/",
                 db_name="ThermostatDB",
                 collection_name="History",
                 debug=False):

        # Connection parameters
        self.connection_string = connection_string
        self.db_name = db_name
        self.collection_name = collection_name
        self.debug = debug

        # MongnoDB client handles
        self.client = None
        self.db = None
        self.collection = None
        self.connected = False

        # Queue used to buffer insert requests so the main program never blocks
        self.insert_queue = queue.Queue()

        # Background worker
        self.worker_running = False
        self.worker_thread = None

        # Attempt initial connection
        self._connect()

        # Start background worker for async inserts
        if self.connected:
            self._start_worker()

    # Establish connection with mongoDB
    def _connect(self):
        try:
            # Create client
            self.client = MongoClient(
                self.connection_string,
                serverSelectionTimeoutMS=2000
            )

            # Tests connection to server
            self.client.admin.command('ping')

            # If successful, get database and collection
            self.db = self.client[self.db_name]
            self.collection = self.db[self.collection_name]
            self.connected = True

            if self.debug:
                print(f"[DB] Successfully connected to MongoDB: {self.db_name}.{self.collection_name}")

        #  Catch server offline or unreachable
        except (ConnectionFailure, ServerSelectionTimeoutError) as e:
            self.connected = False
            if self.debug:
                print(f"[DB] Database unavailable, continuing in local-only mode: {e}")

        # Catch unexpected errors
        except Exception as e:
            self.connected = False
            if self.debug:
                print(f"[DB] Unexpected error connecting to database: {e}")

    # Start background worker thread for asynchronous inserts.
    def _start_worker(self):
        if not self.worker_running:
            self.worker_running = True
            self.worker_thread = Thread(target=self._insert_worker, daemon=True)
            self.worker_thread.start()
            if self.debug:
                print("[DB] Started background insert worker")

    # Background worker that processes insert queue
    def _insert_worker(self):
        while self.worker_running:
            try:
                # Get document from queue
                document = self.insert_queue.get(timeout=1)
                 # Stop worker if no document
                if document is None:
                    break

                # Only attempt inserts if the database is currently connected
                if self.connected:
                    try:
                        self.collection.insert_one(document)
                        # Success
                        if self.debug:
                            print(f"[DB] Logged: {document}")
                    # Failed to insert
                    except Exception as e:
                        if self.debug:
                            print(f"[DB] Insert failed: {e}")
                        # Try to reconnect
                        self._connect()

                # Mark task as done
                self.insert_queue.task_done()

            # No document arrived within the timeout window
            except queue.Empty:
                # Loop and try again
                continue
            except Exception as e:
                # Catch unexpected errors
                if self.debug:
                    print(f"[DB] Worker error: {e}")

    # Log thermostat state to database asynchronously
    # Args:
    #     state: off/heat/cool
    #     temperature: Current temperature in Fahrenheit
    #     setpoint: Target temperature
    #     event_type: periodic, state_change, setpoint_change
    def log_state(self, state, temperature, setpoint, event_type="periodic"):
        # Skip if not connected
        if not self.connected:
            return

        # Create document with timestamp
        document = {
            "timestamp": datetime.now(),
            "state": state,
            "temperature": float(temperature),
            "setpoint": int(setpoint),
            "event_type": event_type
        }

        # Add to queue for async processing
        try:
            self.insert_queue.put_nowait(document)
        except queue.Full:
            if self.debug:
                print("[DB] Insert queue full, skipping log entry")

    # Log event with event descriptions and additional fields
    # to include in the document
    def log_event(self, event_description, **kwargs):
        if not self.connected:
            return

        document = {
            "timestamp": datetime.now(),
            "event": event_description,
            **kwargs
        }

        # Attempt to insert document
        try:
            self.insert_queue.put_nowait(document)
        except queue.Full:
            if self.debug:
                print("[DB] Insert queue full, skipping event log")

    # Retrieve recent history entries
    def get_recent_history(self, limit=100):
        if not self.connected:
            return []

        # List of history documents or empty list if not connected
        try:
            return list(self.collection.find().sort("timestamp", -1).limit(limit))
        except Exception as e:
            if self.debug:
                print(f"[DB] Error retrieving history: {e}")
            return []

    # Get  statistics for the specified time period
    def get_statistics(self, hours=24):
        if not self.connected:
            return None

        try:
            from datetime import timedelta
            # Compute the earliest timestamp in the query
            cutoff_time = datetime.now() - timedelta(hours=hours)

            # MongoDB aggregation pipeline
            pipeline = [
                # Keep only documents whose timestamp is newer than cutoff_time
                {"$match": {"timestamp": {"$gte": cutoff_time}}},
                # Compute aggregate statistics across the filtered documents
                {"$group": {
                    "_id": None,
                    "avg_temp": {"$avg": "$temperature"},
                    "min_temp": {"$min": "$temperature"},
                    "max_temp": {"$max": "$temperature"},
                    "count": {"$sum": 1}
                }}
            ]

            # Return dictionary with statistics or none if not connected
            result = list(self.collection.aggregate(pipeline))
            return result[0] if result else None

        except Exception as e:
            if self.debug:
                print(f"[DB] Error calculating statistics: {e}")
            return None
    # Close database connection and stop worker thread
    def close(self):
        # Stop worker thread
        if self.worker_running:
            self.worker_running = False
            self.insert_queue.put(None)
            if self.worker_thread:
                self.worker_thread.join(timeout=2)

        # Close MongoDB connection
        if self.client:
            self.client.close()
            if self.debug:
                print("[DB] Database connection closed")