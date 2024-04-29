package dev.kauanmocelin;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;

import java.util.UUID;

@ApplicationScoped
public class SignupApplication {

	private final SignupResource signupResource;

	@Inject
	public SignupApplication(SignupResource signupResource) {
		this.signupResource = signupResource;
	}

	public String signup(SignupRequestInputDTO signupRequestInputDTO) {
		signupRequestInputDTO.setUuid(UUID.randomUUID());
		SignupDatabaseDTO existingAccount = signupResource.getAccountByEmail(signupRequestInputDTO.getEmail());
		if(existingAccount != null) return String.valueOf(-4); // already exists
		if(!signupRequestInputDTO.getName().matches("[a-zA-Z]+\\s[a-zA-Z]+")) return String.valueOf(-3); // invalid name
		if(!signupRequestInputDTO.getEmail().matches("^(.+)@(.+)$")) return String.valueOf(-2); // invalid email
		if(!new ValidateCpf().validate(signupRequestInputDTO.getCpf())) return String.valueOf(-1); // invalid cpf
		if(signupRequestInputDTO.isDriver() && signupRequestInputDTO.getCarPlate() != null && !signupRequestInputDTO.getCarPlate().matches("[A-Z]{3}[0-9]{4}")) return String.valueOf(-5);
		signupResource.saveAccount(signupRequestInputDTO);
		return Json.createObjectBuilder()
			.add("accountId", signupRequestInputDTO.getUuid().toString())
			.build()
			.toString();
    }

	public SignupDatabaseDTO getAccount(final UUID accountId) {
		return signupResource.getAccountById(String.valueOf(accountId));
    }
}