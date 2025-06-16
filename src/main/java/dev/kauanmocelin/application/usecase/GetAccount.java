package dev.kauanmocelin.application.usecase;

import dev.kauanmocelin.application.OutputGetAccount;
import dev.kauanmocelin.domain.Account;
import dev.kauanmocelin.infra.repository.AccountRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetAccount {

    private final AccountRepository accountRepository;

    public GetAccount(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public OutputGetAccount execute(final String accountId) {
        final Account account = accountRepository.getAccountById(String.valueOf(accountId));
        return new OutputGetAccount(account.getName(), account.getEmail(), account.getCpf(), account.getCarPlate());
    }
}