package com.exist.service;

import com.exist.model.*;
import com.exist.dao.*;
import java.util.*;

public class AppServiceManager {
	private PersonDao p = new PersonDao();
	private AddressDao a = new AddressDao();
	private ContactDao c = new ContactDao();

	public void addPerson(Person person){
		Address address = a.addAddress(person.getAddress());
		person.setAddress(address);
		p.addPerson(person);
	}

	public void updatePerson(Person person) {
		p.updatePerson(person);
	}

	public void deletePersonById(int id) {
		//Person person = p.getPersonById(id);
		p.deletePersonById(id);
	}

	public List<Person> getAllPersons() {
		return p.getAllPersons();
	}

	public List<Person> getPersonsSortedByLastNameAsc() {
		return p.getPersonsSortedByLastNameAsc();
	}

	public List<Person> getPersonsSortedByLastNameDesc() {
		return p.getPersonsSortedByLastNameDesc();
	}

	public List<Person> getPersonsSortedByDateHiredAsc() {
		return p.getPersonsSortedByDateHiredAsc();
	}

	public List<Person> getPersonsSortedByDateHiredDesc() {
		return p.getPersonsSortedByDateHiredDesc();
	}

	public Person getPersonById(int id) {
		return p.getPersonById(id);
	}

	public void updateAddress(Address address) {
		a.updateAddress(address);
	}

	public Set<Contact> getContactsByPersonId(int id) {
		return c.getContactsByPersonId(id);
	}

	public void addPersonContactById(int id, Contact contact) {
		c.addPersonContact(id, contact);
	}

	public void updatePersonContactById(int id, Contact contact) {
		c.updatePersonContactById(id, contact);
	}

	public void deletePersonContactById(int id) {
		c.deleteContactById(id);
	}

	public Set<Integer> getPersonIds() {
		return p.getPersonIds();
	}

	public Set<Integer> getPersonContactIds(int id) {
		return c.getPersonContactIds(id);
	}

}
