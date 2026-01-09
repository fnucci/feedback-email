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
            // ✅ Monta mensagem personalizada com os dados
            String mensagemCompleta = String.format(
                    """
                            Olá coordenador,
                            
                            Identificamos que o aluno %s deu uma nota baixa para o curso "%s"
                            
                            Nota obtida: %d
                            
                            Comentário do aluno: %s
                            
                            Atenciosamente,
                            Equipe Faculdade FIAP
                            """,
                    emailDTO.getAluno(),
                    emailDTO.getCurso(),
                    emailDTO.getNota(),
                    emailDTO.getMsg()
            );

            SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                    .destination(Destination.builder()
                            .toAddresses(emailDTO.getEmail())
                            .build())
                    .message(Message.builder()
                            .subject(Content.builder()
                                    .data(String.format("Alerta: Nota Baixa no Curso %s", emailDTO.getCurso()))
                                    .build())
                            .body(Body.builder()
                                    .text(Content.builder()
                                            .data(mensagemCompleta)
                                            .build())
                                    .build())
                            .build())
                    .source("rafaelskiss1@hotmail.com")
                    .build();

            sesClient.sendEmail(sendEmailRequest);
            LOG.infof("E-mail enviado com sucesso para: %s (Aluno: %s, Curso: %s, Nota: %d)",
                    emailDTO.getEmail(), emailDTO.getAluno(), emailDTO.getCurso(), emailDTO.getNota());

        } catch (Exception e) {
            LOG.errorf(e, "Erro ao enviar e-mail para: %s", emailDTO.getEmail());
            throw new RuntimeException("Falha ao enviar e-mail", e);
        }
    }
}
