package com.juraszek.algorithm.heuristics.methods;


public class ArithmeticMethod implements HeuristicMethod {
    @Override
    public Double calculateConstant(Integer index, Double[][] M, Double[] C) {
        Double constantValue = 0d;
        for (int i = 0; i < C.length; i++) {
            if (C[i] != null) {
                constantValue += M[index][i] * C[i];
            }
        }
        Double matrixSize = (double) M.length;
        constantValue = (1 / (matrixSize - 1d)) * constantValue;
        return constantValue;
    }

    @Override
    public Double calculateFactor(Integer i, Integer j, Double[][] M) {
        if (i.equals(j)) {
            return 1d;
        }
        Double factorValue = M[i][j];
        Double matrixSize = (double) M.length;
        factorValue = -(1 / (matrixSize - 1)) * factorValue;
        return factorValue;
    }
}
