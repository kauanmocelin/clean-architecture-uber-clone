package dev.kauanmocelin.infra.database;

import jakarta.enterprise.context.ApplicationScoped;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class PostgresqlAdapter implements DatabaseConnection {

    private final DataSource datasource;

    public PostgresqlAdapter(DataSource datasource) throws SQLException {
        this.datasource = datasource;
    }

    @Override
    public List<Map<String, Object>> query(String statement, Object... params) {
        try (Connection con = this.datasource.getConnection();
             PreparedStatement ps = con.prepareStatement(statement)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<Map<String, Object>> results = new ArrayList<>();
                ResultSetMetaData meta = rs.getMetaData();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        row.put(meta.getColumnLabel(i), rs.getObject(i));
                    }
                    results.add(row);
                }
                return results;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(String statement, Object... params) {
        try (Connection con = this.datasource.getConnection();
             PreparedStatement ps = con.prepareStatement(statement)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            this.datasource.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
