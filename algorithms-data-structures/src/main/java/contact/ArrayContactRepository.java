package contact;

// Array-backed repository for storing contacts
public class ArrayContactRepository implements ContactRepository {
    private  static final int MAX_CONTACTS = 100;
    private final Contact[] contacts = new Contact[MAX_CONTACTS];
    private int size = 0;

    // Return number of stored contacts
    @Override
    public int size() {
        return size;
    }

    // Adds a contact
    // Prevents duplicate IDs or adding contact beyond array limits
    @Override
    public void add(Contact contact) {
        // Check for duplicate
        for (int i = 0; i < size; i++) {
            if (contacts[i].getContactId().equals(contact.getContactId())) {
                throw new IllegalArgumentException("ID already exists");
            }
        }
        // Capacity check
        if (size >= MAX_CONTACTS) {
            throw new IllegalStateException("Contact storage is full");
        }
        // Store and increment size
        contacts[size++] = contact;
    }

    // Find a contact by ID
    @Override
    public Contact findById(String contactId){
        // Linear search across array for contactId
        for (int i = 0; i < size; i++) {
            if (contacts[i].getContactId().equals(contactId)) {
                return contacts[i];
            }
        }
        return null;
    }

    // Deletes a contact by ID
    @Override
    public void deleteById(String contactId) {
        for (int i = 0; i < size; i++) {
            if (contacts[i].getContactId().equals(contactId)) {

                // Shift left from the deletion index to compact the array.
                for (int j = i; j < size - 1; j++) {
                    contacts[j] = contacts[j + 1];
                }

                // Clear last element and decrement size.
                contacts[size - 1] = null;
                size--;
                return;
            }
        }
        // ID not found
        throw new IllegalArgumentException("Contact id does not exist");
    }
}
