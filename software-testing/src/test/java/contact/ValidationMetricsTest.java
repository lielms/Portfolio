package contact;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Unit tests for ValidationMetrics to ensure failure tracking
// and reset logic function correctly.

public class ValidationMetricsTest {

    // Ensures each test starts with a clean counter value
    @BeforeEach
    void resetMetrics() {
        ValidationMetrics.reset();
    }

    // Verifies recordFailure increments the counter by 1.
    @Test
    void recordFailureIncrementsCount() {
        assertEquals(0, ValidationMetrics.getFailureCount());
        ValidationMetrics.recordFailure("field", "reason");
        assertEquals(1, ValidationMetrics.getFailureCount());
    }


    // Verifies reset clears the counter after multiple failures.
    @Test
    void resetClearsCount() {
        ValidationMetrics.recordFailure("field1", "reason");
        ValidationMetrics.recordFailure("field2", "reason");
        assertEquals(2, ValidationMetrics.getFailureCount());
        ValidationMetrics.reset();
        assertEquals(0, ValidationMetrics.getFailureCount());
    }
}
