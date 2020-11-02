package io.jonashackt.lectures.exercises.controller;

import io.jonashackt.lectures.exercises.domain.Person;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressBookControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    AddressBookController addressBookController;

    @BeforeEach
    public void init() {
        RestAssured.baseURI = "http://localhost/api ";
        RestAssured.port = port;
    }

    Person luis = new Person("Luis", "Meyer", "luis.meyer@web.de");

    @Test
    public void isLuisSavedAndRetrieved() {

        // Table Person is automatically created by JPA Persistence Provider

        Person isThisLuis = savePersonViaApi(luis);

        assertEquals("Luis", isThisLuis.getFirstName());
        assertEquals("Meyer", isThisLuis.getLastName());
        assertEquals("luis.meyer@web.de",isThisLuis.geteMail());
    }

    @Test
    public void getSavedLuis() {

        Person isThisLuis = savePersonViaApi(luis);

        Person thisIsLuis = getPersonViaApi(isThisLuis.getId());

        assertEquals("Luis", thisIsLuis.getFirstName());
        assertEquals("Meyer", thisIsLuis.getLastName());
        assertEquals("luis.meyer@web.de", thisIsLuis.geteMail());
    }

    private Person getPersonViaApi(Long personId) {
        return when()
                .get("/person/" + personId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body().as(Person.class);
    }

    private Person savePersonViaApi(Person person) {
        return when()
                .post("/person/" + person.getLastName() + "/" + person.getFirstName() + "/" + person.geteMail())
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .body().as(Person.class);
    }

}