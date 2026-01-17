package dev.kauanmocelin.domain.entity;

import dev.kauanmocelin.domain.vo.Coordinate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Position {

    private final String positionId;
    private final String rideId;
    private final Coordinate coord;
    private final LocalDateTime date;

    private Position(String positionId, String rideId, Coordinate coord, LocalDateTime date) {
        this.positionId = positionId;
        this.rideId = rideId;
        this.coord = coord;
        this.date = date;
    }

    public static Position create(String rideId, BigDecimal latitude, BigDecimal longitude, LocalDateTime dateTime) {
        final var positionId = UUID.randomUUID().toString();
        return new Position(positionId, rideId, new Coordinate(latitude, longitude), dateTime);
    }

    public static Position restore(String positionId, String rideId, BigDecimal latitude, BigDecimal longitude, LocalDateTime date) {
        return new Position(positionId, rideId, new Coordinate(latitude, longitude), date);
    }

    public String getPositionId() {
        return positionId;
    }

    public String getRideId() {
        return rideId;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
