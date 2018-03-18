package com.juraszek.algorithm.heuristics;

import com.juraszek.algorithm.models.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created by Dominik on 2017-05-21.
 */
@Service
public class MissingDataService {

    public boolean checkIfMissingDataOccurs(Double[][] pairsComparisons) {
        return Stream.of(pairsComparisons)
                .flatMap(Stream::of)
                .anyMatch(Objects::isNull);
    }

    private Double[][] calculateMissingData(Double[][] pairsComparisons) {
        int dataSize = pairsComparisons.length;
        Double[][] dataWithoutMissingElements = new Double[dataSize][dataSize];
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < dataSize; j++) {
                if (pairsComparisons[i][j] == null) {
                    dataWithoutMissingElements[i][j] = tryCalculateUsingReciprocalProperty(pairsComparisons[j][i]);
                } else {
                    dataWithoutMissingElements[i][j] = pairsComparisons[i][j];
                }
            }
        }
        return dataWithoutMissingElements;
    }

    public Double[][] calculateMissingDataIfOccurs(Double[][] pairsComparisons) {
        if (checkIfMissingDataOccurs(pairsComparisons)) {
            return calculateMissingData(pairsComparisons);
        }
        return pairsComparisons;
    }


    private Double tryCalculateUsingReciprocalProperty(Double reciprocalValue) {
        if (reciprocalValue != null) {
            return 1 / reciprocalValue;
        } else {
            return null;
        }
    }

    public List<Pair> findMissingData(Double[][] pairsComparisons) {
        List<Pair> pairs = new ArrayList<>();
        int dataSize = pairsComparisons.length;
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < dataSize; j++) {
                if (pairsComparisons[i][j] == null) {
                    pairs.add(new Pair(i, j));
                }
            }
        }
        return pairs;
    }

    public Double[][] tryToProposeMissingData(Double[][] pairsComparisons, Double[] alternativesWeights, List<Pair> missingPairList) throws Exception {
        for (Pair pair : missingPairList) {
            if (alternativesWeights[pair.getX()] != null && alternativesWeights[pair.getY()] != null) {
                pairsComparisons[pair.getX()][pair.getY()] = alternativesWeights[pair.getX()] / alternativesWeights[pair.getY()];
            } else {
                Double cX = alternativesWeights[pair.getX()], cY = alternativesWeights[pair.getY()];
                if (alternativesWeights[pair.getX()] == null) {
                    cX = tryToProposeWeightForAlternative(pair.getX(), pairsComparisons, alternativesWeights, new ArrayList<>());
                }
                if (alternativesWeights[pair.getY()] == null) {
                    cY = tryToProposeWeightForAlternative(pair.getY(), pairsComparisons, alternativesWeights, new ArrayList<>());
                }
                if (cX != null && cY != null) {
                    pairsComparisons[pair.getX()][pair.getY()] = cX / cY;
                } else {
                    throw new Exception("Can not propose values for pairs comparisons");
                }
            }
        }
        return pairsComparisons;
    }

    private Double tryToProposeWeightForAlternative(int alternativeID, Double[][] pairsComparisons, Double[] alternativesWeights, List<Integer> visited) {
        for (int i = 0; i < alternativesWeights.length; i++) {
            Double tmp = pairsComparisons[alternativeID][i];
            if (tmp != null && alternativesWeights[i] != null) {
                return tmp * alternativesWeights[i];
            } else if (tmp != null && !visited.contains(i)) {
                visited.add(alternativeID);
                Double proposedC = tryToProposeWeightForAlternative(i, pairsComparisons, alternativesWeights, visited);
                if (proposedC != null)
                    return tmp * proposedC;
                else
                    return null;
            }
        }
        return null;
    }


    public Double[][] updateProposedPairs(Double[][] pairsComparisons, Double[] alternativesWeights, List<Pair> missingPairList) {
        for (Pair pair : missingPairList) {
            pairsComparisons[pair.getX()][pair.getY()] = alternativesWeights[pair.getX()] / alternativesWeights[pair.getY()];
        }
        return pairsComparisons;
    }
}
