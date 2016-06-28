package com.exist;

import com.exist.service.AppServiceManager;
import com.exist.model.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;


public class AppService {
	private AppServiceManager appServiceManager = new AppServiceManager();
	private Scanner in = new Scanner(System.in);


	public void commands() {
		System.out.println();
		System.out.println("Enter number of command:");
		System.out.println("1. ADD PERSON");
		System.out.println("2. UPDATE PERSON");
		System.out.println("3. DELETE PERSON");
		System.out.println("4. LIST PERSON");
		System.out.println("5. ADD PERSON CONTACT");
		System.out.println("6. UPDATE PERSON CONTACT");
		System.out.println("7. DELETE PERSON CONTACT");
		System.out.println("8. EXIT");
		System.out.print("Enter Equivalent Number of Command: ");
	}

	public void showAllPersons() {
		if(hasAvailablePerson()) {
			System.out.print("Sort by Last name/Date hired/GWA (L/D/G): ");
			String sortBy = in.nextLine();
			while(!(sortBy.matches("[ldgLDG]$")) || sortBy.isEmpty()) {
				System.out.print("Please enter a valid choice: ");
				sortBy = in.nextLine();
			}
			System.out.print("Ascending or Descending (A/D): ");
			String order = in.nextLine();
			while(!(order.matches("[adAD]$")) || order.isEmpty()) {
				System.out.print("Please enter a valid choice: ");
				order = in.nextLine();
			}

			if(sortBy.equalsIgnoreCase("l") && order.equalsIgnoreCase("a")) {
				listPersonsByLastNameAsc();
			}

			else if(sortBy.equalsIgnoreCase("l") && order.equalsIgnoreCase("d")) {
				listPersonsByLastNameDesc();
			}

			else if(sortBy.equalsIgnoreCase("d") && order.equalsIgnoreCase("a")) {
				listPersonsByDateHiredAsc();
			}

			else if(sortBy.equalsIgnoreCase("d") && order.equalsIgnoreCase("d")) {
				listPersonsByDateHiredDesc();
			}
			else if(sortBy.equalsIgnoreCase("g") && order.equalsIgnoreCase("a")) {
				listPersonsByGwaAsc();
			}
			else if(sortBy.equalsIgnoreCase("g") && order.equalsIgnoreCase("d")) {
				listPersonsByGwaDesc();
			}
		}
	}

	public void listPersonsByLastNameAsc() {
		List<Person> persons  = appServiceManager.getPersonsSortedByLastNameAsc();
		displayPersonsTable(persons);
	}

	public void listPersonsByLastNameDesc() {
		List<Person> persons  = appServiceManager.getPersonsSortedByLastNameDesc();
		displayPersonsTable(persons);
	}

	public void listPersonsByDateHiredAsc() {
		List<Person> persons  = appServiceManager.getPersonsSortedByDateHiredAsc();
		displayPersonsTable(persons);
	}

	public void listPersonsByDateHiredDesc() {
		List<Person> persons  = appServiceManager.getPersonsSortedByDateHiredDesc();
		displayPersonsTable(persons);
	}

	public void listPersonsByGwaAsc() {
		List<Person> persons  = appServiceManager.getAllPersons();
		Collections.sort(persons);
		displayPersonsTable(persons);     
	}
	public void listPersonsByGwaDesc() {
		List<Person> persons  = appServiceManager.getAllPersons();
		Collections.sort(persons ,Collections.reverseOrder());
		displayPersonsTable(persons); 			
	}

	public void addPerson() {
		Person person = new Person();
		Address address = new Address();
		createUpdatePerson(person);
		createUpdateAddress(address);
		Set<Contact> contacts = createContacts();
		for(Contact c : contacts) {
			c.setPerson(person);
		}
		person.setAddress(address);
		person.setContacts(contacts);
		appServiceManager.addPerson(person);
		System.out.println("Person Successfully added!");
	}

