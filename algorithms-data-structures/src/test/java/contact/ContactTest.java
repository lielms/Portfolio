// Author: Liel Simon
// Date: 11/16/2024

package contact;
// import testing and assertion libraries
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

// Test class for validating Contact class functionality
class ContactTest {

	// Tests creation of valid object
	@Test
	void testContact() {
		// Create valid contact
		Contact contact = new Contact("15555", "Bob", "Smith",
				"1234567890", "8850 West Mayfield");
		// Validates fields by asserting each field matches expected value
		assertEquals("15555", contact.getContactId());
		assertEquals("Bob", contact.getFirstName());
		assertEquals("Smith", contact.getLastName());
		assertEquals("1234567890", contact.getPhoneNumber());
		assertEquals("8850 West Mayfield", contact.getAddress());
	}

	// Verifies invalid IDs are rejected with correct field and reason
	@ParameterizedTest
	@MethodSource("invalidIds")
	void rejectsInvalidIds(String id, String reason) {
		ValidationException ex = assertThrows(
				ValidationException.class,
				() -> new Contact(id, "Bob", "Smith",
						"1234567890", "Addr")
		);
		assertEquals("contactId", ex.getField());
		assertEquals(reason, ex.getReason());
	}

	// Verifies invalid first names are rejected with correct field and reason
	@ParameterizedTest
	@MethodSource("invalidFirstNames")
	void rejectsInvalidFirstNames(String firstName, String reason) {
		ValidationException ex = assertThrows(
				ValidationException.class,
				() -> new Contact("1", firstName, "Smith",
						"1234567890", "Addr")
		);
		assertEquals("firstName", ex.getField());
		assertEquals(reason, ex.getReason());
	}

	// Verifies invalid last names are rejected with correct field and reason
	@ParameterizedTest
	@MethodSource("invalidLastNames")
	void rejectsInvalidLastNames(String lastName, String reason) {
		ValidationException ex = assertThrows(
				ValidationException.class,
				() -> new Contact("1", "Bob", lastName,
						"1234567890", "Addr")
		);
		assertEquals("lastName", ex.getField());
		assertEquals(reason, ex.getReason());
	}

	// Verifies invalid phone numbers are rejected with correct field and reason
	@ParameterizedTest
	@MethodSource("invalidPhones")
	void rejectsInvalidPhones(String phone, String reason) {
		ValidationException ex = assertThrows(
				ValidationException.class,
				() -> new Contact("1", "Bob", "Smith", phone, "Addr")
		);
		assertEquals("phoneNumber", ex.getField());
		assertEquals(reason, ex.getReason());
	}

	// Verifies invalid addresses are rejected with correct field and reason
	@ParameterizedTest
	@MethodSource("invalidAddresses")
	void rejectsInvalidAddresses(String address, String reason) {
		ValidationException ex = assertThrows(
				ValidationException.class,
				() -> new Contact("1", "Bob", "Smith", "1234567890", address)
		);
		assertEquals("address", ex.getField());
		assertEquals(reason, ex.getReason());
	}

	// Boundary test to confirm that maximum allowed lengths are accepted
	@ParameterizedTest
	@MethodSource("boundaryValidContacts")
	void acceptsBoundaryValues(String id, String firstName, String lastName,
							   String phone, String address) {
		assertDoesNotThrow(() -> new Contact(id, firstName, lastName, phone, address));
	}

	// Supplies invalid ID test cases and expected reasons
	static Stream<Arguments> invalidIds() {
		return Stream.of(
				Arguments.of(null, "Value cannot be null"),
				Arguments.of("12345678901", "Length must be <= 10")
		);
	}

	// Supplies invalid first name test cases and expected reasons
	static Stream<Arguments> invalidFirstNames() {
		return Stream.of(
				Arguments.of(null, "Value cannot be null"),
				Arguments.of("Bobbbybobington", "Length must be <= 10")
		);
	}

	// Supplies invalid last name test cases and expected reasons
	static Stream<Arguments> invalidLastNames() {
		return Stream.of(
				Arguments.of(null, "Value cannot be null"),
				Arguments.of("Smithington", "Length must be <= 10")
		);
	}

	// Supplies invalid phone test cases and expected reasons
	static Stream<Arguments> invalidPhones() {
		return Stream.of(
				Arguments.of(null, "Value cannot be null"),
				Arguments.of("12345", "Length must be 10"),
				Arguments.of("12345678900", "Length must be 10"),
				Arguments.of("123efg4567", "Only digits allowed")
		);
	}

	// Supplies invalid address test cases and expected reasons
	static Stream<Arguments> invalidAddresses() {
		return Stream.of(
				Arguments.of(null, "Value cannot be null"),
				Arguments.of("8850 West Mayfield0000000000000", "Length must be <= 30")
		);
	}

	// Supplies boundary-valid contacts.
	static Stream<Arguments> boundaryValidContacts() {
		// Exactly 10 characters for ID boundary.
		String tenChars = "ABCDEFGHIJ";

		// Exactly 10 digits for phone boundary.
		String phone = "1234567890";

		// Exactly 30 characters for address boundary.
		String addr30 = "123456789012345678901234567890";
		return Stream.of(
				Arguments.of(tenChars, "FirstName", "LastName",
						phone, addr30),
				Arguments.of("ID99999999", "MaxLenName", "MaxLenLast",
						phone, addr30)
		);
	}
}