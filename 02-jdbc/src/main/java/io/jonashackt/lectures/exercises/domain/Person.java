package io.jonashackt.lectures.exercises.domain;

public class Person implements Comparable<Person>{
    private final String firstName;

    public String getFirstName() {
        return firstName;
    }

    public String geteMail() {
        return eMail;
    }

    private final String lastName;
    private final String eMail;

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
}
