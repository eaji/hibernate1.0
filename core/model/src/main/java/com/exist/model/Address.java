package com.exist.model;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "ADDRESS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="Contact")
public class Address {
	private int id;
	private String streetNumber;
	private String barangay;
	private String city;
	private String zipCode;
	private Person person;

	public Address(){};



 	@Id
	@GeneratedValue(generator="foreign")
 	@GenericGenerator(name="foreign", strategy = "foreign", parameters={@Parameter(name="property", value="person")})
	@Column(name = "id", unique = true, nullable = false)
	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	@Column(name = "street_number")
	public String getStreetNumber(){
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber){
		this.streetNumber = streetNumber;
	}

	@Column(name = "barangay")
	public String getBarangay(){
		return barangay;
	}

	public void setBarangay(String barangay){
		this.barangay = barangay;
	}

	@Column(name = "city")
	public String getCity(){
		return city;
	}

	public void setCity(String city){
		this.city = city;
	}

	@Column(name = "zip_code")
	public String getZipCode(){
		return zipCode;
	}

	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}

    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public Person getPerson(){
        return person;
    }
    
    public void setPerson(Person person){
        this.person = person;
    }
}
