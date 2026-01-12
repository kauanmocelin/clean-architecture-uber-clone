package dev.kauanmocelin.dto;

import java.math.BigDecimal;

public record InputRequestRide(
    String passengerId,
    BigDecimal fromLat,
    BigDecimal fromLong,
    BigDecimal toLat,
    BigDecimal toLong
) {
}
