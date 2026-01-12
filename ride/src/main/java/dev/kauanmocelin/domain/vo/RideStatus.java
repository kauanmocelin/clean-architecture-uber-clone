package dev.kauanmocelin.domain.vo;

public abstract class RideStatus {

    final String value;

    RideStatus(String value) {
        this.value = value;
    }

    public abstract RideStatus request();
    public abstract RideStatus accept();
    public abstract RideStatus start();
    public abstract RideStatus finish();

    public String getValue() {
        return value;
    }
}
