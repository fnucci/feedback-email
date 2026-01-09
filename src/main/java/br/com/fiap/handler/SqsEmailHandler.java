package br.com.fiap.handler;

import br.com.fiap.model.NotaBaixaResponse;
import br.com.fiap.service.EmailService;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SqsEmailHandler implements RequestHandler<SQSEvent, Void> {

    private final ObjectMapper mapper = new ObjectMapper();
    private final EmailService emailService = new EmailService();

    @Override
    public Void handleRequest(SQSEvent event, Context context) {

        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            try {
                NotaBaixaResponse nota =
                        mapper.readValue(msg.getBody(), NotaBaixaResponse.class);

                emailService.send(
                        nota.email(),
                        "Nota Baixa",
                        nota.msg()
                );

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
