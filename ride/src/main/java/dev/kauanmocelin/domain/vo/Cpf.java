package dev.kauanmocelin.domain.vo;

public class Cpf {

    private static final int FACTOR_FIRST_DIGIT = 10;
    private static final int FACTOR_SECOND_DIGIT = 11;
    private final String value;

    public Cpf(String cpf) {
        if(!this.validate(cpf)){
            throw new IllegalArgumentException("Invalid cpf");
        }
        this.value = cpf;
    }

    private String removeNonDigits(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    private boolean isValidLength(String cpf) {
        return cpf.length() == 11;
    }

    private boolean allDigitsEqual(String cpf) {
        char firstDigit = cpf.charAt(0);
        return cpf.chars().allMatch(digit -> digit == firstDigit);
    }

    private int calculateDigit(String cpf, int factor) {
        int total = 0;
        for (int i = 0; i < cpf.length() - 1; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            if(factor > 1){
                total += digit * factor--;
            }
        }
        int remainder = total % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }

    private String extractDigit(String cpf) {
        return cpf.substring(9);
    }

    private boolean validate(String rawCpf) {
        if (rawCpf == null || rawCpf.isEmpty()) return false;
        String cpf = removeNonDigits(rawCpf);
        if (!isValidLength(cpf)) return false;
        if (allDigitsEqual(cpf)) return false;
        int firstDigit = calculateDigit(cpf, FACTOR_FIRST_DIGIT);
        int secondDigit = calculateDigit(cpf, FACTOR_SECOND_DIGIT);
        return extractDigit(cpf).equals(String.valueOf(firstDigit) + secondDigit);
    }

    public String getValue() {
        return value;
    }
}
