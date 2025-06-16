package dev.kauanmocelin.application.usecase;

import dev.kauanmocelin.domain.Account;
import dev.kauanmocelin.dto.SignupRequestInputDTO;
import dev.kauanmocelin.dto.SignupResponseOutput;
import dev.kauanmocelin.infra.gateway.MailerGateway;
import dev.kauanmocelin.infra.repository.AccountRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Signup {

	private final AccountRepository accountRepository;
	private final MailerGateway mailerGateway;

	public Signup(AccountRepository accountRepository, MailerGateway mailerGateway) {
		this.accountRepository = accountRepository;
        this.mailerGateway = mailerGateway;
    }

	public SignupResponseOutput execute(SignupRequestInputDTO signupRequestInputDTO) {
		final Account existingAccount = accountRepository.getAccountByEmail(signupRequestInputDTO.getEmail());
		if(existingAccount != null) throw new IllegalArgumentException("Account already exists");
		Account account = Account.create(
			signupRequestInputDTO.getName(),
			signupRequestInputDTO.getEmail(),
			signupRequestInputDTO.getCpf(),
			signupRequestInputDTO.getCarPlate(),
			signupRequestInputDTO.isPassenger(),
			signupRequestInputDTO.isDriver()
		);
		accountRepository.saveAccount(account);
		mailerGateway.send(signupRequestInputDTO.getEmail(), "Welcome!", "");
		return new SignupResponseOutput(account.getAccountId());
    }
}