package com.altice;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class SequenceResourceTest {

    @Test
    void testLabseqEndpoint() {
        given()
          .when().get("/labseq/5")
          .then()
             .statusCode(200)
             .body("value", is("1")); 
    }
    
    @Test
    void testInvalidInput() {
        given()
          .when().get("/labseq/-1")
          .then()
             .statusCode(400);
    }
}