package dev.kauanmocelin.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OutputGetRide(
    UUID rideId,
    UUID passengerId,
    BigDecimal fromLat,
    BigDecimal fromLong,
    BigDecimal toLat,
    BigDecimal toLong,
    String status,
    String passengerName,
    String passengerEmail,
    String driverName,
    String driverEmail,
    BigDecimal distance,
    BigDecimal fare
) {}
