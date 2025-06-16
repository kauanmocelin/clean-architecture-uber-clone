package dev.kauanmocelin.domain;

import java.util.UUID;

public class Account {

    private final String accountId;
    private final String name;
    private final String email;
    private final String cpf;
    private final String carPlate;
    private final boolean isPassenger;
    private final boolean isDriver;
    private Account(String accountId, String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver) {
        if(!name.matches("[a-zA-Z]+\\s[a-zA-Z]+")) throw new IllegalArgumentException("Invalid name");
        if(!email.matches("^(.+)@(.+)$")) throw new IllegalArgumentException("Invalid email");
        if(!new ValidateCpf().validate(cpf)) throw new IllegalArgumentException("Invalid cpf");
        if(isDriver && carPlate != null && !carPlate.matches("[A-Z]{3}[0-9]{4}")) throw new IllegalArgumentException("Invalid car plate");

        this.accountId = accountId;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.carPlate = carPlate;
        this.isPassenger = isPassenger;
        this.isDriver = isDriver;
    }

    public static Account create(String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver){
        final var accountId = UUID.randomUUID().toString();
        return new Account(accountId, name, email, cpf, carPlate, isPassenger, isDriver);
    }

    public static Account restore(String accountId, String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver){
        return new Account(accountId, name, email, cpf, carPlate, isPassenger, isDriver);
    }

    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public boolean isPassenger() {
        return isPassenger;
    }

    public boolean isDriver() {
        return isDriver;
    }
}
