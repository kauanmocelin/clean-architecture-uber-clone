package dev.kauanmocelin.application.usecase;

import dev.kauanmocelin.domain.Account;
import dev.kauanmocelin.domain.Ride;
import dev.kauanmocelin.dto.InputGetRide;
import dev.kauanmocelin.dto.OutputGetRide;
import dev.kauanmocelin.infra.repository.AccountRepository;
import dev.kauanmocelin.infra.repository.RideRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class GetRide {

    private final AccountRepository accountRepository;
    private final RideRepository rideRepository;

    public GetRide(AccountRepository accountRepository, RideRepository rideRepository) {
        this.accountRepository = accountRepository;
        this.rideRepository = rideRepository;
    }

    public OutputGetRide execute(InputGetRide inputGetRide) {
      final Ride ride = rideRepository.getRideById(inputGetRide.rideId().toString());
        Account passenger = accountRepository.getAccountById(ride.getPassengerId());
        return new OutputGetRide(
            UUID.fromString(ride.getRideId()),
            UUID.fromString(ride.getPassengerId()),
            ride.getFromLat(),
            ride.getFromLong(),
            ride.getToLat(),
            ride.getToLong(),
            ride.getStatus(),
            passenger.getName(),
            passenger.getEmail()
        );
    }
}
