// Author: Liel Simon
// Date: 11/16/2024

package contact;
//import testing and assertion libraries
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Test class for validating ContactService class functionality
class ContactServiceTest {
	// Declares an instance variable for the ContactService class
	private ContactService contactService;
	// Sets up a new instance of contactService before each test method is run
	@BeforeEach
	void setUp() {
		contactService = new ContactService();
	}
	
	// Test for adding a single contact to ContactService
	@Test
	void testAddingSingleContact() {
		// create contact object
		Contact contact = new Contact("15555", "Bob", "Smith", "1234567890", "8850 West Mayfield");
		// adds contact to the service
		contactService.addContact(contact);
		// asserts the size of the contact list is 1 after adding contact
		assertEquals(1, contactService.getSize(),"Size should be 1 after contact is added");
	}
	
	// Test for adding multiple contacts
	@Test
	void testAddingMultipleContacts() {
		// creates two contact objects
		Contact contact1 = new Contact("15555", "Bob", "Smith", "1234567890", "8850 West Mayfield");
		Contact contact2 = new Contact("15556", "Daniel", "Roberts", "2134567890", "9919 North Tulip St");
		// adds both contacts to contactService
		contactService.addContact(contact1);
		contactService.addContact(contact2);
		//asserts that the size of the contact list is 2 after contacts are added
		assertEquals(2, contactService.getSize(), "Size should be 2 after both contacts are added");
		}
	
	// Test for retrieving contact by Id
	@Test
	void testAddGetContact() {
		// Create contact
		Contact contact = new Contact("15555", "Bob", "Smith", "1234567890", "8850 West Mayfield");
		// Add contact to contactService
		contactService.addContact(contact);
		// Retrieves contact by Id
		Contact getContact = contactService.getContactById("15555");
		// Assert first and last name matches
		assertEquals("Bob", getContact.getFirstName(),"First name Bob");
		assertEquals("Smith", getContact.getLastName(),"First name Smith");
	}
	
	// Test for updating contact
	@Test
	void testUpdateContact() {
		// Create contact
		Contact contact = new Contact("15555", "Bob", "Smith", "1234567890", "8850 West Mayfield");
		// Add contact to contactService
		contactService.addContact(contact);
		// Updates contact fields
		contactService.updateContact("15555", "Bobby", "Blue", "3334445566", "8818 East Brooks");
		// retrieves updated contact
		Contact updatedContact = contactService.getContactById("15555");
		// Asserts that each field has been updated
		assertEquals("Bobby", updatedContact.getFirstName(), "First name updated");
		assertEquals("Blue", updatedContact.getLastName(), "Last name updated");
		assertEquals("3334445566", updatedContact.getPhoneNumber(), "Phone Number updated");
		assertEquals("8818 East Brooks", updatedContact.getAddress(), "Address updated");
	}
	
	// Test for deleting contact
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
}
