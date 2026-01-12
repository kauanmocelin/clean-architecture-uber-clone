package dev.kauanmocelin.domain.vo;

public class Name {

    private final String value;

    public Name(String name) {
        if(!name.matches("[a-zA-Z]+\\s[a-zA-Z]+")){
            throw new IllegalArgumentException("Invalid name");
        }
        this.value = name;
    }

    public String getValue() {
        return value;
    }
}
