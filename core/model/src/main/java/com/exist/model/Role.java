package com.exist.model;

import java.util.*;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "ROLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="Role")
public class Role {
	private int id;
	private String role;
	private Set<Person> persons;

	public Role() {};

	public Role(String role) {
		this.role = role;
	}

/*	public Role(String role, Set<Person> persons) {
		this.role = role;
		this.persons = persons;
	}
*/
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}

	@Column(name="role")
	public String getRole(){
		return this.role;
	}

	public void setRole(String role){
		this.role = role;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = CascadeType.ALL)
	public Set<Person> getPersons(){
		return this.persons;
	}

	public void setPersons(Set<Person> persons){
		this.persons = persons;
	}
	

	@Override
	public String toString(){
		return this.role;
	}

}
