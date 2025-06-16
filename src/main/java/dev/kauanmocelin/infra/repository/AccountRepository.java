package dev.kauanmocelin.infra.repository;

import dev.kauanmocelin.domain.Account;

public interface AccountRepository {
    Account getAccountByEmail(String email);
    Account getAccountById(String accountId);
    void saveAccount(Account account);
}
