package ont.paarma.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class User {
	private int id;

	@NotEmpty
    @Length(min = 2, max = 50)
	private String firstName;
	@NotEmpty
    @Length(min = 2, max = 50)
	private String lastName;
//    @Length(min = 2, max = 50)
//	private String streetAddress;
//    @Length(min = 2, max = 10)
//	private String zipCode;
//    @Length(min = 2, max = 50)
//	private String city;
//    @Length(min = 2, max = 20)
//	private String phoneNumber;	
//    @Length(min = 2, max = 50)
//	private String email;
	
    public User(){
    	super();
    }
    
	public User(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

//	public String getStreetAddress() {
//		return streetAddress;
//	}
//
//	public void setStreetAddress(String streetAddress) {
//		this.streetAddress = streetAddress;
//	}
//
//	public String getZipCode() {
//		return zipCode;
//	}
//
//	public void setZipCode(String zipCode) {
//		this.zipCode = zipCode;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public String getPhoneNumber() {
//		return phoneNumber;
//	}
//
//	public void setPhoneNumber(String phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}
//	
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//
//	@Override
//	public String toString() {
//		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", streetAddress="
//				+ streetAddress + ", zipCode=" + zipCode + ", city=" + city + ", phoneNumber=" + phoneNumber
//				+ ", email=" + email + "]";
//	}
	
}

