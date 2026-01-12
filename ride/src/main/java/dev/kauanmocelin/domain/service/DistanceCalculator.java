package dev.kauanmocelin.domain.service;

import dev.kauanmocelin.domain.entity.Position;
import dev.kauanmocelin.domain.entity.Segment;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

public class DistanceCalculator {

    private DistanceCalculator() {}

    public static BigDecimal getDistance(final List<Position> positions) {
        return IntStream.range(0, positions.size() - 1)
            .mapToObj(i -> {
                Position current = positions.get(i);
                Position next = positions.get(i + 1);
                return new Segment(current.getCoord(), next.getCoord()).getDistance();
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
