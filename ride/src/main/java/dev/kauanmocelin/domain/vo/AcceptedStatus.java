package dev.kauanmocelin.domain.vo;

public class AcceptedStatus extends RideStatus {

    protected AcceptedStatus() {
        super("accepted");
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
        return new InProgressStatus();
    }

    @Override
    public RideStatus finish() {
        throw new RuntimeException("Invalid status");
    }
}
