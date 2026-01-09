package br.com.fiap.service;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

public class EmailService {

    private final SesClient sesClient = SesClient.builder()
            .region(Region.SA_EAST_1)
            .build();

    public void send(String to, String subject, String content) {

        SendEmailRequest request = SendEmailRequest.builder()
                .destination(Destination.builder()
                        .toAddresses(to)
                        .build())
                .message(Message.builder()
                        .subject(Content.builder().data(subject).build())
                        .body(Body.builder()
                                .text(Content.builder().data(content).build())
                                .build())
                        .build())
                .source("rafaelskiss1@hotmail.com")
                .build();

        sesClient.sendEmail(request);
    }
}