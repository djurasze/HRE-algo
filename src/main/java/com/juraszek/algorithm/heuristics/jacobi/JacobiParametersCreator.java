package com.juraszek.algorithm.heuristics.jacobi;

import com.juraszek.algorithm.heuristics.methods.HeuristicMethodManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by Dominik on 2017-05-21.
 */
@Service
public class JacobiParametersCreator {

    @Autowired
    private HeuristicMethodManager heuristicMethodManager;


    public Double[] generateVectorOfConstants(Double[][] M, Double[] C) {
        int vectorSize = calculateVectorSize(C);
        Double[] vectorOfConstants = new Double[vectorSize];
        Map<Integer, Integer> indexesMap = mapIndexesFromC(C);

        for (int i = 0; i < vectorSize; i++) {
            vectorOfConstants[i] = heuristicMethodManager.getMethod().calculateConstant(indexesMap.get(i), M, C);
        }
        return vectorOfConstants;
    }


    public Double[][] generateMatrixOfFactors(Double[][] M, Double[] C) {
        int vectorSize = calculateVectorSize(C);
        Double[][] matrixOfFactors = new Double[vectorSize][vectorSize];
        Map<Integer, Integer> indexesMap = mapIndexesFromC(C);

        for (int i = 0; i < vectorSize; i++) {
            for (int j = 0; j < vectorSize; j++) {
                matrixOfFactors[i][j] = heuristicMethodManager.getMethod().calculateFactor(indexesMap.get(i), indexesMap.get(j), M);
            }
        }
        return matrixOfFactors;
    }

    private Map<Integer, Integer> mapIndexesFromC(Double[] c) {
        Map<Integer, Integer> indexesMap = new HashMap<>();
        Integer mappedIndex = 0;
        for (int i = 0; i < c.length; i++) {
            if (c[i] == null) {
                indexesMap.put(mappedIndex, i);
                mappedIndex++;
            }
        }
        return indexesMap;
    }

    private int calculateVectorSize(Double[] c) {
        return Math.toIntExact(Stream.of(c).filter(Objects::isNull).count());
    }


}
