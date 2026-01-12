package dev.kauanmocelin.infra.repository;

import dev.kauanmocelin.domain.entity.Position;

import java.util.List;

public interface PositionRepository {
    void savePosition(final Position position);
    List<Position> listPositionsByRide(final String rideId);
}
