package dev.kauanmocelin.application.usecase;

import dev.kauanmocelin.domain.entity.Account;
import dev.kauanmocelin.domain.entity.Ride;
import dev.kauanmocelin.dto.InputRequestRide;
import dev.kauanmocelin.dto.OutputRequestRide;
import dev.kauanmocelin.infra.repository.AccountRepository;
import dev.kauanmocelin.infra.repository.RideRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class RequestRide {

    private final AccountRepository accountRepository;
    private final RideRepository rideRepository;

    public RequestRide(AccountRepository accountRepository, RideRepository rideRepository) {
        this.accountRepository = accountRepository;
        this.rideRepository = rideRepository;
    }

    public OutputRequestRide execute(InputRequestRide inputRequestRide) {
        final Account account = accountRepository.getAccountById(inputRequestRide.passengerId());
        if (!account.isPassenger()) {
            throw new IllegalArgumentException("Account is not from a passenger");
        }
        boolean hasActiveRide = rideRepository.hasActiveRideByPassengerId(inputRequestRide.passengerId());
        if(hasActiveRide){
            throw new IllegalArgumentException("Passenger has an active ride");
        }
        Ride ride = Ride.create(
            inputRequestRide.passengerId(),
            inputRequestRide.fromLat(),
            inputRequestRide.fromLong(),
            inputRequestRide.toLat(),
            inputRequestRide.toLong()
        );
        rideRepository.saveRide(ride);
        return new OutputRequestRide(UUID.fromString(ride.getRideId()));
    }
}
