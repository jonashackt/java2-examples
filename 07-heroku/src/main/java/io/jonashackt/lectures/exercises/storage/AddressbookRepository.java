package io.jonashackt.lectures.exercises.storage;

import io.jonashackt.lectures.exercises.domain.Person;
import org.springframework.data.repository.CrudRepository;

public interface AddressbookRepository extends CrudRepository<Person, Long> {

    Person save(Person person);
}
