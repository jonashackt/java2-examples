package io.jonashackt.lectures.exercises.storage;

import io.jonashackt.lectures.exercises.domain.Person;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataController {

    private static final String PERSISTENCE_UNIT_NAME = "io.jonashackt.lectures.addressbook";
    private EntityManagerFactory entityManagerFactory;
    private static DataController instance;

    public static DataController getInstance() {
        if(instance == null) {
            instance = new DataController();
        }
        return instance;
    }

    private DataController() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public GenericDAO<Person, Long> getPersonDao() {
        return new GenericDAO<Person, Long>(Person.class, this.entityManagerFactory.createEntityManager() );
    }
}
