package dev.kauanmocelin;

public record SignupDatabaseDTO(
		String account_id,
		String name,
		String email,
		String cpf,
		String car_plate,
		boolean is_passenger,
		boolean is_driver
) {}
