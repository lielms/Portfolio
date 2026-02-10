package contact;

// Repository interface for Contact storage.
//
// Decouple storage implementation from service logic.
// Enable swapping array storage for other strategies (list, map, database)
// Improve unit testing by allowing test doubles.

public interface ContactRepository {

    // Return number of contacts currently stored
    int size();

    // Adds a contact.
    void  add(Contact contact);

    // Finds a contact by ID.
    Contact findById(String contactId);

    // Deletes a contact by ID.
    void deleteById(String contactId);
}
