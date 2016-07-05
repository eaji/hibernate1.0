package com.exist.service;

import com.exist.model.*;
import com.exist.dao.*;
import java.util.*;

public class AppServiceManager {
	private PersonDao p = new PersonDao();
	private RoleDao r = new RoleDao();

	public void addPerson(Person person){
		p.addPerson(person);
	}

	public void updatePerson(Person person) {
		p.updatePerson(person);
	}

	public void deletePersonById(int id) {
		p.deletePersonById(id);
	}

	public List<Person> getAllPersons() {
		return p.getAllPersons();
	}

	public List<Person> getPersonsSortedBy(int id1, int id2) {
		return p.getPersonsSortedBy(id1, id2);
	}

	public int getPersonsCount() {
		return p.getPersonsCount();
	}

	public Person getPersonById(int id) {
		return p.getPersonById(id);
	}

	public void updateAddress(Address address) {
		p.updateAddress(address);
	}

	public List<Contact> getContactsByPersonId(int id) {
		return p.getContactsByPersonId(id);
	}

	public void addPersonContactById(int id, List<Contact> newContacts) {
		p.addPersonContactById(id, newContacts);
	}

	public void updatePersonContactById(int id, Contact contact) {
		p.updatePersonContactById(id, contact);
	}

	public void deletePersonContactById(int id) {
		p.deletePersonContactById(id);
	}

	public Set<Integer> getPersonIds() {
		return p.getPersonIds();
	}

	public Set<Integer> getPersonContactIds(int id) {
		return p.getPersonContactIds(id);
	}

	public List<Role> getAllRoles() {
		return r.getAllRoles();
	}

	public List<Role> getRolesByPersonId(int id) {
		return r.getRolesByPersonId(id);
	}

	public void manipulatePersonRoleById(int id, List<Role> personRoles) {
		r.manipulatePersonRoleById(id, personRoles);
	}

}
