package com.altice.service;

import java.math.BigInteger;

import com.altice.domain.modal.SequenceValue;
import com.altice.domain.usecase.SequenceCalculator;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SequenceService {

    @Inject
    SequenceCalculator calculator;

    @CacheResult(cacheName = "sequenceCache")
    public SequenceValue getSequenceValue(int n) {
        long startTime = System.currentTimeMillis();
        BigInteger result = calculator.calculate(n);
        long calculationTime = System.currentTimeMillis() - startTime;
        return SequenceValue.builder()
                .index(n)
                .value(result.toString())
                .calculationTimeMs(calculationTime)
                .build();
    }
}