	public void updatePersonById() {
		if(hasAvailablePerson()) {
			displayShortPersonsTable(appServiceManager.getAllPersons());
			String personId = getValidPersonId();
			Person person = appServiceManager.getPersonById(Integer.parseInt(personId));
			Person updatedPerson = createUpdatePerson(person);

			System.out.print("Do you want to update the Address (y/n): ");
			String toUpdateAddress = in.nextLine();
			while(!(toUpdateAddress.matches("[ynYN]$")) || toUpdateAddress.isEmpty()) {
				System.out.print("(y/n) only: ");
				toUpdateAddress = in.nextLine();
			}
			if(toUpdateAddress.equalsIgnoreCase("y")) {
	    		Address updatedAddress = createUpdateAddress(updatedPerson.getAddress());
	    		appServiceManager.updateAddress(updatedAddress);
	    	} else {
				System.out.println("The old address is retained.");
			}

			appServiceManager.updatePerson(updatedPerson);
	    	appServiceManager.updateAddress(person.getAddress());											
			System.out.println("Successfully updated!");
		}
	}

	private Person createUpdatePerson(Person person) {
		System.out.println("Person Info...");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		boolean isEmployed;
		String dateHired;
		Date dHired = null;
		System.out.print("First name: ");
		String firstName = isValidWord(in.nextLine(), "Please input a valid first name: ");
		person.setFirstName(firstName);

		System.out.print("Middle name: ");
		String middleName = isValidWord(in.nextLine(), "Please input a valid middle name: ");
		person.setMiddleName(middleName);

		System.out.print("Last name: ");
		String lastName = isValidWord(in.nextLine(), "Please input a valid last name: ");
		person.setLastName(lastName);

		System.out.print("Birthdate (yyyy/mm/dd): ");
		String birthDate = isValidDate(in.nextLine());

		Date bdate = null;
		try {
			bdate = dateFormat.parse(birthDate);
		} catch(ParseException e) {
			e.printStackTrace();
		}
		person.setBirthdate(bdate);

		System.out.print("GWA (1.0-4.0): ");
		String gwa = in.nextLine();
		while(!(gwa.matches("[4]?(\\.[0]{1})*|([1-3])(\\.[0-9]{1})*$")) || gwa.isEmpty()) {
			System.out.print("Please enter a valid GWA (1.0-4.0): ");
			gwa = in.nextLine();
		}
		person.setGwa(Float.parseFloat(gwa));

		System.out.print("Employed (y/n): ");
		String employed = in.nextLine();

		while(!(employed.matches("[ynYN]$")) || employed.isEmpty()) {
			System.out.print("(y/n) only: ");
			employed = in.nextLine();
		}
		if(employed.equalsIgnoreCase("y")) {
			isEmployed = true;
		} else {
			isEmployed = false;
		}
		person.setEmployed(isEmployed);

		if(isEmployed) {
			System.out.print("Employment date (yyyy/mm/dd): ");
			dateHired = isValidDate(in.nextLine());
			try {
				dHired = dateFormat.parse(dateHired);
			} catch(ParseException e) {
				e.printStackTrace();
			}
			person.setDateHired(dHired);
		} else {
			try {
				dHired = dateFormat.parse("0001/01/01");
			} catch(ParseException e) {
				e.printStackTrace();
			}
			person.setDateHired(dHired);
		}
		return person;
	}

	public void deletePersonById() {
		if(hasAvailablePerson()) {
			displayShortPersonsTable(appServiceManager.getAllPersons());
	        String personId = getValidPersonId();
			appServiceManager.deletePersonById(Integer.parseInt(personId));
			System.out.println("Person succesfully deleted!");
		}
	}

	private Address createUpdateAddress(Address address) {
		System.out.println("Address Info...");
		System.out.print("Street No.: ");
		String streetNumber = isValidNumber(in.nextLine(), "Please input a valid street no.: ");
		address.setStreetNumber(streetNumber);

		System.out.print("Barangay: ");
		String barangay = isValidWord(in.nextLine(), "Please input a valid barangay name: ");
		address.setBarangay(barangay);

		System.out.print("City: ");
		String city = isValidWord(in.nextLine(), "Please input a valid city name: ");
		address.setCity(city);

		System.out.print("Zip code: ");
		String zipCode = isValidNumber(in.nextLine(), "Please input a valid zip code: ");
		address.setZipCode(zipCode);

		return address;
	}

