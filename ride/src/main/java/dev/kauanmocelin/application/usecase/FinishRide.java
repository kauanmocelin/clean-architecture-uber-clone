package dev.kauanmocelin.application.usecase;

import dev.kauanmocelin.application.gateway.InputProcessPayment;
import dev.kauanmocelin.application.gateway.PaymentGateway;
import dev.kauanmocelin.domain.entity.Ride;
import dev.kauanmocelin.dto.InputFinishRide;
import dev.kauanmocelin.infra.repository.RideRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class FinishRide {

    private final RideRepository rideRepository;
    private final PaymentGateway paymentGateway;

    public FinishRide(RideRepository rideRepository, PaymentGateway paymentGateway) {
        this.rideRepository = rideRepository;
        this.paymentGateway = paymentGateway;
    }

    public void execute(InputFinishRide inputFinishRide) {
        Ride ride = rideRepository.getRideById(inputFinishRide.rideId().toString());
        ride.finish();
        rideRepository.updateRide(ride);
        paymentGateway.processPayment(new InputProcessPayment(UUID.fromString(ride.getRideId()), ride.getFare()));
    }
}
