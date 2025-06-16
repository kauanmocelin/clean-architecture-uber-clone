package dev.kauanmocelin;

import dev.kauanmocelin.application.OutputGetAccount;
import dev.kauanmocelin.application.usecase.GetAccount;
import dev.kauanmocelin.application.usecase.Signup;
import dev.kauanmocelin.dto.SignupRequestInputDTO;
import dev.kauanmocelin.dto.SignupResponseOutput;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
class SignupTest {

    @Inject
    Signup signup;
    @Inject
    GetAccount getAccount;

    @Test
    void shouldCreateAnAccountToPassengerApplication() throws SQLException {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
            "John Doe",
            "johndoe" + Math.random() + "@gmaill.com",
            "26123453025",
            "",
            true,
            false);
        SignupResponseOutput signupResponseOutput = signup.execute(signupRequestInputDTO);
        assertThat(signupResponseOutput.accountId()).isNotEmpty();
        OutputGetAccount outputGetAccount = getAccount.execute(signupResponseOutput.accountId());
        assertThat(outputGetAccount.name()).isEqualTo(signupRequestInputDTO.getName());
        assertThat(outputGetAccount.email()).isEqualTo(signupRequestInputDTO.getEmail());
        assertThat(outputGetAccount.cpf()).isEqualTo(signupRequestInputDTO.getCpf());
    }

    @Test
    void shouldCreateAnAccountToDriverApplication() throws SQLException {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
            "John Doe",
            "johndoe" + Math.random() + "@gmaill.com",
            "26123453025",
            "AAA9999",
            false,
            true);
        SignupResponseOutput signupResponseOutput = signup.execute(signupRequestInputDTO);
        assertThat(signupResponseOutput.accountId()).isNotEmpty();
        OutputGetAccount outputGetAccount = getAccount.execute(signupResponseOutput.accountId());
        assertThat(outputGetAccount.name()).isEqualTo(signupRequestInputDTO.getName());
        assertThat(outputGetAccount.email()).isEqualTo(signupRequestInputDTO.getEmail());
        assertThat(outputGetAccount.cpf()).isEqualTo(signupRequestInputDTO.getCpf());
        assertThat(outputGetAccount.carPlate()).isEqualTo(signupRequestInputDTO.getCarPlate());
    }

    @Test
    void shouldNotCreateAnAccountToPassengerWhenNameIsInvalidApplication() throws SQLException {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
            "John",
            "johndoe" + Math.random() + "@gmaill.com",
            "26123453025",
            "AAA9999",
            true,
            false);
        assertThatThrownBy(() -> signup.execute(signupRequestInputDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid name");
    }
}