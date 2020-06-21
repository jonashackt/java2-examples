package io.jonashackt.lectures.exercises.storage;

import io.jonashackt.lectures.exercises.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressbookRepository {

    private static final Logger LOG = LoggerFactory.getLogger(AddressbookRepository.class);

    public void savePerson(Person person) throws StorageError {

        GenericDAO<Person, Long> personDao = DataController.getInstance().getPersonDao();
        if(person.getId() == null) {
            personDao.create(person);
        } else {
            personDao.update(person);
        }
    }

    public Person readPerson(Long id) throws StorageError {

        GenericDAO<Person, Long> personDao = DataController.getInstance().getPersonDao();
        return personDao.findById(id);
    }
}
