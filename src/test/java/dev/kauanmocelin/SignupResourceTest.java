package dev.kauanmocelin;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.internal.http.Status;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
class SignupResourceTest {

    @Test
    void shouldCreateAnAccountToPassenger() {
        final SignupRequestInput signupRequestInput = new SignupRequestInput("John Doe", "johndoe@gmaill.com", "26123453025", "", true, false);

        String outputSignupAccountId =  given()
            .contentType(ContentType.JSON)
            .body(JsonbBuilder.create().toJson(signupRequestInput))
        .when()
            .post("/api/signup")
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("accountId", notNullValue())
            .extract().path("accountId");

        given()
            .contentType(ContentType.JSON)
            .pathParam("accountId", outputSignupAccountId)
        .when()
            .get("/api/accounts/{accountId}")
        .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("name", equalTo( signupRequestInput.name()))
            .body("email", equalTo( signupRequestInput.email()))
            .body("cpf", equalTo( signupRequestInput.cpf()));
    }
}