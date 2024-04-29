package dev.kauanmocelin;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
class SignupResourceTest {

    @Inject
    SignupResource signupResource;

    @Test
    void shouldSaveARegistryOnAccountTableAndQueryById() {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
                UUID.randomUUID(),
                "John Doe",
                "johndoe@gmaill.com",
                "26123453025",
                "",
                true,
                false);
        signupResource.saveAccount(signupRequestInputDTO);
        SignupDatabaseDTO accountById= signupResource.getAccountById(signupRequestInputDTO.getUuid().toString());
        assertThat(accountById.account_id()).isEqualTo(signupRequestInputDTO.getUuid().toString());
        assertThat(accountById.name()).isEqualTo(signupRequestInputDTO.getName());
        assertThat(accountById.email()).isEqualTo(signupRequestInputDTO.getEmail());
        assertThat(accountById.cpf()).isEqualTo(signupRequestInputDTO.getCpf());
    }

    @Test
    void shouldSaveARegistryOnAccountTableAndQueryByEmail() {
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
                UUID.randomUUID(),
                "John Doe",
                "johndoe@gmaill.com",
                "26123453025",
                "",
                true,
                false);
        signupResource.saveAccount(signupRequestInputDTO);
        SignupDatabaseDTO accountById= signupResource.getAccountByEmail(signupRequestInputDTO.getEmail());
        assertThat(accountById.account_id()).isEqualTo(signupRequestInputDTO.getUuid().toString());
        assertThat(accountById.name()).isEqualTo(signupRequestInputDTO.getName());
        assertThat(accountById.email()).isEqualTo(signupRequestInputDTO.getEmail());
        assertThat(accountById.cpf()).isEqualTo(signupRequestInputDTO.getCpf());
    }
}