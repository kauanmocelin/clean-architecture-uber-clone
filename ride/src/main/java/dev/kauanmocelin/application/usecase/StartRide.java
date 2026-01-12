package dev.kauanmocelin.application.usecase;

import dev.kauanmocelin.domain.entity.Ride;
import dev.kauanmocelin.dto.InputStartRide;
import dev.kauanmocelin.infra.repository.RideRepository;

public class StartRide {

    private final RideRepository rideRepository;

    public StartRide(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public void execute(InputStartRide inputStartRide) {
        final Ride ride = rideRepository.getRideById(inputStartRide.rideId().toString());
        ride.start();
        rideRepository.updateRide(ride);
    }
}
