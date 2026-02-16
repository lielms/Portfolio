// Author: Liel Simon
// Date: 11/16/2024

package contact;

// Contact object class
public class Contact {
	// Private fields to store contact details
	private String contactId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	// Constructor to initialize Contact object with input validation
	public Contact(String contactId, String firstName, String lastName, String phoneNumber, String address) {
		// Validates contactID. Must not be null or greater than 10 chars
		if (contactId == null || contactId.length()>10) {
			throw new IllegalArgumentException("Invalid id");
		}
		// Validates firstName. Must not be null or greater than 10 chars
		if (firstName == null || firstName.length() > 10) {
			throw new IllegalArgumentException("Invalid first name");
		}
		// Validates lastName. Must not be null or greater than 10 chars
		if (lastName == null || lastName.length() > 10) {
			throw new IllegalArgumentException("Invalid last name");
		}
		// Validates phoneNumber. Must not be null and must be exactly 10 chars
		if (phoneNumber == null || phoneNumber.length() != 10) {
			throw new IllegalArgumentException("Invalid phone number");
		}
		// for loop to check if only digits were entered for phone number
		for (int i = 0; i < phoneNumber.length(); i++) {
			if (!Character.isDigit(phoneNumber.charAt(i))) {
				throw new IllegalArgumentException("Invalid phone number");
			}
		}
		// Validates address. Must not be null or greater than 10 chars
		if (address == null || address.length() > 30) {
			throw new IllegalArgumentException("Invalid address");
		}
		// Assign values to current object
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
		if (firstName == null|| firstName.length() > 10) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		if (lastName == null || lastName.length() > 10) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}
	public void setPhoneNumber(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.length() != 10) {
			throw new IllegalArgumentException("Invalid phone number");
		}
		// for loop checks if only digits were entered for phone number
		for (int i = 0; i < phoneNumber.length(); i++) {
			if (!Character.isDigit(phoneNumber.charAt(i))) {
				throw new IllegalArgumentException("Invalid phone number");
			}
		}
		this.phoneNumber = phoneNumber;
	}
	public void setAddress(String address) {
		if (address == null || address.length() > 30) {
			throw new IllegalArgumentException("Invalid address");
		}
		this.address = address;
	}
}