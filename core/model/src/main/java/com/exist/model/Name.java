package com.exist.model;

import javax.persistence.*;

@Embeddable
public class Name {

    private String lastName;
	private String firstName;
	private String middleName;
	private String fullName;

	public Name(){};
	
	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "middle_name")
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	@Transient
	public String getFullName() {
		return fullName = this.firstName + " " + this.middleName + " " + this.lastName;
	}
}
