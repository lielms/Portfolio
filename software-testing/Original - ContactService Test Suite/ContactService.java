// Author: Liel Simon
// Date: 11/16/2024

package contact;

// This class manages Contact objects with methods to add, delete, update, and get contacts
// and stores contacts in an array
public class ContactService {
	
	private static final int MAX_CONTACTS = 100; // Maximum number of contacts
	private Contact[] contacts = new Contact[MAX_CONTACTS]; // Creates array to store contacts
	private int size = 0; // Sets current contacts to 0 
	// returns current number of contacts
	public int getSize() {
	    return size;
	}
	// Adds new contact
	public void addContact(Contact contact) {
		// Checks for duplicate contact id
		for (int i =0; i < size; i++) {
			if (contacts[i].getContactId().equals(contact.getContactId())) {
				throw new IllegalArgumentException("ID already exists");
			}
		}
		// throws exception if array is full
	if (size >= MAX_CONTACTS) {
		throw new IllegalStateException("Contact storage is full");
	}
	// Add new contact to array and increments size
	contacts[size] = contact;
	size += 1;
	}
	
// Deletes contact by id
public void deleteContact(String contactId) {
	for (int i = 0; i < size; i++) {
		if (contacts[i].getContactId().equals(contactId)) {
			// loop to shift remaining contacts to the left
			for (int j = i; j < size - 1; j++) {
				contacts[j] = contacts[j + 1];
			}
			// sets last reference to null and reduce array size
			contacts[size - 1] = null;
			size -= 1;
			return;
		}
	}
	// throws exception if unable to find contact id
	throw new IllegalArgumentException("Contact id does not exist");
	}
// Allows for existing contact to be updated
public void updateContact(String contactId, String firstName, String lastName, String phoneNumber, String address) {
    // Validate contact Id entered
    if (contactId == null || contactId.length() > 10) {
        throw new IllegalArgumentException("Invalid contact ID");
    }
	for (int i = 0; i < size; i++) {
		// find contact by Id
		if (contacts[i].getContactId().equals(contactId)) {
			// updates field if valid input
			if (firstName != null) {
				contacts[i].setFirstName(firstName);
			}
			if (lastName != null) {
				contacts[i].setLastName(lastName);
			}
			if (phoneNumber != null) {
				contacts[i].setPhoneNumber(phoneNumber);
			}
			if (address != null) {
				contacts[i].setAddress(address);
			}
			return;
		}
	}
	// throws exception if no contact with that id is found
		throw new IllegalArgumentException("Contact ID does not exist");
	}
// retrieves contact by Id
public Contact getContactById(String contactId) {
    for (int i = 0; i < size; i++) {
        if (contacts[i].getContactId().equals(contactId)) {
            return contacts[i];
        }
    }
    return null;
}
}
