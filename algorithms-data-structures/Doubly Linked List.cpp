//============================================================================
// Name        : LinkedList.cpp
// Author      : Liel Simon
// Version     : 2.0
// Copyright   : Copyright © 2023 SNHU COCE
// Description : Lab 3-2 Lists and Searching
//===========================================================================
// Bid Management System with Doubly Linked List
// Provides efficient insertion, deletion, search, and sorting of bid data
//===========================================================================
#include <algorithm>
#include <iostream>

#include <time.h>

#include "CSVparser.hpp"
#include "DoublyLinkedList.hpp"

using namespace std;

//============================================================================
// Global definitions visible to all methods and classes
//============================================================================

/*
 * Convert string to double, stripping specified character
 * Time Complexity: O(n) where n is string length
 * Used primarily to remove dollar signs from currency strings
 * 
 * credit: http://stackoverflow.com/a/24875936
 * 
 * @param str The string to convert
 * @param ch The character to strip out
 */
double strToDouble(string str, char ch) {
    str.erase(remove(str.begin(), str.end(), ch), str.end());
    return atof(str.c_str());
}

// define a structure to hold bid information
struct Bid {
    string bidId; // unique identifier
    string title;
    string fund;
    double amount;
    Bid() : amount(0.0) {}
};


//============================================================================
// Static methods used for testing
//============================================================================

/**
 * Display the bid information
 * Output bid details
 * 
 * @param bid struct containing the bid info
 */
void displayBid(Bid bid) {
    cout << bid.bidId << ": " << bid.title << " | " 
        << bid.amount << " | " << bid.fund << endl;
}

/**
 * Prompt user for bid information
 *
 * @return Bid struct containing the bid info
 */
Bid getBid() {
    Bid bid;

    cout << "Enter Id: ";
    cin.ignore();
    getline(cin, bid.bidId);

    cout << "Enter title: ";
    getline(cin, bid.title);

    cout << "Enter fund: ";
    cin >> bid.fund;

    cout << "Enter amount: ";
    cin.ignore();
    string strAmount;
    getline(cin, strAmount);
    // Convert amount string to double and remove any dollar signs
    bid.amount = strToDouble(strAmount, '$');

    return bid;
}

/**
 * Load a CSV file containing bids into a DoublyLinkedList
 * Parses CSV rows and creates Bid objects and appends them to the list
 */
void loadBids(string csvPath, DoublyLinkedList<Bid>* list) {
    cout << "Loading CSV file " << csvPath << endl;

    // initialize the CSV Parser
    csv::Parser file = csv::Parser(csvPath);

    try {
        // loop to read rows of a CSV file
        for (size_t i = 0; i < file.rowCount(); i++) {

            // initialize a bid using data from current row (i)
            Bid bid;
            bid.bidId = file[i][1];
            bid.title = file[i][0];
            bid.fund = file[i][8];
            bid.amount = strToDouble(file[i][4], '$');

            // add this bid to the end of the list
            list->Append(bid);
        }
    }
    // Handle CSV parsing errors
    catch (csv::Error& e) {
        std::cerr << e.what() << std::endl;
    }
}

/**
 * Main:
 * Provides menu for bid management operations
 *
 * @param arg[1] path to CSV file to load from (optional)
 * @param arg[2] the bid Id to use when searching the list (optional)
 */
int main(int argc, char* argv[]) {

    // process command line arguments
    string csvPath, bidKey;
    switch (argc) {
    case 2:
        csvPath = argv[1];
        bidKey = "98109";
        break;
    case 3:
        csvPath = argv[1];
        bidKey = argv[2];
        break;
    default:
        csvPath = "eBid_Monthly_Sales.csv";
        bidKey = "98109";
    }

    // Variable for timing operations
    clock_t ticks;

    // Create the doubly linked list to hold bids
    DoublyLinkedList<Bid> bidList;

    Bid bid;
    
    // Main menu loop
    int choice = 0;
    while (choice != 9) {
        cout << "Menu:" << endl;
        cout << "  1. Enter a Bid" << endl;
        cout << "  2. Load Bids" << endl;
        cout << "  3. Display All Bids" << endl;
        cout << "  4. Find Bid" << endl;
        cout << "  5. Remove Bid" << endl;
        cout << "  6. Sort Bids by ID" << endl;
        cout << "  7. Sort Bids by Amount" << endl;
        cout << "  9. Exit" << endl;
        cout << "Enter choice: ";
        cin >> choice;

        switch (choice) {
        case 1:
            // Get bid information from user and add to list
            bid = getBid();
            bidList.Append(bid);
            displayBid(bid);
            break;

        case 2:
            // Load bids from CSV file
            // Records start time, loads bids, then calculates elapsed time
            ticks = clock();
            loadBids(csvPath, &bidList);
            cout << bidList.Size() << " bids read" << endl;

            // current clock ticks minus starting clock ticks
            ticks = clock() - ticks;
            cout << "time: " << ticks << " milliseconds" << endl;
            cout << "time: " << ticks * 1.0 / CLOCKS_PER_SEC << " seconds" << endl;
            break;

        case 3:
            // Display all bids using lambda function with ForEach
            cout << "\nAll Bids:" << endl;
            bidList.ForEach([](const Bid& b) {
                displayBid(b);
                });
            break;

        case 4:
            // Search for specific bid by ID
            // Returns pointer to matching bid or nullptr if not found
            ticks = clock();
            {
                Bid* foundBid = bidList.Search([&bidKey](const Bid& b) {
                    return b.bidId == bidKey;
                    });

                ticks = clock() - ticks;

                if (foundBid != nullptr) {
                    displayBid(*foundBid);
                }
                else {
                    cout << "Bid Id " << bidKey << " not found." << endl;
                }
            }

            cout << "time: " << ticks << " clock ticks" << endl;
            cout << "time: " << ticks * 1.0 / CLOCKS_PER_SEC << " seconds" << endl;
            break;

        case 5:
            // Remove bid by ID
            if (bidList.Remove([&bidKey](const Bid& b) {
                return b.bidId == bidKey;
                })) {
                cout << "Bid " << bidKey << " removed." << endl;
            }
            else {
                cout << "Bid " << bidKey << " not found." << endl;
            }
            break;

        case 6:
            // Sort bids by ID using merge sort algorithm
            // Merge sort provides O(n log n) performance
            ticks = clock();
            bidList.Sort([](const Bid& a, const Bid& b) {
                return a.bidId < b.bidId;
                });
            ticks = clock() - ticks;

            cout << "Bids sorted by ID" << endl;
            cout << "time: " << ticks << " clock ticks" << endl;
            cout << "time: " << ticks * 1.0 / CLOCKS_PER_SEC << " seconds" << endl;
            break;

        case 7:
            // Sort bids by amount using merge sort algorithm
            // Lambda comparator defines order by amount
            ticks = clock();
            bidList.Sort([](const Bid& a, const Bid& b) {
                return a.amount < b.amount;
                });
            ticks = clock() - ticks;

            cout << "Bids sorted by amount (O(n log n))" << endl;
            cout << "time: " << ticks << " clock ticks" << endl;
            cout << "time: " << ticks * 1.0 / CLOCKS_PER_SEC << " seconds" << endl;
            break;
        }
    }

    cout << "Good bye." << endl;

    return 0;
}
