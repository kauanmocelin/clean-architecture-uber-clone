package dev.kauanmocelin.infra.repository;

import dev.kauanmocelin.domain.entity.Position;
import dev.kauanmocelin.infra.database.DatabaseConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class PositionRepositoryDatabase implements PositionRepository {

    private final DatabaseConnection connection;

    public PositionRepositoryDatabase(DatabaseConnection connection) {
        this.connection = connection;
    }

    @Override
    public void savePosition(final Position position) {
        connection.execute("insert into uber_clone.position(position_id, ride_id, lat, long, date) values (?, ?, ?, ?, ?)",
            UUID.fromString(position.getPositionId()), UUID.fromString(position.getRideId()), position.getCoord().getLatitude(), position.getCoord().getLongitude(), Timestamp.valueOf(position.getDate()));
    }

    @Override
    public List<Position> listPositionsByRide(final String rideId) {
        List<Position> positions = new ArrayList<>();
        List<Map<String, Object>> result = connection.query("select * from uber_clone.position where ride_id = ?", rideId);
        if (result.isEmpty()) return positions;
        for (Map<String, Object> row : result) {
            positions.add(mapToPosition(row));
        }
        return positions;
    }

    private Position mapToPosition(Map<String, Object> row) {
        return Position.restore(
            row.get("position_id").toString(),
            row.get("ride_id").toString(),
            (BigDecimal) row.get("lat"),
            (BigDecimal) row.get("long"),
            ((Timestamp) row.get("date")).toLocalDateTime()
        );
    }
}
