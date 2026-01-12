package dev.kauanmocelin.infra.gateway;

import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class MailerGatewayMemory implements MailerGateway {

//    @Inject
//    Logger log;
    Logger log = Logger.getLogger(MailerGatewayMemory.class);

    @Override
    public void send(String recipient, String subject, String content) {
        log.infof("MailerGatewayMemory sending content: %s", content);
    }
}