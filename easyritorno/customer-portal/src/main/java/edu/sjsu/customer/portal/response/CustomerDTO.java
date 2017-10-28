package edu.sjsu.customer.portal.response;

public class CustomerDTO extends GenericResponse {

	private static final long serialVersionUID = 1L;

	private String id;
	private String firstName;
	private String lastName;

	public CustomerDTO() {
	}

	public CustomerDTO(String id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CustomerDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
