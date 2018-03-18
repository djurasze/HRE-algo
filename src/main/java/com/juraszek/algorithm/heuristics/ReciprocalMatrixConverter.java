package com.juraszek.algorithm.heuristics;

import org.springframework.stereotype.Service;

/**
 * Created by Dominik on 2017-05-21.
 */
@Service
public class ReciprocalMatrixConverter {


    public Double[][] convertToReciprocalMatrix(Double[][] pairsComparisons) {
        int dataSize = pairsComparisons.length;
        Double[][] reciprocalMatrix = new Double[dataSize][dataSize];
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < dataSize; j++) {
                reciprocalMatrix[i][j] = convertToReciprocal(pairsComparisons[i][j], pairsComparisons[j][i]);
            }
        }
        return reciprocalMatrix;
    }

    private Double convertToReciprocal(Double value, Double reciprocalValue) {
        if (value == null || reciprocalValue == null)
            return value;
        if (value * reciprocalValue != 1) {
            return Math.sqrt(value * (1 / reciprocalValue));
        } else {
            return value;
        }
    }
}
