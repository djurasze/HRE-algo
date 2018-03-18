package com.juraszek.algorithm.utils.xmcda;

import com.juraszek.algorithm.heuristics.CalculationMethod;
import com.juraszek.algorithm.heuristics.methods.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class XMCDAProperties {

    @Autowired
    private HeuristicMethodManager heuristicMethodManager;
    private Map<Parameters, Object> parameters;

    public XMCDAProperties() {
        parameters = new HashMap<>();
        setDefaultParams();
    }

    private void setDefaultParams() {
        parameters.put(Parameters.MaxIterations, 100);
        parameters.put(Parameters.NormalizeResults, true);
        parameters.put(Parameters.WithSmallestError, false);
        parameters.put(Parameters.TryToReciprocalMatrix, false);
        parameters.put(Parameters.CalculationMethod, CalculationMethod.Arithmetic);
    }

    public void setParameter(Parameters parameter, Object value) {
        parameters.put(parameter, value);
    }

    public Object getParameter(Parameters parameter) {
        return this.parameters.get(parameter);
    }

    public void initHeuristicMethod() {
        heuristicMethodManager.setMethod(getMethod());
    }

    private HeuristicMethod getMethod() {
        if (CalculationMethod.MinimizingEstimationError.name().equals(parameters.get(Parameters.CalculationMethod)))
            return new MinimizingEstimationErrorMethod();
        else if (CalculationMethod.Geometric.name().equals(parameters.get(Parameters.CalculationMethod))) {
            return new GeometricMethod();
        }
        return new ArithmeticMethod();
    }
}
