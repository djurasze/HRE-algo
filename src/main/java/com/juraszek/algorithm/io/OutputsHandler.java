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
        final HashMap<String, XMCDA> x_results = new HashMap<>();

        XMCDA x_weighted_sum = new XMCDA();
        AlternativesValues<Double> x_alternatives_values = new AlternativesValues<>();

        for (int i = 0; i < model.getAlternatives().size(); i++) {
            x_alternatives_values.put(model.getAlternatives().get(i), model.getAlternativesWeights()[i]);
        }

        x_weighted_sum.alternativesValuesList.add(x_alternatives_values);

        x_results.put("alternativesValues", x_weighted_sum);

        return x_results;

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
