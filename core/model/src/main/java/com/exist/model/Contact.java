package com.exist.model;

public class Contact {
	private int id;
	private ContactType contactType;
	private String value;
	private Person person;

	public Contact(){};

	public Contact(ContactType contactType, String value){
		this.contactType = contactType;
		this.value = value;
	}

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	public ContactType getContactType(){
		return contactType;
	}

	public void setContactType(ContactType contactType){
		this.contactType = contactType;
	}

	public String getValue(){
		return value;
	}

	public void setValue(String value){
		this.value = value;
	}

	public Person getPerson(){
		return person;
	}

	public void setPerson(Person person){
		this.person = person;
	}

	public String toString(){
		return contactType + ": " + this.value;
	}
}
