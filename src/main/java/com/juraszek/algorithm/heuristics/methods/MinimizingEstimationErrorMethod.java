package com.juraszek.algorithm.heuristics.methods;

public class MinimizingEstimationErrorMethod implements HeuristicMethod {
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
            return calculateSConstant(i, M) + 1;
        }
        Double matrixSize = (double) M.length;
        return -((M[i][j] + M[j][i]) / (matrixSize - 1));
    }

    private Double calculateSConstant(Integer index, Double[][] M) {
        Double result = 0d;
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M.length; j++) {
                if (j == index && i != index) {
                    result += M[i][j] * M[i][j];
                }
            }
        }
        result = (1 / (M.length - 1d)) * result;
        return result;
    }
}
