package dev.kauanmocelin.domain.vo;

public class RequestedStatus extends RideStatus {

    protected RequestedStatus() {
        super("requested");
    }

    @Override
    public RideStatus request() {
        throw new RuntimeException("Invalid status");
    }

    @Override
    public RideStatus accept() {
        return new AcceptedStatus();
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
