package com.exist.model;

import java.util.*;

public class Person implements Comparable<Person> {
	private int id;
	private String lastName;
	private String firstName;
	private String middleName;
	private String fullName;
	private Address address;
	private Contact contact;
	private Date birthdate;
	private float gwa;
	private Date dateHired;
	private boolean employed;
	private Set<Contact> contacts;

	public Person(){};

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName)  {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName)  {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getFullName() {
		return fullName = this.firstName + " " + this.middleName + " " + this.lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public float getGwa() { 
		return gwa;
	}

	public void setGwa(float gwa) {
		this.gwa = gwa;
	}

	public Date getDateHired() {
		return dateHired;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}

	public boolean isEmployed() {
		return employed;
	}

	public void setEmployed(boolean employed) {
		this.employed = employed;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public int compareTo(Person comparePerson) {
		float compareGwa = ((Person) comparePerson).getGwa(); 
		float diff = this.gwa - compareGwa;
		 if(diff < 0) {
		 	return -1;
		 } else if(diff > 0) {
		 	return 1;
		 } else {
		 	return 0;
		 }

	}	

}