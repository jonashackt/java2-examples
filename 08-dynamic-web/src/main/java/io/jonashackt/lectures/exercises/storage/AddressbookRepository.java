package io.jonashackt.lectures.exercises.storage;

import io.jonashackt.lectures.exercises.domain.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressbookRepository extends CrudRepository<Person, Long> {

    Person save(Person person);

    Optional<Person> findById(Long id);

    Iterable<Person> findAll();
}
