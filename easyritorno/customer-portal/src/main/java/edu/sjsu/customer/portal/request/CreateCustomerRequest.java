package edu.sjsu.customer.portal.request;

import java.io.Serializable;

public class CreateCustomerRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;

	public CreateCustomerRequest() {
	}

	public CreateCustomerRequest(String firstName, String lastName) {
		super();
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

	@Override
	public String toString() {
		return "CreateCustomerRequest [firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
