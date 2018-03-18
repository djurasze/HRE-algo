package com.juraszek.algorithm.io;

import com.juraszek.algorithm.models.HREModel;
import org.springframework.stereotype.Service;
import org.xmcda.AlternativesValues;
import org.xmcda.XMCDA;

import java.util.HashMap;
import java.util.Map;

@Service
public class OutputsHandler {
    public Map<String, XMCDA> convert(HREModel model) {
        final HashMap<String, XMCDA> results = new HashMap<>();

        XMCDA weightedSum = new XMCDA();
        AlternativesValues<Double> alternativesValues = new AlternativesValues<>();

        for (int i = 0; i < model.getAlternatives().size(); i++) {
            alternativesValues.put(model.getAlternatives().get(i), model.getAlternativesWeights()[i]);
        }

        weightedSum.alternativesValuesList.add(alternativesValues);
        results.put("alternativesValues", weightedSum);

        return results;
    }


    public String xmcdaV3Tag(String outputName) {
        switch (outputName) {
            case "alternativesValues":
                return "alternativesValues";
            case "messages":
                return "programExecutionResult";
            default:
                throw new IllegalArgumentException(String.format("Unknown output name '%s'", outputName));
        }
    }

}
