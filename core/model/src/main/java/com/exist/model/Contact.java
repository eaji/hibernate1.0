package com.exist.model;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "CONTACT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="Contact")
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

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "contact_type")
	public ContactType getContactType(){
		return contactType;
	}

	public void setContactType(ContactType contactType){
		this.contactType = contactType;
	}

	@Column(name = "value")
	public String getValue(){
		return value;
	}

	public void setValue(String value){
		this.value = value;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id", nullable = false)
	public Person getPerson(){
		return person;
	}

	public void setPerson(Person person){
		this.person = person;
	}

	@Override
	public String toString(){
		return contactType + ": " + this.value;
	}
}
