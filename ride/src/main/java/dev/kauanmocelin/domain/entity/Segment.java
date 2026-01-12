package dev.kauanmocelin.domain.entity;

import dev.kauanmocelin.domain.vo.Coordinate;

import java.math.BigDecimal;

import static java.lang.Math.*;

public class Segment {

    private final Coordinate from;
    private final Coordinate to;

    public Segment(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
    }

    public Coordinate getFrom() {
        return from;
    }

    public Coordinate getTo() {
        return to;
    }
    
    public BigDecimal getDistance() {
        final double earthRadiusKm = 6371.0;
        final double degreesToRadians = Math.PI / 180;

        double lat1 = from.getLatitude().doubleValue();
        double lon1 = from.getLongitude().doubleValue();
        double lat2 = to.getLatitude().doubleValue();
        double lon2 = to.getLongitude().doubleValue();

        double deltaLat = (lat2 - lat1) * degreesToRadians;
        double deltaLon = (lon2 - lon1) * degreesToRadians;

        double a = sin(deltaLat / 2) * sin(deltaLat / 2)
            + cos(lat1 * degreesToRadians) * cos(lat2 * degreesToRadians)
            * sin(deltaLon / 2) * sin(deltaLon / 2);

        double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        double distance = earthRadiusKm * c;

        return BigDecimal.valueOf(Math.round(distance));
    }
}
