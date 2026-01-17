package dev.kauanmocelin.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NormalFareCalculator implements FareCalculator {

    @Override
    public BigDecimal calculate(BigDecimal distance) {
        return distance.multiply(BigDecimal.valueOf(2.1)).setScale(0, RoundingMode.HALF_UP);
    }
}
