package com.juraszek.algorithm.heuristics.jacobi;

import com.juraszek.algorithm.heuristics.MissingDataService;
import com.juraszek.algorithm.heuristics.methods.HeuristicMethodManager;
import com.juraszek.algorithm.models.Pair;
import com.juraszek.algorithm.utils.HREResultConverter;
import com.juraszek.algorithm.utils.xmcda.Parameters;
import com.juraszek.algorithm.utils.xmcda.XMCDAProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class JacobiAlgorithm {

    @Autowired
    private XMCDAProperties xmcdaProperties;
    @Autowired
    private JacobiParametersCreator jacobiParametersCreator;
    @Autowired
    private HREResultConverter hreResultConverter;
    @Autowired
    private MissingDataService missingDataService;
    @Autowired
    private HeuristicMethodManager heuristicMethodManager;

    public Double[] solve(Double[][] pairsComparisons, Double[] alternativesWeights, List<Pair> missingPairList) {

        Double[] constants = jacobiParametersCreator.generateVectorOfConstants(pairsComparisons, alternativesWeights);
        Double[][] factors = jacobiParametersCreator.generateMatrixOfFactors(pairsComparisons, alternativesWeights);

        Double[][] inputMatrix = prepareInputMatrix(factors, constants);

        int iterations = 0;
        int inputSize = inputMatrix.length;
        Double epsilon = 1e-15;
        Double[] currentIterationResult = new Double[inputSize];
        Double[] previousIterationResult = new Double[inputSize];
        Double[] resultWithLowestError = new Double[inputSize];
        Double smallestError = null;
        Arrays.fill(currentIterationResult, 0d);
        Arrays.fill(previousIterationResult, 0d);

        while (true) {
            for (int i = 0; i < inputSize; i++) {
                double sum = inputMatrix[i][inputSize]; // b_n

                for (int j = 0; j < inputSize; j++)
                    if (j != i)
                        sum -= inputMatrix[i][j] * previousIterationResult[j];
                currentIterationResult[i] = 1 / inputMatrix[i][i] * sum;
            }

            iterations++;
            if (iterations == 1) continue;

            boolean stop = true;
            for (int i = 0; i < inputSize && stop; i++)
                if (Math.abs(currentIterationResult[i] - previousIterationResult[i]) > epsilon)
                    stop = false;

            if ((Boolean) xmcdaProperties.getParameter(Parameters.WithSmallestError)) {
                if (iterations >= 2) {
                    Double error = heuristicMethodManager.calculateError(currentIterationResult,
                            pairsComparisons, alternativesWeights, hreResultConverter.convertToResult(alternativesWeights, previousIterationResult));
                    if ((smallestError == null || error < smallestError)) {
                        smallestError = error;
                        resultWithLowestError = currentIterationResult.clone();
                    }
                }
            }

            if (stop || iterations >= (int) xmcdaProperties.getParameter(Parameters.MaxIterations))
                break;
            previousIterationResult = currentIterationResult.clone();
            if (!missingPairList.isEmpty()) {
                inputMatrix = updateMissingPairs(pairsComparisons, alternativesWeights, previousIterationResult, missingPairList);
            }


        }
        return (Boolean) xmcdaProperties.getParameter(Parameters.WithSmallestError) ? resultWithLowestError : previousIterationResult;
    }

    private Double[][] updateMissingPairs(Double[][] pairsComparisons, Double[] allAlternativesWeights,
                                          Double[] currentAlternativesWeights, List<Pair> missingPairList) {

        pairsComparisons = missingDataService.updateProposedPairs(pairsComparisons,
                hreResultConverter.convertToResult(allAlternativesWeights, currentAlternativesWeights), missingPairList);
        Double[] constants = jacobiParametersCreator.generateVectorOfConstants(pairsComparisons, allAlternativesWeights);
        Double[][] factors = jacobiParametersCreator.generateMatrixOfFactors(pairsComparisons, allAlternativesWeights);
        return prepareInputMatrix(factors, constants);
    }

    private Double[][] prepareInputMatrix(Double[][] factors, Double[] constants) {
        Double[][] tem = new Double[factors.length][factors.length + 1];
        for (int i = 0; i < factors.length; i++) {
            for (int j = 0; j < factors.length + 1; j++) {
                if (j != factors.length) {
                    tem[i][j] = factors[i][j];
                } else {
                    tem[i][j] = constants[i];
                }
            }
        }
        return tem;
    }


}

