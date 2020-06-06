package io.jonashackt.lectures.exercises;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;

public class AddressBook {
    private static final Logger LOG = LoggerFactory.getLogger(AddressBook.class);

    private ArrayList<Person> persons = new ArrayList<>();

    public void addContact(Person person) {
        LOG.debug("Adding Person to AddressBook: " + person.toString());
        this.persons.add(person);
        Collections.sort(persons);
    }

    public Person getContactByLastName(String lastName) throws PersonNotFoundException {
        for(Person person : persons) {
            if(person.getLastName().equals(lastName)) {
                return person;
            }
        }
        String errorMessage = "Person with the last name '" + lastName + "' could not be found!";
        LOG.error(errorMessage);
        throw new PersonNotFoundException(errorMessage);
    }

    public int getSize() {
        LOG.info("There are currently " + persons.size() + " persons in the address book.");
        return persons.size();
    }

    public Person getPersonByIndex(int index) {
        Person person = persons.get(index);
        LOG.info("Person " + person.toString() + " is currently stored at index number " + index);
        return person;
    }

    public long getFrequencyOfLastName(String lastName) {
        LOG.debug("Let's find out, how many persons with last name " + lastName + " we have in our address book right now!");
        return persons.stream().map(Person::getLastName).filter(lastName::equals).count();

    }
}
