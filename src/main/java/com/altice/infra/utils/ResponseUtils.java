package com.altice.infra.utils;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

@Singleton
public class ResponseUtils implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper mapper) {

        mapper.getFactory().setStreamReadConstraints(
                StreamReadConstraints.builder()
                        .maxNumberLength(1_000_0000) 
                        .build());
    }
}