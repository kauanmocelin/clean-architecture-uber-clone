package dev.kauanmocelin.domain.vo;

public class CompletedStatus extends RideStatus {

    protected CompletedStatus() {
        super("completed");
    }

    @Override
    public RideStatus request() {
        throw new RuntimeException("Invalid status");
    }

    @Override
    public RideStatus accept() {
        throw new RuntimeException("Invalid status");
    }

    @Override
    public RideStatus start() {
        throw new RuntimeException("Invalid status");
    }

    @Override
    public RideStatus finish() {
        throw new RuntimeException("Invalid status");
    }
}
