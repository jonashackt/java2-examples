package io.jonashackt.lectures.exercises.controller;

import io.jonashackt.lectures.exercises.domain.Person;
import io.jonashackt.lectures.exercises.storage.AddressbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AddressBookController {

    @Autowired AddressbookRepository addressbookRepository;

    @GetMapping(path = "/persons")
    public Iterable<Person> get(Model model) {

        Iterable<Person> persons = addressbookRepository.findAll();

        model.addAttribute("persons", persons);

        return persons;
    }

    @GetMapping(path = "/add")
    public String showAddPersonForm(Model model) {
        return "add";
    }

    @PostMapping(path = "/addperson")
    public String addPerson(@Valid Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add";
        }

        addressbookRepository.save(person);

        model.addAttribute("person", person);
        model.addAttribute("persons", addressbookRepository.findAll());

        return "redirect:/index";
    }
}
