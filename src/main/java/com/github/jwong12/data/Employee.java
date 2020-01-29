package com.github.jwong12.data;

import java.time.LocalDate;

/**
 * Employee Class
 */
public class Employee {
	private String employeeID;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	
	/**
	 * Default Constructor
	 */
	public Employee() {}
	
	/**
	 * Constructor
	 * @param employeeID
	 * @param firstName
	 * @param lastName
	 * @param dateOfBirth
	 */
	public Employee(String employeeID, String firstName, String lastName, LocalDate dateOfBirth) {
		super();
		this.employeeID = employeeID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmployeeID() {
		return employeeID;
	}
	
	public void setEmployeeID(String employeeID) {
		if (employeeID != null && employeeID.length() == 9) {
			this.employeeID = employeeID;
		}
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		if (firstName != null && firstName.length() > 0) {
			this.firstName = firstName;
		}
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		if (lastName != null && lastName.length() > 0) {
			this.lastName = lastName;
		}
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		if (dateOfBirth != null) {
			this.dateOfBirth = dateOfBirth;
		}
	}
	
	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfBirth=" + dateOfBirth + ", toString()=" + super.toString() + "]";
	}
}
