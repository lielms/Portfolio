// Author: Liel Simon
// Date: 11/16/2024

package contact;
// import testing and assertion libraries
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

// Test class for validating Contact class functionality
class ContactTest {
	@Test
	// Tests creation of valid object
	void testContact() {
		// Create valid contact
		Contact contact = new Contact("15555", "Bob", "Smith", "1234567890", "8850 West Mayfield");
		// Validates fields by asserting each field matches expected value
		assertTrue(contact.getContactId().equals("15555"));
		assertTrue(contact.getFirstName().equals("Bob"));
		assertTrue(contact.getLastName().equals("Smith"));
		assertTrue(contact.getPhoneNumber().equals("1234567890"));
		assertTrue(contact.getAddress().equals("8850 West Mayfield"));
	}
	
	@Test
	void testIfFieldsTooLong() {
		// Creates and tests contact with Id field that is too long
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("15555555555", "Bob", "Smith", "1234567890", "8850 West Mayfield");
		}, "Expected IllegalArgumentException when contact ID is too long");
		// Creates and tests contact with First Name that is too long
		Assertions.assertThrows(IllegalArgumentException.class,() -> {
			new Contact("15555", "Bobbbybobington", "Smith", "1234567890", "8850 West Mayfield");
		}, "Expected IllegalArgumentException when first name is too long");
		// Creates and tests contact with Last Name that is too long
		Assertions.assertThrows(IllegalArgumentException.class,() -> {
			new Contact("15555", "Bob", "Smithington", "1234567890", "8850 West Mayfield");
		}, "Expected IllegalArgumentException when last name is too long");
		// Creates and tests contact with Address that is too long
		Assertions.assertThrows(IllegalArgumentException.class,() -> {
			new Contact("15555", "Bob", "Smith", "1234567890", "8850 West Mayfield0000000000000");
		}, "Expected IllegalArgumentException when address is too long");
	}
	@Test
	void testPhoneNumberValidation() {
		// Creates and tests phone number less than 10 digits
		Assertions.assertThrows(IllegalArgumentException.class,() -> {
			new Contact("15555", "Bob", "Smith", "12345", "8850 West Mayfield");
		});
		// Creates and tests phone number greater than 10 digits
		Assertions.assertThrows(IllegalArgumentException.class,() -> {
			new Contact("15555", "Bob", "Smith", "12345678900", "8850 West Mayfield");
		});
		// Creates and tests phone number containing non-digits
		Assertions.assertThrows(IllegalArgumentException.class,() -> {
			new Contact("15555", "Bob", "Smith", "123efg4567", "8850 West Mayfield");
		});
	}
}

