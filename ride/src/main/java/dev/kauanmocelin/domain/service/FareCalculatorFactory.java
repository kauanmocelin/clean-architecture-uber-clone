package dev.kauanmocelin.domain.service;

import java.time.LocalDateTime;

public class FareCalculatorFactory {

    public static FareCalculator create(LocalDateTime date){
        if(date.getHour() > 22){
            return new OvernightFareCalculator();
        }
        if(date.getHour() <= 22){
            return new NormalFareCalculator();
        }
        throw new RuntimeException();
    }
}
