package dev.kauanmocelin;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@ApplicationScoped
public class SignupResource {

    DataSource datasource;

    @Inject
    public SignupResource(DataSource datasource) {
        this.datasource = datasource;
    }

    public SignupDatabaseDTO getAccountByEmail(String email) {
        SignupDatabaseDTO signupDatabaseDTO = null;
        try (Connection con = datasource.getConnection();) {
            PreparedStatement ps = con.prepareStatement("select * from uber_clone.account where email = ?");
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    signupDatabaseDTO = new SignupDatabaseDTO(
                            rs.getString("account_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("cpf"),
                            rs.getString("car_plate"),
                            rs.getBoolean("is_passenger"),
                            rs.getBoolean("is_driver")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return signupDatabaseDTO;
    }

    public SignupDatabaseDTO getAccountById(String accountId) {
        SignupDatabaseDTO signupDatabaseDTO = null;
        try (Connection con = datasource.getConnection();) {
            PreparedStatement ps = con.prepareStatement("select * from uber_clone.account where account_id = ?");
            ps.setObject(1, UUID.fromString(accountId));
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    signupDatabaseDTO = new SignupDatabaseDTO(
                            rs.getString("account_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("cpf"),
                            rs.getString("car_plate"),
                            rs.getBoolean("is_passenger"),
                            rs.getBoolean("is_driver")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return signupDatabaseDTO;
    }

    public void saveAccount(SignupRequestInputDTO account) {
        try (Connection con = datasource.getConnection();) {
            try (PreparedStatement insertStatement = con.prepareStatement("insert into uber_clone.account (account_id, name, email, cpf, car_plate, is_passenger, is_driver) values (?, ?, ?, ?, ?, ?, ?)")) {
                insertStatement.setObject(1, account.getUuid().toString(), java.sql.Types.OTHER);
                insertStatement.setString(2, account.getName());
                insertStatement.setString(3, account.getEmail());
                insertStatement.setString(4, account.getCpf());
                insertStatement.setString(5, account.getCarPlate());
                insertStatement.setBoolean(6, account.isPassenger());
                insertStatement.setBoolean(7, account.isDriver());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}