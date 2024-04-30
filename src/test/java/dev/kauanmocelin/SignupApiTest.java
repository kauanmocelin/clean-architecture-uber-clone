package dev.kauanmocelin;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
class SignupApiTest {

    @Test
    void shouldCreateAnAccountToPassenger() {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO("John Doe", "johndoe@gmaill.com", "26123453025", "", true, false);

        String outputSignupAccountId = given()
                .contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(signupRequestInputDTO))
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
                .body("name", equalTo(signupRequestInputDTO.getName()))
                .body("email", equalTo(signupRequestInputDTO.getEmail()))
                .body("cpf", equalTo(signupRequestInputDTO.getCpf()));
    }

    @Test
    void shouldCreateAnAccountToDriver() {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
                "John Doe",
                "johndoe"+Math.random()+"@gmaill.com",
                "26123453025",
                "AAA9999",
                false,
                true);
        String outputSignupAccountId =  given()
                .contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(signupRequestInputDTO))
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
                .body("name", equalTo( signupRequestInputDTO.getName()))
                .body("email", equalTo( signupRequestInputDTO.getEmail()))
                .body("cpf", equalTo( signupRequestInputDTO.getCpf()))
                .body("car_plate", equalTo( signupRequestInputDTO.getCarPlate()));
    }

    @Test
    void shouldNotCreateAnAccountToPassengerWhenNameIsInvalid() {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
                "John",
                "johndoe"+Math.random()+"@gmaill.com",
                "26123453025",
                "",
                true,
                false);
        given()
                .contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(signupRequestInputDTO))
                .when()
                .post("/api/signup")
                .then()
                .statusCode(422)
                .body(equalTo("-3"));
    }

    @Test
    void shouldNotCreateAnAccountToPassengerWhenEmailIsInvalid() {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
                "John Doe",
                "johndoe"+Math.random(),
                "26123453025",
                "",
                true,
                false);
        given()
                .contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(signupRequestInputDTO))
                .when()
                .post("/api/signup")
                .then()
                .statusCode(422)
                .body(equalTo("-2"));
    }

    @Test
    void shouldNotCreateAnAccountToPassengerWhenCpfIsInvalid() {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
                "John Doe",
                "johndoe"+Math.random()+"@gmaill.com",
                "2612345",
                "",
                true,
                false);
        given()
                .contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(signupRequestInputDTO))
                .when()
                .post("/api/signup")
                .then()
                .statusCode(422)
                .body(equalTo("-1"));
    }

    @Test
    void shouldNotCreateAnAccountToDriverWhenCarPlateIsInvalid() {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
                "John Doe",
                "johndoe"+Math.random()+"@gmaill.com",
                "26123453025",
                "AAA55",
                false,
                true);
        given()
                .contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(signupRequestInputDTO))
                .when()
                .post("/api/signup")
                .then()
                .statusCode(422)
                .body(equalTo("-5"));
    }

    @Test
    void shouldNotCreateAnAccountToPassengerWhenEmailAlreadyExists() {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
                "John Doe",
                "johndoe"+Math.random()+"@gmaill.com",
                "26123453025",
                "",
                true,
                false);
        given()
                .contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(signupRequestInputDTO))
                .when()
                .post("/api/signup");
        given()
                .contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(signupRequestInputDTO))
                .when()
                .post("/api/signup")
                .then()
                .statusCode(422)
                .body(equalTo("-4"));
    }
}