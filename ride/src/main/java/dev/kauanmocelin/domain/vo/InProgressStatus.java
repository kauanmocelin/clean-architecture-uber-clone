package dev.kauanmocelin.domain.vo;

public class InProgressStatus extends RideStatus {

    protected InProgressStatus() {
        super("in_progress");
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
        return new CompletedStatus();
    }
}
