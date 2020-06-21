package io.jonashackt.lectures.exercises.domain;

import javax.persistence.*;

@Entity
@Table(name = "PERSON")
public class Person implements Comparable<Person> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    // Keine Verwendung von final mehr möglich mit JPA
    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public String geteMail() {
        return eMail;
    }

    private String lastName;
    private String eMail;

    // Argumentloser Konstruktor ist Anforderung für JPA
    public Person() {};

    public Person(String firstName, String lastName, String eMail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public int compareTo(Person person) {
        return this.lastName.compareTo(person.getLastName());
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " (" + this.eMail + ")";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
