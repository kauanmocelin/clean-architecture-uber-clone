package dev.kauanmocelin;

import dev.kauanmocelin.domain.entity.Account;
import dev.kauanmocelin.infra.database.PostgresqlAdapter;
import dev.kauanmocelin.infra.repository.AccountRepositoryDatabase;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class AccountRepositoryTest {

//    @Inject
//    AccountRepositoryDatabase accountDaoDatabase;

    @Inject
    DataSource dataSource;

    @Test
    void shouldSaveARegistryOnAccountTableAndQueryById() throws SQLException {
        PostgresqlAdapter postgresqlAdapter = new PostgresqlAdapter(dataSource);
        AccountRepositoryDatabase accountDaoDatabase = new AccountRepositoryDatabase(postgresqlAdapter);
        final Account account = Account.create(
            "John Doe",
            "johndoe" + Math.random() + "@gmaill.com",
            "26123453025",
            "",
            true,
            false
        );
        accountDaoDatabase.saveAccount(account);
        Account accountById = accountDaoDatabase.getAccountById(account.getAccountId());
        assertThat(accountById.getAccountId()).isEqualTo(account.getAccountId());
        assertThat(accountById.getName()).isEqualTo(account.getName());
        assertThat(accountById.getEmail()).isEqualTo(account.getEmail());
        assertThat(accountById.getCpf()).isEqualTo(account.getCpf());
        postgresqlAdapter.close();
    }

    @Test
    void shouldSaveARegistryOnAccountTableAndQueryByEmail() throws SQLException {
        PostgresqlAdapter postgresqlAdapter = new PostgresqlAdapter(dataSource);
        AccountRepositoryDatabase accountDaoDatabase = new AccountRepositoryDatabase(postgresqlAdapter);
        final Account account = Account.create(
            "John Doe",
            "johndoe" + Math.random() + "@gmaill.com",
            "26123453025",
            "",
            true,
            false
        );
        accountDaoDatabase.saveAccount(account);
        Account accountByEmail = accountDaoDatabase.getAccountByEmail(account.getEmail());
        assertThat(accountByEmail.getAccountId()).isEqualTo(account.getAccountId());
        assertThat(accountByEmail.getName()).isEqualTo(account.getName());
        assertThat(accountByEmail.getEmail()).isEqualTo(account.getEmail());
        assertThat(accountByEmail.getCpf()).isEqualTo(account.getCpf());
        postgresqlAdapter.close();
    }
}