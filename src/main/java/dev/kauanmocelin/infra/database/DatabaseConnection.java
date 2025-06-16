package dev.kauanmocelin.infra.database;

import java.util.List;
import java.util.Map;

public interface DatabaseConnection {
    List<Map<String, Object>> query(String statement, Object... params);
    void execute(String statement, Object... params);
    void close();
}
