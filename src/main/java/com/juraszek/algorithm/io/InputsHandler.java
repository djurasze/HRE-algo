package com.juraszek.algorithm.io;

import com.juraszek.algorithm.models.HREModel;
import com.juraszek.algorithm.utils.xmcda.Parameters;
import com.juraszek.algorithm.utils.xmcda.XMCDAMessageParser;
import com.juraszek.algorithm.utils.xmcda.XMCDAProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmcda.*;
import org.xmcda.utils.Coord;
import org.xmcda.utils.ValueConverters;

import java.util.ArrayList;
import java.util.List;

@Service
public class InputsHandler {

    @Autowired
    XMCDAProperties xmcdaProperties;

    public HREModel checkAndExtractInputs(XMCDA xmcda, ProgramExecutionResult executionResult) {
        checkInputs(xmcda, executionResult);

        if (executionResult.isError())
            return null;

        return extractInputs(xmcda);
    }

    private HREModel extractInputs(XMCDA xmcda) {

        HREModel inputs = new HREModel();

        inputs.setAlternatives(extractAlternatives(xmcda));
        inputs.setAlternativesWeights(extractAlterantivesWeights(xmcda, inputs.getAlternatives()));
        inputs.setPairsComparisons(extractPairsComparisons(xmcda, inputs.getAlternatives()));
        extractParameters(xmcda);

        return inputs;
    }

    private Double[][] extractPairsComparisons(XMCDA xmcda, List<Alternative> alternatives) {
        Double[][] pairsComparisons = new Double[alternatives.size()][];
        for (int i = 0; i < alternatives.size(); i++) {
            pairsComparisons[i] = new Double[alternatives.size()];
            for (int j = 0; j < alternatives.size(); j++) {
                QualifiedValues<?> qualifiedValues = xmcda.alternativesMatricesList.get(0).get(new Coord<>(alternatives.get(i), alternatives.get(j)));
                if (qualifiedValues != null) {
                    pairsComparisons[i][j] = (Double) qualifiedValues.get(0).getValue();

                } else {
                    pairsComparisons[i][j] = null;
                }
            }
        }
        return pairsComparisons;
    }

    private Double[] extractAlterantivesWeights(XMCDA xmcda, List<Alternative> alternatives) {
        Double[] alternativesWeights = new Double[alternatives.size()];
        for (int i = 0; i < alternatives.size(); i++) {
            LabelledQValues<?> qualifiedValues = xmcda.alternativesValuesList.get(0).get(alternatives.get(i));
            if (qualifiedValues != null) {
                alternativesWeights[i] = (Double) qualifiedValues.get(0).getValue();

            } else {
                alternativesWeights[i] = null;
            }
        }
        return alternativesWeights;
    }

    private List<Alternative> extractAlternatives(XMCDA xmcda) {
        List<Alternative> alternatives = new ArrayList<>();
        for (Alternative alternative : xmcda.alternatives)
            if (alternative.isActive())
                alternatives.add(alternative);
        return alternatives;
    }

    private void extractParameters(XMCDA xmcda) {
        if (xmcda.programParametersList.size() > 0) {
            xmcda.programParametersList.get(0).forEach(programParameter ->
                    xmcdaProperties.setParameter(Parameters.valueOf(programParameter.id()),
                            programParameter.getValues().get(0).getValue()));
        }
    }

    private void checkInputs(XMCDA xmcda, ProgramExecutionResult errors) {

        checkAlternatives(xmcda, errors);
        checkAlternativesValues(xmcda, errors);
        checkAlternativesMatrix(xmcda, errors);
        checkParameters(xmcda, errors);
    }

    private void checkAlternativesMatrix(XMCDA xmcda, ProgramExecutionResult errors) {
        if (xmcda.alternativesMatricesList.size() == 0) {
            errors.addError("Alternative values matrix is empty");
        } else if (xmcda.alternativesMatricesList.size() > 1) {
            errors.addError("More than one alternative values matrix has been supplied");
        } else {
            xmcda.alternatives.getActiveAlternatives()
                    .forEach(alternative1 -> xmcda.alternatives.getActiveAlternatives()
                            .forEach(alternative2 -> {
                                try {
                                    QualifiedValues<?> rawQualifiedValues = xmcda.alternativesMatricesList.get(0).get(alternative1, alternative2);
                                    if (rawQualifiedValues != null) {
                                        QualifiedValues<Double> qualifiedValues = rawQualifiedValues.convertToDouble();
                                        if (qualifiedValues.size() != 1) {
                                            final String msg = "Error when converting the alternative matrix pairs to Double, reason: Pairs can only contain one value";
                                            errors.addError(msg);
                                        }
                                    }
                                } catch (ValueConverters.ConversionException e) {
                                    final String msg = "Error when converting the alternative matrix pairs to Double, reason:";
                                    errors.addError(XMCDAMessageParser.getMessage(msg, e));
                                }
                            }));
        }
    }

    private void checkParameters(XMCDA xmcda, ProgramExecutionResult errors) {
        try {
            if (xmcda.programParametersList.size() > 0) {
                xmcda.programParametersList.get(0)
                        .forEach(programParameter -> Parameters.valueOf(programParameter.id()));
            }
        } catch (IllegalArgumentException e) {
            final String msg = "Invalid parameter name found, reason:";
            errors.addError(XMCDAMessageParser.getMessage(msg, e));
        }
    }

    private void checkAlternativesValues(XMCDA xmcda, ProgramExecutionResult errors) {
        if (xmcda.alternativesValuesList.size() > 1) {
            errors.addError("More than one alternatives values vectors has been supplied");
        } else {
            try {
                if (xmcda.alternativesValuesList.isEmpty()) {
                    AlternativesValues<Double> doubleAlternativesValues = new AlternativesValues<>();
                    Alternative randomAlternative = xmcda.alternatives.getActiveAlternatives().get(0);
                    doubleAlternativesValues.put(randomAlternative, 1.0);
                    xmcda.alternativesValuesList.add(doubleAlternativesValues);
                } else {
                    AlternativesValues<Double> doubleAlternativesValues = xmcda.alternativesValuesList.get(0).asDouble();
                    xmcda.alternativesValuesList.set(0, doubleAlternativesValues);
                }
            } catch (Exception e) {
                final String msg = "Error when converting the alternative values to Double, reason:";
                errors.addError(XMCDAMessageParser.getMessage(msg, e));

            }
        }
    }

    private void checkAlternatives(XMCDA xmcda, ProgramExecutionResult errors) {
        if (xmcda.alternatives.size() == 0) {
            errors.addError("No alternatives has been supplied");
        }
    }

}
