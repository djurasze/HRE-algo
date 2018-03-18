package com.juraszek.algorithm.heuristics.methods;


public class GeometricMethod implements HeuristicMethod {
    @Override
    public Double calculateConstant(Integer index, Double[][] M, Double[] C) {
        Double constantValue = 1d;
        for (int i = 0; i < C.length; i++) {
            if (C[i] != null) {
                constantValue *= M[index][i] * C[i];
            } else if (i != index) {
                constantValue *= M[index][i];
            }
        }
        return Math.log10(constantValue);
    }

    @Override
    public Double calculateFactor(Integer i, Integer j, Double[][] M) {
        if (i.equals(j)) {
            return M.length - 1d;
        } else {
            return -1d;
        }
    }
}
