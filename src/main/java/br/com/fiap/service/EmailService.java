package br.com.fiap.service;

import br.com.fiap.model.EmailDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@ApplicationScoped
public class EmailService {

    private static final Logger LOG = Logger.getLogger(EmailService.class);

    @Inject
    SesClient sesClient;

    public void sendEmail(EmailDTO emailDTO) {
        try {
            SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                    .destination(Destination.builder().toAddresses(emailDTO.getEmail()).build())
                    .message(Message.builder()
                            .subject(Content.builder().data("Nota Baixa").build())
                            .body(Body.builder()
                                    .text(Content.builder().data(emailDTO.getMsg()).build())
                                    .build())
                            .build())
                    .source("rafaelskiss1@hotmail.com")
                    .build();

            LOG.infof("Email request que foi enviada: %s", sendEmailRequest.toString());
            sesClient.sendEmail(sendEmailRequest);
            LOG.infof("E-mail enviado com sucesso para: %s", emailDTO.getEmail());

        } catch (Exception e) {
            LOG.error("Erro ao enviar e-mail via SES", e);
            throw e;
        }
    }
}
