package dev.kauanmocelin;

import dev.kauanmocelin.domain.entity.Position;
import dev.kauanmocelin.domain.service.DistanceCalculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RideTest {

    @Test
    void shouldUpdateRideWithSuccess() {
        final List<Position> positions = java.util.List.of(
            Position.create("", BigDecimal.valueOf(-27.584905257808835), BigDecimal.valueOf(-48.545022195325124), LocalDateTime.of(2026, 1, 17, 23, 30, 0)),
            Position.create("", BigDecimal.valueOf(-27.496887588317275), BigDecimal.valueOf(-48.522234807851476), LocalDateTime.of(2026, 1, 17, 23, 30, 0))        );
        assertThat(DistanceCalculator.getDistance(positions)).isEqualTo(new BigDecimal(10));
    }
}