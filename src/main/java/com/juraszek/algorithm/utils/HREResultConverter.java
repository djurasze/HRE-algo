package com.juraszek.algorithm.utils;

import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by Dominik on 2017-09-03.
 */
@Service

public class HREResultConverter {
    public Double[] convertToResult(Double[] c, Double[] jacobiResult) {
        int jacobiResultIndex = 0;
        Double[] result = new Double[c.length];
        for (int i = 0; i < c.length; i++) {
            if (c[i] != null) {
                result[i] = c[i];
            } else {
                result[i] = jacobiResult[jacobiResultIndex];
                jacobiResultIndex++;
            }
        }
        return result;
    }

    public Double[] normalizeResult(Double[] result) {
        Double sum = MathUtils.sum(result);
        return Arrays.stream(result).map(aDouble -> MathUtils.round(aDouble / sum, 3)).toArray(Double[]::new);
    }

    public Double[] rescaleResult(Double[] jacobiResult) {
        return Arrays.stream(jacobiResult).map(aDouble -> Math.pow(10, aDouble)).toArray(Double[]::new);
    }
}
