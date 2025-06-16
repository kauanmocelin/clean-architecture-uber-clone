package dev.kauanmocelin.infra.gateway;

public interface MailerGateway {
    void send(final String recipient, final String subject, final String content);
}
