package com.nisum.oppenheimer.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

        // Fail on unknown properties to enforce strict structure
        builder.featuresToEnable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // Pretty print the JSON output
        builder.featuresToEnable(SerializationFeature.INDENT_OUTPUT);

        return builder;
    }
}
