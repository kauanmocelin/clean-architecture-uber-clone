package dev.kauanmocelin.application.usecase;

import dev.kauanmocelin.domain.entity.Position;
import dev.kauanmocelin.domain.entity.Ride;
import dev.kauanmocelin.dto.InputUpdatePosition;
import dev.kauanmocelin.infra.repository.PositionRepository;
import dev.kauanmocelin.infra.repository.RideRepository;

public class UpdatePosition {

    private final PositionRepository positionRepository;
    private final RideRepository rideRepository;

    public UpdatePosition(PositionRepository positionRepository, RideRepository rideRepository) {
        this.positionRepository = positionRepository;
        this.rideRepository = rideRepository;
    }

    public void execute(InputUpdatePosition inputUpdatePosition) {
        Ride ride = rideRepository.getRideById(inputUpdatePosition.rideId().toString());
        ride.updatePosition(inputUpdatePosition.latitude(), inputUpdatePosition.longitude(), inputUpdatePosition.dateTime());
        rideRepository.updateRide(ride);
        final var position = Position.create(inputUpdatePosition.rideId().toString(), inputUpdatePosition.latitude(), inputUpdatePosition.longitude(), inputUpdatePosition.dateTime());
        positionRepository.savePosition(position);
    }
}
