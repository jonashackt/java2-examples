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

    @Autowired AddressBookController addressBookController;

    @BeforeEach
    public void init() {
        RestAssured.baseURI = "http://localhost/api";
        RestAssured.port = port;
    }

    @Test
    public void apiAvailable() {
        when()
            .get("/health")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .assertThat()
                .body(is(equalTo(AddressBookController.HEALTH_RESPONSE)));
    }

    @Test
    public void isLuisSavedAndRetrieved() {

        // Table Person is automatically created by JPA Persistence Provider
        Person luis = new Person("Luis", "Meyer", "luis.meyer@web.de");

        Person isThisLuis = when()
                                .post("/person/Meyer/Luis/luis.meyer@web.de")
                            .then()
                                .statusCode(HttpStatus.SC_CREATED)
                                .extract()
                                    .body().as(Person.class);

        assertEquals("Luis", isThisLuis.getFirstName());
        assertEquals("Meyer", isThisLuis.getLastName());
        assertEquals("luis.meyer@web.de",isThisLuis.geteMail());
    }

}