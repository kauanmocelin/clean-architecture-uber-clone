package dev.kauanmocelin;

import dev.kauanmocelin.application.usecase.AcceptRide;
import dev.kauanmocelin.application.usecase.GetRide;
import dev.kauanmocelin.application.usecase.RequestRide;
import dev.kauanmocelin.application.usecase.Signup;
import dev.kauanmocelin.dto.*;
import dev.kauanmocelin.infra.database.DatabaseConnection;
import dev.kauanmocelin.infra.gateway.MailerGatewayMemory;
import dev.kauanmocelin.infra.repository.*;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class AcceptRideTest {

    @Inject
    DatabaseConnection databaseConnection;
    @Inject
    DataSource dataSource;

    @Test
    void shouldAcceptRideWhenSuccessfulSignedUp() {
        AccountRepository accountRepository = new AccountRepositoryDatabase(databaseConnection);
        RideRepository rideRepository = new RideRepositoryDatabase(dataSource);
        PositionRepository positionRepository = new PositionRepositoryDatabase(databaseConnection);
        MailerGatewayMemory mailerGateway = new MailerGatewayMemory();
        Signup signup = new Signup(accountRepository, mailerGateway);
        final SignupRequestInputDTO inputSignupPassenger = new SignupRequestInputDTO(
            "John Doe",
            "johndoe" + Math.random() + "@gmaill.com",
            "26123453025",
            "",
            true,
            false);
        SignupResponseOutput outputSignupPassenger = signup.execute(inputSignupPassenger);
        final SignupRequestInputDTO inputSignupDriver = new SignupRequestInputDTO(
            "John Doe",
            "johndoe" + Math.random() + "@gmaill.com",
            "26123453025",
            "AAA9999",
            false,
            true);
        SignupResponseOutput outputSignupDriver = signup.execute(inputSignupDriver);
        RequestRide requestRide = new RequestRide(accountRepository, rideRepository);
        InputRequestRide inputRequestRide = new InputRequestRide(
            outputSignupPassenger.accountId(),
            BigDecimal.valueOf(-27.584905257808835),
            BigDecimal.valueOf(-48.545022195325124),
            BigDecimal.valueOf(-27.496887588317275),
            BigDecimal.valueOf(-48.522234807851476)
        );
        OutputRequestRide outputRequestRide = requestRide.execute(inputRequestRide);
        AcceptRide acceptRide = new AcceptRide(accountRepository, rideRepository);
        acceptRide.execute(new InputAcceptRide(outputRequestRide.rideId(), outputSignupDriver.accountId()));
        GetRide getRide = new GetRide(accountRepository, rideRepository, positionRepository);
        InputGetRide inputGetRide = new InputGetRide(outputRequestRide.rideId());
        OutputGetRide outputGetRide = getRide.execute(inputGetRide);
        assertThat(outputRequestRide.rideId()).isNotNull();
        assertThat(outputGetRide.status()).isEqualTo("accepted");
        assertThat(outputGetRide.driverName()).isEqualTo("John Doe");
    }
}
