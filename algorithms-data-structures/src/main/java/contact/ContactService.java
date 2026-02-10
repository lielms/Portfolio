// Author: Liel Simon
// Date: 11/16/2024

package contact;

import java.util.logging.Logger;

// Manages Contact objects.
// Provides CRUD operations with logging, input validation, and null checks.
public class ContactService {
	// repository responsible for storing, finding, and deleting contacts.
	private final ContactRepository repo;

	// Logger for tracing service operations.
	private static final Logger LOGGER =
			Logger.getLogger(ContactService.class.getName());

	// Default constructor uses an array-backed repository.
	// This keeps usage simple while preserving the ability to
	// inject other implementations in tests.
	public ContactService() {
		this(new ArrayContactRepository());
	}

	// Constructor that accepts a repository implementation.
	public ContactService(ContactRepository repo) {
		if (repo == null) {
			throw new IllegalArgumentException("Repository cannot be null");
		}
		this.repo = repo;
	}

	// Return number of contacts currently stored.
	public int getSize() {
	    return repo.size();
	}

	// Adds new contact to the repository.
	// Repository enforces unique IDs and capacity limit.
	public void addContact(Contact contact) {
		LOGGER.fine(() -> "Adding contact " + contact.getContactId());
		repo.add(contact);
		}

	// Deletes contact by id.
	// Repository throws if the ID does not exist.
	public void deleteContact(String contactId) {
		LOGGER.fine(() -> "Deleting contact " + contactId);
		repo.deleteById(contactId);
		}

	// Updates existing contact.
	// contactId must be valid, If contact is not found, throws.
	// Setters on Contact re-validate each field using ContactValidator.
	public void updateContact(String contactId, String firstName, String lastName,
							  String phoneNumber, String address) {
		LOGGER.fine(() -> "Updating contact " + contactId);
		// Validate contact ID format before querying repository.
		ContactValidator.validateId(contactId);

		// Retrieve the contact to update.
		Contact contact = repo.findById(contactId);
		if (contact == null) {
			// Log at warning because the caller attempted an invalid operation.
			LOGGER.warning(() -> "Update failed; contact " + contactId + " not found");
			throw new IllegalArgumentException("Contact ID does not exist");
		}
		// Apply only non-null updates. Each setter validates its input.
		if (firstName != null) {
			contact.setFirstName(firstName);
		}
		if (lastName != null) {
			contact.setLastName(lastName);
		}
		if (phoneNumber != null) {
			contact.setPhoneNumber(phoneNumber);
		}
		if (address != null) {
			contact.setAddress(address);
		}
	}

	// Retrieves contact by Id.
	// Returns null if not found, Logs a message if missing.
	public Contact getContactById(String contactId) {
		Contact contact = repo.findById(contactId);
		if (contact == null) {
			LOGGER.fine(() -> "Contact " + contactId + " not found");
		}
		return contact;
		}
}
