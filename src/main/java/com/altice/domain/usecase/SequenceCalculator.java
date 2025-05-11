package com.altice.domain.usecase;

import java.math.BigInteger;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SequenceCalculator {   
    private static final BigInteger L_0 = BigInteger.ZERO;
    private static final BigInteger L_1 = BigInteger.ONE;
    private static final BigInteger L_2 = BigInteger.ZERO;
    private static final BigInteger L_3 = BigInteger.ONE;
    
    public BigInteger calculate(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Sequence index (must be non-negative): " + n);
        }

        if (n > 1000000) {
            throw new IllegalArgumentException("Sequence index (must be less than or equal to 1000000)");
        }
        
        if (n == 0) return L_0;
        if (n == 1) return L_1;
        if (n == 2) return L_2;
        if (n == 3) return L_3;
        
        BigInteger nMinus4 = L_0;
        BigInteger nMinus3 = L_1;
        BigInteger nMinus2 = L_2;
        BigInteger nMinus1 = L_3;
        BigInteger current;
        
        for (int i = 4; i <= n; i++) {
            current = nMinus4.add(nMinus3);
            nMinus4 = nMinus3;
            nMinus3 = nMinus2;
            nMinus2 = nMinus1;
            nMinus1 = current;
        }
        
        return nMinus1;
    }
}