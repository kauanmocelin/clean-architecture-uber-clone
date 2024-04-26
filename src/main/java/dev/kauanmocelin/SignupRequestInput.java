package dev.kauanmocelin;

public record SignupRequestInput(
		String name,
		String email,
		String cpf,
		String car_plate,
		boolean is_passenger,
		boolean is_driver
) {}