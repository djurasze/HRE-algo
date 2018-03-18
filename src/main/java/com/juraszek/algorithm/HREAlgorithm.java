package com.juraszek.algorithm;

import com.juraszek.algorithm.heuristics.CalculationMethod;
import com.juraszek.algorithm.heuristics.MissingDataService;
import com.juraszek.algorithm.heuristics.ReciprocalMatrixConverter;
import com.juraszek.algorithm.heuristics.jacobi.JacobiAlgorithm;
import com.juraszek.algorithm.models.HREModel;
import com.juraszek.algorithm.models.Pair;
import com.juraszek.algorithm.utils.HREResultConverter;
import com.juraszek.algorithm.utils.xmcda.Parameters;
import com.juraszek.algorithm.utils.xmcda.XMCDAProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Dominik on 2017-05-21.
 */
@Service
public class HREAlgorithm {

    @Autowired
    private MissingDataService missingDataService;
    @Autowired
    private ReciprocalMatrixConverter reciprocalMatrixConverter;
    @Autowired
    private XMCDAProperties xmcdaProperties;
    @Autowired
    private JacobiAlgorithm jacobiAlgorithm;
    @Autowired
    private HREResultConverter hreResultConverter;

    public HREModel singleCalculate(HREModel inputs) throws Exception {

        Double[][] pairsComparisons = inputs.getPairsComparisons();
        Double[] alternativesWeights = inputs.getAlternativesWeights();
        pairsComparisons = missingDataService.calculateMissingDataIfOccurs(pairsComparisons);
        List<Pair> missingPairList = missingDataService.findMissingData(pairsComparisons);
        if (!missingPairList.isEmpty()) {
            pairsComparisons = missingDataService.tryToProposeMissingData(pairsComparisons, alternativesWeights, missingPairList);
        }
        if ((Boolean) xmcdaProperties.getParameter(Parameters.TryToReciprocalMatrix)) {
            pairsComparisons = reciprocalMatrixConverter.convertToReciprocalMatrix(pairsComparisons);
        }

        Double[] jacobiResult = jacobiAlgorithm.solve(pairsComparisons, alternativesWeights, missingPairList);
        if (CalculationMethod.Geometric.name().equals(xmcdaProperties.getParameter(Parameters.CalculationMethod))) {
            jacobiResult = hreResultConverter.rescaleResult(jacobiResult);
        }
        Double[] results = hreResultConverter.convertToResult(alternativesWeights, jacobiResult);
        if ((Boolean) xmcdaProperties.getParameter(Parameters.NormalizeResults)) {
            results = hreResultConverter.normalizeResult(results);
        }
        inputs.setAlternativesWeights(results);
        return inputs;
    }

}
