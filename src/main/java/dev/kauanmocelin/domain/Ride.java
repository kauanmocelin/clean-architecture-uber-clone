package dev.kauanmocelin.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Ride {
    private final String rideId;
    private final String passengerId;
    private final BigDecimal fromLat;
    private final BigDecimal fromLong;
    private final BigDecimal toLat;
    private final BigDecimal toLong;
    private final String status;
    private final LocalDateTime date;

    private Ride(String rideId, String passengerId, BigDecimal fromLat, BigDecimal fromLong, BigDecimal toLat, BigDecimal toLong, String status, LocalDateTime date) {
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.fromLat = fromLat;
        this.fromLong = fromLong;
        this.toLat = toLat;
        this.toLong = toLong;
        this.status = status;
        this.date = date;
    }

    public static Ride create(String passengerId, BigDecimal fromLat, BigDecimal fromLong, BigDecimal toLat, BigDecimal toLong){
        final var rideId = UUID.randomUUID().toString();
        final var status = "requested";
        LocalDateTime date = LocalDateTime.now();
        return new Ride(rideId, passengerId, fromLat, fromLong, toLat, toLong, status, date);
    }

    public static Ride restore(String rideId, String passengerId, BigDecimal fromLat, BigDecimal fromLong, BigDecimal toLat, BigDecimal toLong, String status, LocalDateTime date){
        return new Ride(rideId, passengerId, fromLat, fromLong, toLat, toLong, status, date);
    }

    public String getRideId() {
        return rideId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public BigDecimal getFromLat() {
        return fromLat;
    }

    public BigDecimal getFromLong() {
        return fromLong;
    }

    public BigDecimal getToLat() {
        return toLat;
    }

    public BigDecimal getToLong() {
        return toLong;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
