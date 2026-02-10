// Author: Liel Simon
// Date: 11/16/2024

package contact;
//import testing and assertion libraries
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Unit tests for ContactService
// Covers add, retrieve by ID, update, delete, and enforce capacity limit
class ContactServiceTest {

	// Declares an instance variable for the ContactService class
	private ContactService contactService;

	// Sets up a new instance of ContactService before each test method is run
	@BeforeEach
	void setUp() {
		contactService = new ContactService();
	}
	
	// Test for adding a single contact and increments repository size
	@Test
	void testAddingSingleContact() {
		// create contact object
		Contact contact = new Contact("15555", "Bob",
				"Smith", "1234567890", "8850 West Mayfield");
		// adds contact to the service
		contactService.addContact(contact);
		// asserts the size of the contact list is 1 after adding contact
		assertEquals(1, contactService.getSize(),
				"Size should be 1 after contact is added");
	}
	
	// Test for adding two contacts
	@Test
	void testAddingMultipleContacts() {
		Contact contact1 = new Contact("15555", "Bob", "Smith",
				"1234567890", "8850 West Mayfield");
		Contact contact2 = new Contact("15556", "Daniel", "Roberts",
				"2134567890", "9919 North Tulip St");
		contactService.addContact(contact1);
		contactService.addContact(contact2);
		assertEquals(2, contactService.getSize(),
				"Size should be 2 after both contacts are added");
	}
	
	// Test for retrieving contact by Id
	@Test
	void testAddGetContact() {
		Contact contact = new Contact("15555", "Bob", "Smith",
				"1234567890", "8850 West Mayfield");
		contactService.addContact(contact);
		Contact getContact = contactService.getContactById("15555");
		assertEquals("Bob", getContact.getFirstName(), "First name Bob");
		assertEquals("Smith", getContact.getLastName(), "First name Smith");
	}
	
	// Tests updating a contact changes all provided fields
	@Test
	void testUpdateContact() {
		Contact contact = new Contact("15555", "Bob", "Smith",
				"1234567890", "8850 West Mayfield");
		contactService.addContact(contact);

		contactService.updateContact("15555", "Bobby", "Blue", "3334445566",
				"8818 East Brooks");

		Contact updatedContact = contactService.getContactById("15555");
		assertEquals("Bobby", updatedContact.getFirstName(),
				"First name updated");
		assertEquals("Blue", updatedContact.getLastName(),
				"Last name updated");
		assertEquals("3334445566", updatedContact.getPhoneNumber(),
				"Phone Number updated");
		assertEquals("8818 East Brooks", updatedContact.getAddress(),
				"Address updated");
	}

	// Tests updateContact, treats null parameters as "do not change"
	@Test
	void testUpdateContactIgnoresNullParameters() {
		Contact contact = new Contact("15555", "Bob", "Smith",
				"1234567890", "8850 West Mayfield");
		contactService.addContact(contact);

		contactService.updateContact("15555", null, "Blue", null,
				"8818 East Brooks");

		Contact updated = contactService.getContactById("15555");
		assertEquals("Bob", updated.getFirstName(),
				"Null first name should not overwrite value");
		assertEquals("Blue", updated.getLastName());
		assertEquals("1234567890", updated.getPhoneNumber(),
				"Null phone should not overwrite value");
		assertEquals("8818 East Brooks", updated.getAddress());
	}

	// Tests retrieving an unknown ID returns null
	@Test
	void testGetContactByIdReturnsNullWhenMissing() {
		assertNull(contactService.getContactById("99999"));
	}

	// Tests delete removes the contact and decreases size
	@Test
	void testDeleteContact() {
		// Create contact
		Contact contact = new Contact("15555", "Bob", "Smith", "1234567890", "8850 West Mayfield");
		// Add contact to contactService
		contactService.addContact(contact);
		// Checks if contact is present
		assertEquals(1, contactService.getSize(), "Size should be 1");
		// Deletes contact by Id
		contactService.deleteContact("15555");
		// Checks if contact was deleted
		assertEquals(0, contactService.getSize(), "Size should be 0");
	}

	// Tests the repository capacity limit of 100
	@Test
	void testRepositoryCapacityLimit() {
		// Fill repository to its max capacity.
		for (int i = 0; i < 100; i++) {
			contactService.addContact(new Contact(
					String.format("ID%05d", i),
					String.format("FN%02d", i % 100),
					"LN" + (i % 10),
					String.format("%010d", i),
					"Addr " + i));
		}
		assertEquals(100, contactService.getSize());

		// Next add should fail due to capacity
		Contact overflow = new Contact("OVERFLOW", "Bob", "Smith",
				"9999999999", "Addr");
		assertThrows(IllegalStateException.class,
				() -> contactService.addContact(overflow),
				"Should throw when storage is full");
	}

	// Dependency injection tests

	// Verifies that the addContact method correctly propagates exceptions thrown
	// by the repository
	@Test
	void testAddContactPropagatesRepositoryFailure() {
		ContactService failingService = new ContactService(new FailingRepository());
		Contact contact = new Contact("10000", "Jane", "Doe", "1234567890", "123 Lane");

		assertThrows(IllegalArgumentException.class,
				() -> failingService.addContact(contact),
				"Service should bubble repository failures when storage rejects adds");
	}

	// Verifies that the deleteContact method allows repository exceptions
	// to propagate up to the caller.
	@Test
	void testDeleteContactPropagatesRepositoryFailure() {
		ContactService failingService = new ContactService(new FailingRepository());

		assertThrows(IllegalArgumentException.class,
				() -> failingService.deleteContact("missing"),
				"Delete should fail when the repository reports an error");
	}

	// Verifies that the deleteContact method allows repository exceptions
	// to propagate up to the caller.
	@Test
	void testUpdateContactPropagatesRepositoryFailure() {
		ContactService failingService = new ContactService(new FailingRepository());

		assertThrows(IllegalArgumentException.class,
				() -> failingService.updateContact("missing", "A", "B", "1234567890", "Addr"),
				"Update should fail when the repository cannot find the requested contact");
	}
}
