package com.juraszek.algorithm.utils.xmcda;

import com.juraszek.algorithm.heuristics.CalculationMethod;
import com.juraszek.algorithm.heuristics.methods.*;
import com.juraszek.algorithm.io.ProgramExecutionResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmcda.ProgramExecutionResult;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class XMCDAProperties {

    @Autowired
    private HeuristicMethodManager heuristicMethodManager;
    private Map<Parameters, Object> parameters;
    @Autowired
    private ProgramExecutionResultsService programExecutionResultsService;

    public XMCDAProperties() {
        parameters = new HashMap<>();
        setDefaultParams();
    }

    private void setDefaultParams() {
        parameters.put(Parameters.MaxIterations, 100);
        parameters.put(Parameters.NormalizeResults, true);
        parameters.put(Parameters.WithSmallestError, false);
        parameters.put(Parameters.TryToReciprocalMatrix, false);
        parameters.put(Parameters.CalculationMethod, "Arithmetic");
    }

    public void setParameter(Parameters parameter, Object value) {
        parameters.put(parameter, value);
    }

    public Object getParameter(Parameters parameter) {
        return this.parameters.get(parameter);
    }

    public void initHeuristicMethod(ProgramExecutionResult executionResult, File prgExecResults) throws Exception {
        try {
            heuristicMethodManager.setMethod(getMethod());
        } catch (Throwable t) {
            String message = XMCDAMessageParser.getMessage("The calculation could not be performed, reason: ", t);
            executionResult.addError(message);
            programExecutionResultsService.writeProgramExecutionResultsAndExit(prgExecResults, executionResult, message);
        }
    }

    private HeuristicMethod getMethod() throws Exception {
        if (CalculationMethod.MinimizingEstimationError.name().equals(parameters.get(Parameters.CalculationMethod)))
            return new MinimizingEstimationErrorMethod();
        else if (CalculationMethod.Geometric.name().equals(parameters.get(Parameters.CalculationMethod))) {
            return new GeometricMethod();
        } else if (CalculationMethod.Arithmetic.name().equals(parameters.get(Parameters.CalculationMethod))){
            return new ArithmeticMethod();
        }
        throw new Exception("Invalid calculation method: " + parameters.get(Parameters.CalculationMethod));
    }
}
