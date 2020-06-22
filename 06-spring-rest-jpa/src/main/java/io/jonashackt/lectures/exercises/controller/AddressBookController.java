package io.jonashackt.lectures.exercises.controller;

import io.jonashackt.lectures.exercises.domain.Person;
import io.jonashackt.lectures.exercises.storage.AddressbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class AddressBookController {

    protected static final String HEALTH_RESPONSE = "Yep, I'am here!";

    @Autowired AddressbookRepository addressbookRepository;

    @GetMapping("/health")
    public String areYouAlive() {
        return HEALTH_RESPONSE;
    }

    @RequestMapping(path = "/person/{lastName}/{firstName}/{email}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Person save(@PathVariable("lastName") String lastName, @PathVariable("firstName") String firstName, @PathVariable("email") String email) {
        Person person = new Person(firstName, lastName, email);
        return addressbookRepository.save(person);
    }
}
