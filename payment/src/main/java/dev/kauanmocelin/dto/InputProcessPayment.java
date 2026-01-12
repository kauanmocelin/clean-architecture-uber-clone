package dev.kauanmocelin.dto;

import java.math.BigDecimal;

public record InputProcessPayment(String rideId, BigDecimal amount) {
}
