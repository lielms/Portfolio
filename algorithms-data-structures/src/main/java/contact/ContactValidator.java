package contact;

// Centralize validation logic.
// Keeps validation rules consistent across constructors and setters.
// Record validation failures using ValidationMetrics.
//
// Rules:
//  ID/FirstName/LastName: not null and must be <= 10
//  Phone: non-null, digits only, and length == 10
//  Address: non-null, length <= 30
public class ContactValidator {
    private ContactValidator() {}

    public static void validateId(String contactId) {
        if (contactId == null) {
            fail("contactId", "Value cannot be null");
        }
        if (contactId.length() > 10) {
            fail("contactId", "Length must be <= 10");
        }
    }
    public static void validateFirstName(String firstName) {
        if (firstName == null) {
            fail("firstName", "Value cannot be null");
        }
        if (firstName.length() > 10) {
            fail("firstName", "Length must be <= 10");
        }
    }
    public static void validateLastName(String lastName) {
        if (lastName == null) {
            fail("lastName", "Value cannot be null");
        }
        if (lastName.length() > 10) {
            fail("lastName", "Length must be <= 10");
        }
    }
    public static void validatePhone(String phoneNumber) {
        if (phoneNumber == null) {
            fail("phoneNumber", "Value cannot be null");
        }
        if (phoneNumber.length() != 10) {
            fail("phoneNumber", "Length must be 10");
        }
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (!Character.isDigit(phoneNumber.charAt(i))) {
                fail("phoneNumber", "Only digits allowed");
            }
        }
    }
    public static void validateAddress(String address) {
        if (address == null) {
            fail("address", "Value cannot be null");
        }
        if (address.length() > 30) {
            fail("address", "Length must be <= 30");
        }
    }
    private static void fail(String field, String reason) {
        ValidationMetrics.recordFailure(field, reason);
        throw new ValidationException(field, reason);
    }
}
