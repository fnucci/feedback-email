package br.com.fiap.resource;

import br.com.fiap.model.EmailDTO;
import br.com.fiap.service.EmailService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.logging.Logger;

@Named("emailResource")
public class EmailResource implements RequestHandler<SQSEvent, String> {

    private static final Logger LOG = Logger.getLogger(EmailResource.class);

    @Inject
    EmailService emailService;

    @Inject
    ObjectMapper objectMapper;

    @Override
    public String handleRequest(SQSEvent event, Context context) {
        LOG.info("Iniciando processamento de evento SQS");

        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            try {
                LOG.infof("Processando mensagem: %s", msg.getMessageId());
                EmailDTO emailDTO = objectMapper.readValue(msg.getBody(), EmailDTO.class);
                emailService.sendEmail(emailDTO);

            } catch (Exception e) {
                LOG.errorf(e, "Erro ao processar mensagem SQS %s", msg.getMessageId());
                throw new RuntimeException("Falha no processamento da mensagem", e);
            } // ✅ Fecha try-catch
        } // ✅ Fecha for

        return "OK";
    }
}
