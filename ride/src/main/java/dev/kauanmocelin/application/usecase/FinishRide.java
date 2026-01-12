package dev.kauanmocelin.application.usecase;

import dev.kauanmocelin.domain.entity.Ride;
import dev.kauanmocelin.dto.InputFinishRide;
import dev.kauanmocelin.infra.repository.RideRepository;

public class FinishRide {

    private final RideRepository rideRepository;

    public FinishRide(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public void execute(InputFinishRide inputFinishRide) {
        Ride ride = rideRepository.getRideById(inputFinishRide.rideId().toString());
        ride.finish();
        rideRepository.updateRide(ride);
    }
}
