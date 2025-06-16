package dev.kauanmocelin.infra.gateway;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MailerGatewayMemory implements MailerGateway {
    @Override
    public void send(String recipient, String subject, String content) {
        System.out.println("MailerGatewayMemory sending");
    }
}