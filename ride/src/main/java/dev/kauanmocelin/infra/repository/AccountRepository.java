package dev.kauanmocelin.infra.repository;

import dev.kauanmocelin.domain.entity.Account;

public interface AccountRepository {
    Account getAccountByEmail(String email);
    Account getAccountById(String accountId);
    void saveAccount(Account account);
}