	public void addContactById() {
		if(hasAvailablePerson()) {
			displayShortPersonsTable(appServiceManager.getAllPersons());
			String personId = getValidPersonId();
			Contact contact = createContact();
			appServiceManager.addPersonContactById(Integer.parseInt(personId), contact);
			System.out.println("Contact succesfully added!");
		}
	}

	private Contact createContact() {
		System.out.println("Adding Contact Info...");
		String value = "";

		System.out.print("Landline, Mobile, or E-mail (L/M/E): ");
		String contactType = in.nextLine();
		while(!(contactType.matches("[lmeLME]$")) || contactType.isEmpty()) {
			System.out.print("(L/M/E) only: ");
			contactType = in.nextLine();
		} 

		if(contactType.equalsIgnoreCase("l") || contactType.equalsIgnoreCase("m")) {
			System.out.print("Number: ");
			value = isValidNumber(in.nextLine(), "Please input a valid contact number: ");
		}
		else {
			System.out.print("E-mail: ");
			value = in.nextLine();
			while(!(value.matches("([_0-9a-zA-Z-]+)(\\.[0-9a-zA-Z]+)*\\@[0-9a-zA-Z]+(\\.[0-9a-zA-Z]+)*(\\.[0-9a-zA-Z]{2,})$")) || value.isEmpty()) {
				System.out.print("Enter a valid E-mail address: ");
				value = in.nextLine();
			}
		}
		return new Contact(convertToTypeEnum(contactType), value);
	}

	private Set<Contact> createContacts() {
		boolean toAddContact = true;														
		Set<Contact> contacts = new HashSet<>();
		while(toAddContact) {
			contacts.add(createContact());
			System.out.print("Add another contact (y/n): ");
			String choice = in.nextLine();
			while(!(choice.matches("[ynYN]$")) || choice.isEmpty()) {
				System.out.print("(y/n) only: ");
				choice = in.nextLine();
			}
			if(choice.equalsIgnoreCase("y")) {
				toAddContact = true;
			}
			else {
				toAddContact = false;
			}
		}
		return contacts;
	}

	public void updateContactById() {
		if(hasAvailablePerson()) {
			displayShortPersonsTable(appServiceManager.getAllPersons());
			String personId = getValidPersonId();
			if(hasAvailableContact(personId)) {
				Set<Contact> contacts = appServiceManager.getContactsByPersonId(Integer.parseInt(personId));
				displayContactsTable(contacts);
		        String contactId = getValidPersonContactId(personId);

				Contact contact = createContact();
				appServiceManager.updatePersonContactById(Integer.parseInt(contactId), contact);
				System.out.println("Contact succesfully updated!");
			}
		}
	}

	public void deleteContactById() {
		if(hasAvailablePerson()) {
			displayShortPersonsTable(appServiceManager.getAllPersons());
			String personId = getValidPersonId();
			if(hasAvailableContact(personId)) {
				Set<Contact> contacts = appServiceManager.getContactsByPersonId(Integer.parseInt(personId));
				displayContactsTable(contacts);
		        String contactId = getValidPersonContactId(personId);

				appServiceManager.deletePersonContactById(Integer.parseInt(contactId));
				System.out.println("Contact successfully deleted!");
			}
		}
	}

