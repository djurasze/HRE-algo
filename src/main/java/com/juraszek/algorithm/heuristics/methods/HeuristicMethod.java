package com.juraszek.algorithm.heuristics.methods;

public interface HeuristicMethod {
    Double calculateConstant(Integer integer, Double[][] m, Double[] c);

    Double calculateFactor(Integer integer, Integer integer1, Double[][] m);
}
