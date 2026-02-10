package contact;

// Test double repository that simulates failures.
//
// Verify that service code properly propagates or handles repository errors
// and create negative test scenarios.
public class FailingRepository implements ContactRepository {

    // simulate return 0
    @Override
    public  int size () {
        return 0;
    }

    // Always fails to simulate repository add failure.
    @Override
    public void add(Contact contact){
        throw new IllegalArgumentException("Simulated failure");
    }

    // Simulate return null
    @Override
    public Contact findById(String contactId) {
        return null;
    }

    // Simulate delete failure or missing record
    @Override
    public void deleteById(String contactId) {
        throw new IllegalArgumentException("Not Found");
    }
}
