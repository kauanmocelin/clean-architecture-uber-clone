package dev.kauanmocelin;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
class SignupApplicationTest {

    @Inject
    SignupApplication signupApplication;

    @Test
    void shouldCreateAnAccountToPassengerApplication() throws SQLException {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
                "John Doe",
                "johndoe"+Math.random()+"@gmaill.com",
                "26123453025",
                "",
                true,
                false);
        String outputSignup = signupApplication.signup(signupRequestInputDTO);
        var accountId = new JsonPath(outputSignup).getString("accountId");
        assertThat(accountId).isNotEmpty();
        SignupDatabaseDTO outputGetAccount = signupApplication.getAccount(UUID.fromString(accountId));
        assertThat(outputGetAccount.name()).isEqualTo(signupRequestInputDTO.getName());
        assertThat(outputGetAccount.email()).isEqualTo(signupRequestInputDTO.getEmail());
        assertThat(outputGetAccount.cpf()).isEqualTo(signupRequestInputDTO.getCpf());
    }

    @Test
    void shouldCreateAnAccountToDriverApplication() throws SQLException {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
                "John Doe",
                "johndoe"+Math.random()+"@gmaill.com",
                "26123453025",
                "AAA9999",
                false,
                true);
        String outputSignup = signupApplication.signup(signupRequestInputDTO);
        var accountId = new JsonPath(outputSignup).getString("accountId");
        assertThat(accountId).isNotEmpty();
        SignupDatabaseDTO outputGetAccount = signupApplication.getAccount(UUID.fromString(accountId));
        assertThat(outputGetAccount.name()).isEqualTo(signupRequestInputDTO.getName());
        assertThat(outputGetAccount.email()).isEqualTo(signupRequestInputDTO.getEmail());
        assertThat(outputGetAccount.cpf()).isEqualTo(signupRequestInputDTO.getCpf());
        assertThat(outputGetAccount.car_plate()).isEqualTo(signupRequestInputDTO.getCarPlate());
    }

    @Test
    void shouldNotCreateAnAccountToPassengerWhenNameIsInvalidApplication() throws SQLException {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
                "John",
                "johndoe"+Math.random()+"@gmaill.com",
                "26123453025",
                "AAA9999",
                true,
                false);
        assertThatThrownBy(() -> signupApplication.signup(signupRequestInputDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid name");
    }
}