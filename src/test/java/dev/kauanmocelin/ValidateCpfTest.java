package dev.kauanmocelin;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ValidateCpfTest {

	@ParameterizedTest
	@ValueSource(strings = { "97456321558", "71428793860", "87748248800" })
	void shouldTestAValidCpf(String cpf) {
		assertThat(new ValidateCpf().validate(cpf)).isTrue();
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"11111111111", "123", "1234566789123456789"})
	void shouldTestAInvalidCpf(String cpf) {
		assertThat(new ValidateCpf().validate(cpf)).isFalse();
	}
}