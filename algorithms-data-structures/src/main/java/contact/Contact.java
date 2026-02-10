// Author: Liel Simon
// Date: 11/16/2024

package contact;

// Contact object class
//
// Encapsulates: contactId, firstName,
// 		lastName, phoneNumber, address
//
// Constructor validates all fields on creation.
// Setters validate each field on update.
public class Contact {

	// Private fields to store contact data
	private String contactId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;

	// Constructs a new Contact and validates all fields using ContactValidator
	public Contact(String contactId, String firstName, String lastName,
				   String phoneNumber, String address) {
		ContactValidator.validateId(contactId);
		ContactValidator.validateFirstName(firstName);
		ContactValidator.validateLastName(lastName);
		ContactValidator.validatePhone(phoneNumber);
		ContactValidator.validateAddress(address);

		// Assign validated values to current object
		this.contactId = contactId;
		this.firstName = firstName;
	    this.lastName = lastName;
	    this.phoneNumber = phoneNumber;
	    this.address = address;
	}
	
	// Contact information accessor methods
	public String getContactId() {
		return contactId;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	
	// Contact information mutator methods with input validation
	public void setFirstName(String firstName) {
		ContactValidator.validateFirstName(firstName);
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		ContactValidator.validateLastName(lastName);
		this.lastName = lastName;
	}
	public void setPhoneNumber(String phoneNumber) {
		ContactValidator.validatePhone(phoneNumber);
		this.phoneNumber = phoneNumber;
	}
	public void setAddress(String address) {
		ContactValidator.validateAddress(address);
		this.address = address;
	}
}