package dev.kauanmocelin.application.gateway;

import java.math.BigDecimal;

public interface PaymentGateway {
    void processPayment(final InputProcessPayment inputProcessPayment);
}
