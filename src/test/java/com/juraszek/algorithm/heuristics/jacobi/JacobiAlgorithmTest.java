package com.juraszek.algorithm.heuristics.jacobi;

import com.juraszek.algorithm.utils.xmcda.Parameters;
import com.juraszek.algorithm.utils.xmcda.XMCDAProperties;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JacobiAlgorithmTest {

    @Mock
    private JacobiParametersCreator jacobiParametersCreator;

    @Mock
    private XMCDAProperties xmcdaProperties;

    @InjectMocks
    private JacobiAlgorithm jacobiAlgorithm;

    @Test
    public void solve() {
        //given
        Double[][] M = new Double[][]{
                {1d, 2d, 3d, 5d, 9d},
                {1 / 2d, 1d, 2d, 4d, 9d},
                {0.3333d, 1 / 2d, 1d, 2d, 8d},
                {1 / 5d, 1 / 4d, 1 / 2d, 1d, 7d},
                {0.1111, 0.1111, 0.125, 0.1428, 1d}
        };

        Double[] C = new Double[]{
                1d, null, null, null, null
        };

        final Double[][] EXPECTED_FACTORS = new Double[][]{
                {1d, -0.5d, -1d, -2.25d},
                {-0.125d, 1d, -0.5d, -2d},
                {-0.0625d, -0.125d, 1d, -1.75d},
                {-0.0278d, -0.0313d, -0.0357d, 1d}
        };

        final Double[] EXPECTED_CONSTANTS = new Double[]{
                0.125d, 0.0833d, 0.05d, 0.0278d
        };

        final Double[] EXPECTED_RESULT = new Double[]{
                0.8473692767223608,
                0.4946218909967829,
                0.3005314477574792,
                0.07756750376557794
        };


        //when
        when(jacobiParametersCreator.generateMatrixOfFactors(M, C)).thenReturn(EXPECTED_FACTORS);
        when(jacobiParametersCreator.generateVectorOfConstants(M, C)).thenReturn(EXPECTED_CONSTANTS);
        when(xmcdaProperties.getParameter(Parameters.MaxIterations)).thenReturn(100);
        when(xmcdaProperties.getParameter(Parameters.WithSmallestError)).thenReturn(false);

        Double[] result = jacobiAlgorithm.solve(M, C, Lists.emptyList());
        //then
        assertThat(result).isEqualTo(EXPECTED_RESULT);
    }
}
