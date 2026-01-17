package dev.kauanmocelin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record InputUpdatePosition(UUID rideId, BigDecimal latitude, BigDecimal longitude, LocalDateTime dateTime) {
}
