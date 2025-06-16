package dev.kauanmocelin;

import dev.kauanmocelin.dto.SignupRequestInputDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
class AccountControllerTest {

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
                .body("name", is(signupRequestInputDTO.getName()))
                .body("email", is(signupRequestInputDTO.getEmail()))
                .body("cpf", is(signupRequestInputDTO.getCpf()));
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
                .body("name", is( signupRequestInputDTO.getName()))
                .body("email", is( signupRequestInputDTO.getEmail()))
                .body("cpf", is( signupRequestInputDTO.getCpf()))
                .body("carPlate", is( signupRequestInputDTO.getCarPlate()));
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
                .body("message", is("Invalid name"));
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
                .body("message", is("Invalid email"));
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
                .body("message", is("Invalid cpf"));
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
                .body("message", is("Invalid car plate"));
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
                .body("message", is("Account already exists"));
    }
}