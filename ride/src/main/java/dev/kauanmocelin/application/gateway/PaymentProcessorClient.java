package dev.kauanmocelin.application.gateway;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/process_payment")
@RegisterRestClient(configKey = "process-payment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PaymentProcessorClient {

    @POST
    void processPayment(InputProcessPayment inputProcessPayment);

}
