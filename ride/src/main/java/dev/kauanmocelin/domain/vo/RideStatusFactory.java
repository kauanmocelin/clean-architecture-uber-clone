package dev.kauanmocelin.domain.vo;

import dev.kauanmocelin.domain.entity.Ride;

public class RideStatusFactory {

    public static RideStatus create(final Ride ride, final String status){
        if(status.equals("requested")){
            return new RequestedStatus();
        }
        if(status.equals("accepted")){
            return new AcceptedStatus();
        }
        if(status.equals("in_progress")){
            return new InProgressStatus();
        }
        if(status.equals("completed")){
            return new CompletedStatus();
        }
        throw new RuntimeException("Invalid status");
    }
}