	private void displayPersonsTable(List<Person> persons) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println(String.format("%-5s%-30s%-5s%-40s%-12s%-5s%-9s%-10s%-1s","|p_id","|full_name","|a_id","|complete_address","|birthdate","|gwa","|date_hired","|employed","|contacts","|"));
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
		for (Person p : persons) {
			Address address = p.getAddress();
			Set<Contact> contacts = p.getContacts();
			String completeAddress = address.getStreetNumber() + " " + address.getBarangay() + " " + address.getCity() + " " + address.getZipCode();
			String completeContact = "";
			for(Contact c : contacts) {
				completeContact = completeContact + " " + c.getContactType() + ":" + c.getValue();
			}
			System.out.println(String.format("%-5s%-30s%-5s%-40s%-12s%-5s%-9s%-10s%-1s","|"+p.getId(),"|"+p.getFullName(),"|"+p.getAddress().getId(),"|"+completeAddress,"|"+dateFormat.format(p.getBirthdate()),"|"+p.getGwa(),"|"+dateFormat.format(p.getDateHired()),"|"+p.isEmployed(),"|"+completeContact,"|"));
		}
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
	}

	private void displayShortPersonsTable(List<Person> persons) {
		System.out.println("------------------------------------");		
		System.out.println(String.format("%-5s%-30s%-1s","|p_id","|full_name","|"));
		System.out.println("------------------------------------");		
		for (Person p : persons) {
			System.out.println(String.format("%-5s%-30s%-1s","|"+p.getId(),"|"+p.getFullName(),"|"));	
		}
		System.out.println("------------------------------------");		
	}

	private void displayContactsTable(Set<Contact> contacts) {
		System.out.println("--------------------------------------------------------");		
		System.out.println(String.format("%-5s%-15s%-35s%-1s","|c_id","|contact_type","|value","|"));
		System.out.println("--------------------------------------------------------");		
		for (Contact c : contacts){
			System.out.println(String.format("%-5s%-15s%-35s%-1s","|"+c.getId(),"|"+c.getContactType(),"|"+c.getValue(),"|"));	
		}
		System.out.println("--------------------------------------------------------");		
	}

	private ContactType convertToTypeEnum(String type) {
		if(type.equalsIgnoreCase("l")) {
			return ContactType.Landline;
		} else if(type.equalsIgnoreCase("m")) {
			return ContactType.Mobile;
		} else {
			return ContactType.Email;
		}	
	}

	private String isValidWord(String name, String errMsg) {
		while(!(name.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]{2,}$)*")) || name.isEmpty()) {
            System.out.print(errMsg);
            name = in.nextLine();
        }
    	return name;
	}

	private String isValidNumber(String number, String errMsg) {
		while(!(number.matches("\\d+$")) || number.isEmpty()) {
            System.out.print(errMsg);
            number = in.nextLine();
        }
    	return number;
	}

	private String isValidDate(String date) {
		while(!(date.matches("((19|20)\\d\\d)/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])$")) || date.isEmpty()) {
		    System.out.print("Please enter a valid date format (yyyy/mm/dd): ");
            date = in.nextLine();
        }
    	return date;
	}

	private String isValidPersonId(String id) {
		Set<Integer> personIds = appServiceManager.getPersonIds();
		while(!(id.matches("\\d+$")) || id.isEmpty() || !(personIds.toString().contains(id))) {
			System.out.print("Please enter a valid id: ");
			id = in.nextLine();
		}
		return id;
	}

	private String isValidPersonContactId(String contactId, String personId) {
		Set<Integer> contactIds = appServiceManager.getPersonContactIds(Integer.parseInt(personId));
		while(!(contactId.matches("\\d+$")) || contactId.isEmpty() || !(contactIds.toString().contains(contactId))) {
			System.out.print("Please enter a valid id: ");
			contactId = in.nextLine();
		}
		return contactId;
	}

	private String getValidPersonId() {
		System.out.print("Person id: ");									
		String id = isValidPersonId(in.nextLine());
		return id;
	}

	private String getValidPersonContactId(String personId) {
		System.out.print("Contact id: ");									
		String contactId = isValidPersonContactId(in.nextLine(), personId);
		return contactId;
	}

	private boolean hasAvailablePerson() {
		List<Person> persons = appServiceManager.getAllPersons();
		if(persons.isEmpty()) {
			System.out.println("Transaction Failed. No person available. Please add first.");
			return false;
		} else {
			return true;
		}
	}

	private boolean hasAvailableContact(String personId) {
		Set<Integer> contactIds = appServiceManager.getPersonContactIds(Integer.parseInt(personId));
		if(contactIds.isEmpty()) {
			System.out.println("Transaction Failed. No contact available for this person. Please add first.");
			return false;
		} else {
			return true;
		}
	}

}		
