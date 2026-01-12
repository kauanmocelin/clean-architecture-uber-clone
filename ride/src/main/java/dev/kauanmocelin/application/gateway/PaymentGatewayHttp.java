package dev.kauanmocelin.application.gateway;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class PaymentGatewayHttp implements PaymentGateway {

    private final PaymentProcessorClient paymentProcessorClient;

    public PaymentGatewayHttp(@RestClient PaymentProcessorClient paymentProcessorClient) {
        this.paymentProcessorClient = paymentProcessorClient;
    }

    @Override
    public void processPayment(InputProcessPayment inputProcessPayment) {
        paymentProcessorClient.processPayment(inputProcessPayment);
    }
}
