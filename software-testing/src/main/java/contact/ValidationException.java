package contact;

// Custom exceptions for validation failures.
// Provides field and reason for debugging.
public class ValidationException extends RuntimeException {

    // field that failed to validate
    private final String field;

    // Reason for validation failure
    private final String reason;

    // Create new validation exception

    public ValidationException(String field, String reason) {
        super(field + ": " + reason);
        this.field = field;
        this.reason = reason;
    }
    public String getField() {
        return field;
    }
    public String getReason() {
        return reason;
    }
}
