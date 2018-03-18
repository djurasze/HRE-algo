package com.juraszek.algorithm.heuristics.jacobi;

import com.juraszek.algorithm.heuristics.methods.ArithmeticMethod;
import com.juraszek.algorithm.heuristics.methods.GeometricMethod;
import com.juraszek.algorithm.heuristics.methods.HeuristicMethodManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JacobiParametersCreatorTest {

    @Mock
    private HeuristicMethodManager heuristicMethodManager;

    @InjectMocks
    private JacobiParametersCreator jacobiParametersCreator;

    @Test
    public void generateVectorOfArithmeticConstantsTest() {
        //given
        Double[][] M = new Double[][]{
                {1d, 3 / 5d, 4 / 7d, 5 / 8d, 1 / 2d},
                {5 / 3d, 1d, 5 / 7d, 5 / 2d, 10 / 3d},
                {4 / 7d, 7 / 5d, 1d, 7 / 2d, 4d},
                {8 / 5d, 2 / 5d, 2 / 7d, 1d, 4 / 3d},
                {2d, 3 / 10d, 1 / 4d, 3 / 4d, 1d}
        };

        Double[] C = new Double[]{
                null, 5d, 7d, null, null
        };

        final Double[] EXPECTED_CONSTANTS = new Double[]{
                1.75d, 1d, 0.8125d
        };


        //when
        when(heuristicMethodManager.getMethod()).thenReturn(new ArithmeticMethod());

        Double[] resultConstants = jacobiParametersCreator.generateVectorOfConstants(M, C);

        //then
        assertThat(resultConstants).isEqualTo(EXPECTED_CONSTANTS);

    }


    @Test
    public void generateVectorOfArithmeticConstantsSecondScenarioTest() {
        //given
        Double[][] M = new Double[][]{
                {1d, 2d, 3d, 5d, 9d},
                {1 / 2d, 1d, 2d, 4d, 9d},
                {1 / 3d, 1 / 2d, 1d, 2d, 8d},
                {1 / 5d, 1 / 4d, 1 / 2d, 1d, 7d},
                {1 / 9d, 1 / 9d, 1 / 8d, 1 / 7d, 1d}
        };

        Double[] C = new Double[]{
                1d, null, null, null, null
        };

        final Double[] EXPECTED_CONSTANTS = new Double[]{
                0.125d, 0.08333333333333333, 0.05, 0.027777777777777776
        };


        //when
        when(heuristicMethodManager.getMethod()).thenReturn(new ArithmeticMethod());

        Double[] resultConstants = jacobiParametersCreator.generateVectorOfConstants(M, C);

        //then
        assertThat(resultConstants).isEqualTo(EXPECTED_CONSTANTS);

    }


    @Test
    public void generateArithmeticMatrixOfFactorsTest() {
        //given
        Double[][] M = new Double[][]{
                {1d, 2d, 3d, 5d, 9d},
                {1 / 2d, 1d, 2d, 4d, 9d},
                {1 / 3d, 1 / 2d, 1d, 2d, 8d},
                {1 / 5d, 1 / 4d, 1 / 2d, 1d, 7d},
                {1 / 9d, 1 / 9d, 1 / 8d, 1 / 7d, 1d}
        };

        Double[] C = new Double[]{
                1d, null, null, null, null
        };

        final Double[][] EXPECTED_FACTORS = new Double[][]{
                {1d, -0.5d, -1d, -2.25d},
                {-0.125d, 1d, -0.5d, -2d},
                {-0.0625d, -0.125d, 1d, -1.75d},
                {-0.027777777777777776,
                        -0.03125,
                        -0.03571428571428571, 1d}
        };


        //when
        when(heuristicMethodManager.getMethod()).thenReturn(new ArithmeticMethod());

        Double[][] resultFactors = jacobiParametersCreator.generateMatrixOfFactors(M, C);
        //then
        assertThat(resultFactors).isEqualTo(EXPECTED_FACTORS);

    }

    @Test
    public void generateArithmeticMatrixOfFactorsSecondScenarioTest() {
        //given
        Double[][] M = new Double[][]{
                {1d, 3 / 5d, 4 / 7d, 5 / 8d, 1 / 2d},
                {5 / 3d, 1d, 5 / 7d, 5 / 2d, 10 / 3d},
                {4 / 7d, 7 / 5d, 1d, 7 / 2d, 4d},
                {8 / 5d, 2 / 5d, 2 / 7d, 1d, 4 / 3d},
                {2d, 3 / 10d, 1 / 4d, 3 / 4d, 1d}
        };

        Double[] C = new Double[]{
                null, 5d, 7d, null, null
        };

        final Double[][] EXPECTED_FACTORS = new Double[][]{
                {1d, -0.15625, -0.125d},
                {-0.4d, 1d, -0.3333333333333333},
                {-0.5d, -0.1875d, 1d}
        };


        //when
        when(heuristicMethodManager.getMethod()).thenReturn(new ArithmeticMethod());

        Double[][] resultFactors = jacobiParametersCreator.generateMatrixOfFactors(M, C);
        //then
        assertThat(resultFactors).isEqualTo(EXPECTED_FACTORS);

    }


    @Test
    public void generateVectorOfGeometricConstantsTest() {
        //given
        Double[][] M = new Double[][]{
                {1d, 3 / 5d, 4 / 7d, 2d, 16 / 7d},
                {5 / 3d, 1d, 5 / 7d, 5 / 2d, 10 / 3d},
                {7 / 4d, 7 / 5d, 1d, 7 / 2d, 4d},
                {1 / 2d, 2 / 5d, 2 / 7d, 1d, 4 / 3d},
                {7 / 16d, 3 / 10d, 1 / 4d, 3 / 4d, 1d}
        };

        Double[] C = new Double[]{
                null, 5d, 7d, null, null
        };

        final Double[] EXPECTED_CONSTANTS = new Double[]{
                1.739233184353274, 0.4259687322722811, -0.06483137150799222
        };


        //when
        when(heuristicMethodManager.getMethod()).thenReturn(new GeometricMethod());

        Double[] resultConstants = jacobiParametersCreator.generateVectorOfConstants(M, C);

        //then
        assertThat(resultConstants).isEqualTo(EXPECTED_CONSTANTS);

    }

    @Test
    public void generateGeometricMatrixOfFactorsTest() {
        //given
        Double[][] M = new Double[][]{
                {1d, 3 / 5d, 4 / 7d, 2d, 16 / 7d},
                {5 / 3d, 1d, 5 / 7d, 5 / 2d, 10 / 3d},
                {7 / 4d, 7 / 5d, 1d, 7 / 2d, 4d},
                {1 / 2d, 2 / 5d, 2 / 7d, 1d, 4 / 3d},
                {7 / 16d, 3 / 10d, 1 / 4d, 3 / 4d, 1d}
        };

        Double[] C = new Double[]{
                null, 5d, 7d, null, null
        };

        final Double[][] EXPECTED_FACTORS = new Double[][]{
                {4d, -1d, -1d},
                {-1d, 4d, -1d},
                {-1d, -1d, 4d}
        };


        //when
        when(heuristicMethodManager.getMethod()).thenReturn(new GeometricMethod());


        Double[][] resultFactors = jacobiParametersCreator.generateMatrixOfFactors(M, C);
        //then
        assertThat(resultFactors).isEqualTo(EXPECTED_FACTORS);

    }

}