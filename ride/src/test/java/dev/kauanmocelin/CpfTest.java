package dev.kauanmocelin;

import dev.kauanmocelin.domain.vo.Cpf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CpfTest {

	@ParameterizedTest
	@ValueSource(strings = { "97456321558", "71428793860", "87748248800" })
	void shouldTestAValidCpf(String cpf) {
		assertThatCode(() -> new Cpf(cpf))
			.doesNotThrowAnyException();
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"11111111111", "123", "1234566789123456789"})
	void shouldTestAInvalidCpf(String cpf) {
		assertThatThrownBy(() -> new Cpf(cpf))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Invalid cpf");
	}
}