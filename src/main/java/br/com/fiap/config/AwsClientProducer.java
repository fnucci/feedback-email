package br.com.fiap.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@ApplicationScoped
public class AwsClientProducer {

    @ConfigProperty(name = "aws.region", defaultValue = "us-east-1")
    String awsRegion;

    @Produces
    @ApplicationScoped
    public SesClient sesClient() {
        return SesClient.builder()
                .region(Region.of(awsRegion))
                .httpClient(UrlConnectionHttpClient.builder().build())
                .build();
    }
}

