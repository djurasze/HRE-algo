package com.juraszek.algorithm.models;

import lombok.Getter;
import lombok.Setter;
import org.xmcda.Alternative;

import java.util.List;

@Getter
@Setter
public class HREModel {

    private Double[][] pairsComparisons;
    private Double[] alternativesWeights;
    private List<Alternative> alternatives;
}
