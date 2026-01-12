package dev.kauanmocelin.infra.repository;

import dev.kauanmocelin.domain.entity.Ride;

public interface RideRepository {
    void saveRide(final Ride ride);
    boolean hasActiveRideByPassengerId(final String passengerId);
    Ride getRideById(final String rideId);
    void updateRide(final Ride ride);
}
