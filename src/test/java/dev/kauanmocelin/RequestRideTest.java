package dev.kauanmocelin;

import dev.kauanmocelin.application.usecase.GetRide;
import dev.kauanmocelin.application.usecase.RequestRide;
import dev.kauanmocelin.application.usecase.Signup;
import dev.kauanmocelin.dto.*;
import dev.kauanmocelin.infra.database.DatabaseConnection;
import dev.kauanmocelin.infra.gateway.MailerGatewayMemory;
import dev.kauanmocelin.infra.repository.AccountRepository;
import dev.kauanmocelin.infra.repository.AccountRepositoryDatabase;
import dev.kauanmocelin.infra.repository.RideRepository;
import dev.kauanmocelin.infra.repository.RideRepositoryDatabase;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
class RequestRideTest {

    @Inject
    DatabaseConnection databaseConnection;
    @Inject
    DataSource dataSource;

    @Test
    void shouldRequestRideWhenSuccessfulSignedUp() {
        AccountRepository accountRepository = new AccountRepositoryDatabase(databaseConnection);
        RideRepository rideRepository = new RideRepositoryDatabase(dataSource);
        MailerGatewayMemory mailerGateway = new MailerGatewayMemory();
        Signup signup = new Signup(accountRepository, mailerGateway);
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
            "John Doe",
            "johndoe" + Math.random() + "@gmaill.com",
            "26123453025",
            "",
            true,
            false);
        SignupResponseOutput signupResponseOutput = signup.execute(signupRequestInputDTO);
        RequestRide requestRide = new RequestRide(accountRepository, rideRepository);
        InputRequestRide inputRequestRide = new InputRequestRide(
            signupResponseOutput.accountId(),
            BigDecimal.valueOf(-27.584905257808835),
            BigDecimal.valueOf(-48.545022195325124),
            BigDecimal.valueOf(-27.496887588317275),
            BigDecimal.valueOf(-48.522234807851476)
        );
        OutputRequestRide outputRequestRide = requestRide.execute(inputRequestRide);
        assertThat(outputRequestRide.rideId()).isNotNull();
        GetRide getRide = new GetRide(accountRepository, rideRepository);
        InputGetRide inputGetRide = new InputGetRide(outputRequestRide.rideId());
        OutputGetRide outputGetRide = getRide.execute(inputGetRide);
        assertThat(outputGetRide.status()).isEqualTo("requested");
        assertThat(outputGetRide.passengerId()).isEqualTo(UUID.fromString(signupResponseOutput.accountId()));
        assertThat(outputGetRide.fromLat()).isEqualTo(inputRequestRide.fromLat());
        assertThat(outputGetRide.fromLong()).isEqualTo(inputRequestRide.fromLong());
        assertThat(outputGetRide.passengerName()).isEqualTo("John Doe");
        assertThat(outputGetRide.passengerEmail()).isEqualTo(signupRequestInputDTO.getEmail());
    }

    @Test
    void shouldFailToRequestRideWhenPassegerHasAnRideActive() {
        AccountRepository accountRepository = new AccountRepositoryDatabase(databaseConnection);
        RideRepository rideRepository = new RideRepositoryDatabase(dataSource);
        MailerGatewayMemory mailerGateway = new MailerGatewayMemory();
        Signup signup = new Signup(accountRepository, mailerGateway);
        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
            "John Doe",
            "johndoe" + Math.random() + "@gmaill.com",
            "26123453025",
            "",
            true,
            false);
        SignupResponseOutput signupResponseOutput = signup.execute(signupRequestInputDTO);
        RequestRide requestRide = new RequestRide(accountRepository, rideRepository);
        InputRequestRide inputRequestRide = new InputRequestRide(
            signupResponseOutput.accountId(),
            BigDecimal.valueOf(-27.584905257808835),
            BigDecimal.valueOf(-48.545022195325124),
            BigDecimal.valueOf(-27.496887588317275),
            BigDecimal.valueOf(-48.522234807851476)
        );
        requestRide.execute(inputRequestRide);
        assertThatThrownBy(() -> requestRide.execute(inputRequestRide))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Passenger has an active ride");
    }

    @Test
    void shouldFailToRequestRideWhenNotPassenger() {
        AccountRepository accountRepository = new AccountRepositoryDatabase(databaseConnection);
        RideRepository rideRepository = new RideRepositoryDatabase(dataSource);
        MailerGatewayMemory mailerGateway = new MailerGatewayMemory();
        Signup signup = new Signup(accountRepository, mailerGateway);

        final SignupRequestInputDTO signupRequestInputDTO = new SignupRequestInputDTO(
            "John Doe",
            "johndoe" + Math.random() + "@gmaill.com",
            "26123453025",
            "AAA9999",
            false,
            true);
        SignupResponseOutput signupResponseOutput = signup.execute(signupRequestInputDTO);
        RequestRide requestRide = new RequestRide(accountRepository, rideRepository);
        InputRequestRide inputRequestRide = new InputRequestRide(
            signupResponseOutput.accountId(),
            new BigDecimal(-27.584905257808835),
            new BigDecimal(-48.545022195325124),
            new BigDecimal(-27.496887588317275),
            new BigDecimal(-48.522234807851476)
        );
        assertThatThrownBy(() -> requestRide.execute(inputRequestRide))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Account is not from a passenger");
    }
}
