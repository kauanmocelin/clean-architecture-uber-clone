package dev.kauanmocelin.domain.vo;

public class Email {

    private final String value;

    public Email(String email) {
        if(!email.matches("^(.+)@(.+)$")){
            throw new IllegalArgumentException("Invalid email");
        }
        this.value = email;
    }

    public String getValue() {
        return value;
    }
}
