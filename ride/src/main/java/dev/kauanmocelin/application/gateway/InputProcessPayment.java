package dev.kauanmocelin.application.gateway;

import java.math.BigDecimal;
import java.util.UUID;

public record InputProcessPayment(UUID rideId, BigDecimal amount) {}
