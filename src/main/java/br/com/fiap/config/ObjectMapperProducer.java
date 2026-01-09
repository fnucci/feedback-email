package br.com.fiap.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class ObjectMapperProducer {

    @Produces
    @ApplicationScoped
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Ignora propriedades desconhecidas no JSON
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
