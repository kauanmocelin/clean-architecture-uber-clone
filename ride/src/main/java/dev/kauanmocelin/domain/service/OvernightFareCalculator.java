package dev.kauanmocelin.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OvernightFareCalculator implements FareCalculator {

    @Override
    public BigDecimal calculate(BigDecimal distance) {
        return distance.multiply(BigDecimal.valueOf(4.2)).setScale(0, RoundingMode.HALF_UP);
    }
}
