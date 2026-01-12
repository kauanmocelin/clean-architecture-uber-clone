package dev.kauanmocelin.infra.http;

import dev.kauanmocelin.application.usecase.ProcessPayment;
import dev.kauanmocelin.dto.InputProcessPayment;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/process_payment")
public class PaymentController {

    private final ProcessPayment processPayment;

    public PaymentController(ProcessPayment processPayment) {
        this.processPayment = processPayment;
    }

    @POST
    public Response processPayment(InputProcessPayment inputProcessPayment) {
        processPayment.execute(inputProcessPayment);
        return Response.ok().build();
    }
}