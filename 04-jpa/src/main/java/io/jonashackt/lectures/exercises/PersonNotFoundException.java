package io.jonashackt.lectures.exercises;

public class PersonNotFoundException extends Throwable {
    public PersonNotFoundException(String message) {
        super(message);
    }
}
