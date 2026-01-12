package dev.kauanmocelin;

import dev.kauanmocelin.application.gateway.PaymentGateway;
import dev.kauanmocelin.application.gateway.PaymentGatewayHttp;
import dev.kauanmocelin.application.gateway.PaymentProcessorClient;
import dev.kauanmocelin.application.usecase.*;
import dev.kauanmocelin.dto.*;
import dev.kauanmocelin.infra.database.DatabaseConnection;
import dev.kauanmocelin.infra.gateway.MailerGatewayMemory;
import dev.kauanmocelin.infra.repository.*;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class FinishRideTest {

    @Inject
    DatabaseConnection databaseConnection;
    @Inject
    DataSource dataSource;
    @RestClient
    PaymentProcessorClient paymentProcessorClient;

    @Test
    void shouldFinishARideWhenSuccessful() {
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
        StartRide startRide = new StartRide(rideRepository);
        startRide.execute(new InputStartRide(outputRequestRide.rideId()));

        UpdatePosition updatePosition1 = new UpdatePosition(positionRepository, rideRepository);
        updatePosition1.execute(new InputUpdatePosition(
            outputRequestRide.rideId(),
            BigDecimal.valueOf(-27.584905257808835),
            BigDecimal.valueOf(-48.545022195325124)));

        UpdatePosition updatePosition2 = new UpdatePosition(positionRepository, rideRepository);
        updatePosition2.execute(new InputUpdatePosition(
            outputRequestRide.rideId(),
            BigDecimal.valueOf(-27.496887588317275),
            BigDecimal.valueOf(-48.522234807851476)));

        PaymentGateway paymentGateway = new PaymentGatewayHttp(paymentProcessorClient);
        FinishRide finishRide = new FinishRide(rideRepository, paymentGateway);
        InputFinishRide inputFinishRide = new InputFinishRide(outputRequestRide.rideId());
        finishRide.execute(inputFinishRide);

        GetRide getRide = new GetRide(accountRepository, rideRepository, positionRepository);
        InputGetRide inputGetRide = new InputGetRide(outputRequestRide.rideId());
        OutputGetRide outputGetRide = getRide.execute(inputGetRide);
        assertThat(outputRequestRide.rideId()).isNotNull();
        assertThat(outputGetRide.fare()).isEqualTo(new BigDecimal(21));
        assertThat(outputGetRide.status()).isEqualTo("completed");
        assertThat(outputGetRide.distance()).isEqualTo(new BigDecimal(10));
    }
}
