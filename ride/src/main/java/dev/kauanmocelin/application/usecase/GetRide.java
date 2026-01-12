package dev.kauanmocelin.application.usecase;

import dev.kauanmocelin.domain.entity.Account;
import dev.kauanmocelin.domain.entity.Ride;
import dev.kauanmocelin.dto.InputGetRide;
import dev.kauanmocelin.dto.OutputGetRide;
import dev.kauanmocelin.infra.repository.AccountRepository;
import dev.kauanmocelin.infra.repository.PositionRepository;
import dev.kauanmocelin.infra.repository.RideRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class GetRide {

    private final AccountRepository accountRepository;
    private final RideRepository rideRepository;
    private final PositionRepository positionRepository;

    public GetRide(AccountRepository accountRepository, RideRepository rideRepository, PositionRepository positionRepository) {
        this.accountRepository = accountRepository;
        this.rideRepository = rideRepository;
        this.positionRepository = positionRepository;
    }

    public OutputGetRide execute(InputGetRide inputGetRide) {
        final Ride ride = rideRepository.getRideById(inputGetRide.rideId().toString());
        final Account passenger = accountRepository.getAccountById(ride.getPassengerId());
        final Optional<Account> optionalDriver = Optional.ofNullable(ride.getDriverId())
            .map(accountRepository::getAccountById);
        return new OutputGetRide(
            UUID.fromString(ride.getRideId()),
            UUID.fromString(ride.getPassengerId()),
            ride.getFromLat(),
            ride.getFromLong(),
            ride.getToLat(),
            ride.getToLong(),
            ride.getStatus(),
            passenger.getName(),
            passenger.getEmail(),
            optionalDriver.map(Account::getName).orElse(""),
            optionalDriver.map(Account::getEmail).orElse(""),
            ride.getDistance(),
            ride.getFare()
        );
    }
}
