package dev.kauanmocelin.infra.repository;

import dev.kauanmocelin.domain.Ride;
import jakarta.enterprise.context.ApplicationScoped;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

@ApplicationScoped
public class RideRepositoryDatabase implements RideRepository {

    private final DataSource datasource;

    public RideRepositoryDatabase(final DataSource datasource) {
        this.datasource = datasource;
    }

    @Override
    public void saveRide(Ride ride) {
        try (Connection con = datasource.getConnection();) {
            try (PreparedStatement insertStatement = con.prepareStatement("insert into uber_clone.ride (ride_id, passenger_id, from_lat, from_long, to_lat, to_long, status, date) values (?, ?, ?, ?, ?, ?, ?, ?)")) {
                insertStatement.setObject(1, ride.getRideId().toString(), java.sql.Types.OTHER);
                insertStatement.setObject(2, ride.getPassengerId().toString(), java.sql.Types.OTHER);
                insertStatement.setBigDecimal(3, ride.getFromLat());
                insertStatement.setBigDecimal(4, ride.getFromLong());
                insertStatement.setBigDecimal(5, ride.getToLat());
                insertStatement.setBigDecimal(6, ride.getToLong());
                insertStatement.setString(7, ride.getStatus());
                insertStatement.setTimestamp(8, Timestamp.valueOf(ride.getDate()));
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasActiveRideByPassengerId(String passengerId) {
        try (Connection con = datasource.getConnection();) {
            PreparedStatement ps = con.prepareStatement("select 1 from uber_clone.ride where passenger_id = ? and status <> 'completed'");
            ps.setObject(1, UUID.fromString(passengerId));
            try (ResultSet rs = ps.executeQuery();) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ride getRideById(String rideId) {
        Ride ride = null;
        try (Connection con = datasource.getConnection();) {
            PreparedStatement ps = con.prepareStatement("select * from uber_clone.ride where ride_id = ?");
            ps.setObject(1, UUID.fromString(rideId));
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    ride = Ride.restore(
                        rs.getString("ride_id"),
                        rs.getString("passenger_id"),
                        rs.getBigDecimal("from_lat"),
                        rs.getBigDecimal("from_long"),
                        rs.getBigDecimal("to_lat"),
                        rs.getBigDecimal("to_long"),
                        rs.getString("status"),
                        rs.getTimestamp("date").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ride;
    }
}
