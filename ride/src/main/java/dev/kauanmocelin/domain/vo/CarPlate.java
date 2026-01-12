package dev.kauanmocelin.domain.vo;

public class CarPlate {

    private final String value;

    public CarPlate(String carPlate) {
        if(!carPlate.isBlank() && !carPlate.matches("[A-Z]{3}[0-9]{4}")){
            throw new IllegalArgumentException("Invalid car plate");
        }
        this.value = carPlate;
    }

    public String getValue() {
        return value;
    }
}
