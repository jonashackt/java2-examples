package io.jonashackt.lectures.exercises.controller;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

}