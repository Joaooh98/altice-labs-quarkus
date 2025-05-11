package com.altice.domain.modal;

import java.math.BigInteger;

public class SequenceValue {
    private final int index;
    private final String value;
    private final long calculationTimeMs;

    private SequenceValue(Builder builder) {
        this.index = builder.index;
        this.value = builder.value;
        this.calculationTimeMs = builder.calculationTimeMs;
    }

    public int getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    public long getCalculationTimeMs() {
        return calculationTimeMs;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private int index;
        private String value;
        private long calculationTimeMs;
        
        private Builder() {}
        
        public Builder index(int index) {
            this.index = index;
            return this;
        }
        
        public Builder value(String value) {
            this.value = value;
            return this;
        }
        
        public Builder calculationTimeMs(long calculationTimeMs) {
            this.calculationTimeMs = calculationTimeMs;
            return this;
        }
        
        public SequenceValue build() {
            return new SequenceValue(this);
        }
    }
}