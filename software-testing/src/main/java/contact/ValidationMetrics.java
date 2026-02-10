package contact;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

//
// Tracks validation failures across the application.
//
// Provide a counter for how many validation failures occurred and
// logs the field and reason
//
// Uses AtomicInteger so increments are thread-safe if
// validation is called from multiple threads.
//

public class ValidationMetrics {

    // Count of validation failures with AtomicInteger
    // to maintain concurrency
    private static final AtomicInteger failureCount = new AtomicInteger();

    // Logger for recording validation failures
    private static final Logger LOGGER =
            Logger.getLogger(ValidationMetrics.class.getName());

    // Private constructor to prevent instantiation

    private ValidationMetrics() {}

    public static void recordFailure(String field, String reason) {
        // Increment counter
        failureCount.incrementAndGet();
        // Log at warning to highlight invalid input
        LOGGER.warning(() -> "Validation failure on " + field + ": " + reason);
    }

    public static int getFailureCount() {
        return failureCount.get();
    }

    // Reset counter to zero
    public static void reset() {
        failureCount.set(0);
    }
}
