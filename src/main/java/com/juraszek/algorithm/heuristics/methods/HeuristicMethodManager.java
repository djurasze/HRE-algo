package com.juraszek.algorithm.heuristics.methods;

import com.juraszek.algorithm.utils.HREResultConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeuristicMethodManager {

    @Autowired
    private HREResultConverter hreResultConverter;

    private HeuristicMethod method;

    public HeuristicMethod getMethod() {
        return method;
    }

    public void setMethod(HeuristicMethod method) {
        this.method = method;
    }

    public Double calculateError(Double[] results, Double[][] inputMatrix, Double[] C, Double[] prevC) {
        Double[] filledC = hreResultConverter.convertToResult(C, results);

        Double errorsSum = 0d;
        for (int i = 0; i < C.length; i++) {
            int counter = 0;
            for (int j = 0; j < C.length; j++) {
                if (C[i] == null) {
                    errorsSum += Math.abs(filledC[counter] - prevC[j] * inputMatrix[i][j]);
                    counter++;
                }
            }
            errorsSum = 1d / (C.length) * errorsSum;

        }
        return (1d / results.length) * errorsSum;

    }
}
