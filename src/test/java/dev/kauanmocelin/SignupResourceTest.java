package dev.kauanmocelin;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.json.bind.JsonbBuilder;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
class SignupResourceTest {

    @Test
    void shouldCreateAnAccountToPassenger() {
        final SignupRequestInput signupRequestInput = new SignupRequestInput("John Doe", "johndoe@gmaill.com", "26123453025", "", true, false);
        given()
            .contentType(ContentType.JSON)
            .body(JsonbBuilder.create().toJson(signupRequestInput))
        .when()
            .post("/signup")
        .then()
            .contentType(ContentType.JSON)
            .statusCode(200)
            .body("accountId", notNullValue());
    }
}