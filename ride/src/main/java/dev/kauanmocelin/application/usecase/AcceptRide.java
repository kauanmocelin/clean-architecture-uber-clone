package dev.kauanmocelin.application.usecase;

import dev.kauanmocelin.domain.entity.Account;
import dev.kauanmocelin.domain.entity.Ride;
import dev.kauanmocelin.dto.InputAcceptRide;
import dev.kauanmocelin.infra.repository.AccountRepository;
import dev.kauanmocelin.infra.repository.RideRepository;

public class AcceptRide {

    private final AccountRepository accountRepository;
    private final RideRepository rideRepository;

    public AcceptRide(AccountRepository accountRepository, RideRepository rideRepository) {
        this.accountRepository = accountRepository;
        this.rideRepository = rideRepository;
    }

    public void execute(InputAcceptRide inputRequestRide) {
        final Account account = accountRepository.getAccountById(inputRequestRide.driverId());
        if (!account.isDriver()) {
            throw new IllegalArgumentException("Account is not from a driver");
        }
        final Ride ride = rideRepository.getRideById(inputRequestRide.rideId().toString());
        ride.accept(inputRequestRide.driverId());
        rideRepository.updateRide(ride);
    }
}
