package dev.kauanmocelin.infra.repository;

import dev.kauanmocelin.domain.entity.Account;
import dev.kauanmocelin.infra.database.DatabaseConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class AccountRepositoryDatabase implements AccountRepository {

    private final DatabaseConnection connection;

    public AccountRepositoryDatabase(DatabaseConnection connection) {
        this.connection = connection;
    }

    public Account getAccountByEmail(final String email) {
        List<Map<String, Object>> result = connection.query("SELECT * FROM uber_clone.account WHERE email = ?", email);
        if (result.isEmpty()) return null;
        return mapToAccount(result.getFirst());
    }

    public Account getAccountById(final String accountId) {
        List<Map<String, Object>> result = connection.query("SELECT * FROM uber_clone.account WHERE account_id = ?", UUID.fromString(accountId));
        if (result.isEmpty()) return null;
        return mapToAccount(result.getFirst());
    }

    public void saveAccount(final Account account) {
        connection.execute("INSERT INTO uber_clone.account (account_id, name, email, cpf, car_plate, is_passenger, is_driver) VALUES (?, ?, ?, ?, ?, ?, ?)",
            UUID.fromString(account.getAccountId()), account.getName(), account.getEmail(),
            account.getCpf(), account.getCarPlate(), account.isPassenger(), account.isDriver());
    }

    private Account mapToAccount(Map<String, Object> row) {
        return Account.restore(
            row.get("account_id").toString(),
            row.get("name").toString(),
            row.get("email").toString(),
            row.get("cpf").toString(),
            row.get("car_plate").toString(),
            (Boolean) row.get("is_passenger"),
            (Boolean) row.get("is_driver")
        );
    }
}