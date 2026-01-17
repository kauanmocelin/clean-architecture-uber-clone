package dev.kauanmocelin.domain.service;

import java.math.BigDecimal;

public interface FareCalculator {
    BigDecimal calculate(BigDecimal distance);
}
