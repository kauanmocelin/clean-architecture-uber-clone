package dev.kauanmocelin;

import java.util.UUID;

public final class SignupRequestInputDTO {
    private UUID uuid;
    private String name;
    private String email;
    private String cpf;
    private String carPlate;
    private boolean isPassenger;
    private boolean isDriver;

    public SignupRequestInputDTO() {
    }

    public SignupRequestInputDTO(String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.carPlate = carPlate;
        this.isPassenger = isPassenger;
        this.isDriver = isDriver;
    }

    public SignupRequestInputDTO(UUID uuid, String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.carPlate = carPlate;
        this.isPassenger = isPassenger;
        this.isDriver = isDriver;
    }


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public boolean isPassenger() {
        return isPassenger;
    }

    public void setPassenger(boolean passenger) {
        this.isPassenger = passenger;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        this.isDriver = driver;
    }
}