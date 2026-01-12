package dev.kauanmocelin.domain.entity;

import dev.kauanmocelin.domain.vo.CarPlate;
import dev.kauanmocelin.domain.vo.Cpf;
import dev.kauanmocelin.domain.vo.Email;
import dev.kauanmocelin.domain.vo.Name;

import java.util.UUID;

public class Account {

    private final String accountId;
    private final Name name;
    private final Email email;
    private final Cpf cpf;
    private final CarPlate carPlate;
    private final boolean isPassenger;
    private final boolean isDriver;

    private Account(String accountId, Name name, Email email, Cpf cpf, CarPlate carPlate, boolean isPassenger, boolean isDriver) {
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
        return new Account(accountId, new Name(name), new Email(email), new Cpf(cpf), new CarPlate(carPlate), isPassenger, isDriver);
    }

    public static Account restore(String accountId, String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver){
        return new Account(accountId, new Name(name), new Email(email), new Cpf(cpf), new CarPlate(carPlate), isPassenger, isDriver);
    }

    public String getAccountId() {
        return accountId;
    }

    public String getName() {
        return name.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getCpf() {
        return cpf.getValue();
    }

    public String getCarPlate() {
        return carPlate.getValue();
    }

    public boolean isPassenger() {
        return isPassenger;
    }

    public boolean isDriver() {
        return isDriver;
    }
}
