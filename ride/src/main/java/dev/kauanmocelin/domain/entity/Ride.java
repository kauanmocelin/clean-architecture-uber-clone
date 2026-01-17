package dev.kauanmocelin.domain.entity;

import com.aayushatharva.brotli4j.common.annotations.Local;
import dev.kauanmocelin.domain.service.FareCalculatorFactory;
import dev.kauanmocelin.domain.vo.Coordinate;
import dev.kauanmocelin.domain.vo.RideStatus;
import dev.kauanmocelin.domain.vo.RideStatusFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

public class Ride {
    private BigDecimal fare;
    private BigDecimal distance;
    private Coordinate lasPosition;
    private String rideId;
    private String passengerId;
    private String driverId;
    private Segment segment;
    private RideStatus status;
    private LocalDateTime date;

    private Ride(String rideId, String passengerId, String driverId, Segment segment, String status, LocalDateTime date, Coordinate lasPosition, BigDecimal distance, BigDecimal fare) {
        this.rideId = rideId;
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.segment = segment;
        this.status = RideStatusFactory.create(this, status);
        this.date = date;
        this.lasPosition = lasPosition;
        this.distance = distance;
        this.fare = fare;
    }

    public static Ride create(String passengerId, BigDecimal fromLat, BigDecimal fromLong, BigDecimal toLat, BigDecimal toLong){
        final var rideId = UUID.randomUUID().toString();
        final var status = "requested";
        LocalDateTime date = LocalDateTime.now();
        final var lastPosition = new Coordinate(fromLat, fromLong);
        final var distance = new BigDecimal(0);
        final var fare = new BigDecimal(0);
        return new Ride(rideId, passengerId, "", new Segment(new Coordinate(fromLat, fromLong), new Coordinate(toLat, toLong)), status, date, lastPosition, distance, fare);
    }

    public static Ride restore(String rideId, String passengerId, String driverId, BigDecimal fromLat, BigDecimal fromLong, BigDecimal toLat, BigDecimal toLong, String status, LocalDateTime date, BigDecimal lastLat, BigDecimal lastLong, BigDecimal distance, BigDecimal fare){
        return new Ride(rideId, passengerId, driverId, new Segment(new Coordinate(fromLat, fromLong), new Coordinate(toLat, toLong)), status, date, new Coordinate(lastLat, lastLong), distance, fare);
    }

    public String getRideId() {
        return rideId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public String getDriverId() {
        return driverId;
    }

    public BigDecimal getFromLat() {
        return segment.getFrom().getLatitude();
    }

    public BigDecimal getFromLong() {
        return segment.getFrom().getLongitude();
    }

    public BigDecimal getToLat() {
        return segment.getTo().getLatitude();
    }

    public BigDecimal getToLong() {
        return segment.getTo().getLongitude();
    }

    public String getStatus() {
        return this.status.getValue();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Coordinate getLasPosition() {
        return lasPosition;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void accept(final String driverId) {
        this.status = status.accept();
        this.driverId = driverId;
    }

    public void start() {
        this.status = status.start();
    }

    public void finish() {
        this.status = status.finish();
    }

    public void updatePosition(BigDecimal latitude, BigDecimal longitude, LocalDateTime dateTime) {
        Coordinate newPosition = new Coordinate(latitude, longitude);
        BigDecimal distance = new Segment(lasPosition, newPosition).getDistance();
        this.distance = this.distance.add(distance);
        this.fare = this.fare.add(FareCalculatorFactory.create(dateTime).calculate(distance));
        this.lasPosition = newPosition;
    }
}
