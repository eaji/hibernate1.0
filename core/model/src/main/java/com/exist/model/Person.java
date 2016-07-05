package com.exist.model;


import java.util.*; 
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



@Entity
@Table(name = "PERSON")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="Person")
public class Person implements Comparable<Person> {
	private int id;
	private Name name;
	private Address address;
	private Date birthdate;
	private float gwa;
	private Date dateHired;
	private boolean employed;
	private List<Contact> contacts;
	private List<Role> roles;

	public Person(){};

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Embedded
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	@OneToOne(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Column(name = "birthdate")
	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	@Column(name = "gwa")
	public float getGwa() { 
		return gwa;
	}

	public void setGwa(float gwa) {
		this.gwa = gwa;
	}

	@Column(name = "date_hired")
	public Date getDateHired() {
		return dateHired;
	}

	public void setDateHired(Date dateHired) {
		this.dateHired = dateHired;
	}

	@Column(name = "employed")
	public boolean isEmployed() {
		return employed;
	}

	public void setEmployed(boolean employed) {
		this.employed = employed;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "person_role", joinColumns = { 
			@JoinColumn(name = "person_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "role_id", 
					nullable = false, updatable = false) })
	public List<Role> getRoles(){
		return this.roles;
	}

	public void setRoles(List<Role> roles){
		this.roles = roles;
	}

	@OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
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
