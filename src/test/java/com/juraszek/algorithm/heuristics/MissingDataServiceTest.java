package com.juraszek.algorithm.heuristics;

import com.juraszek.algorithm.models.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MissingDataServiceTest {
    @Test
    public void checkIfMissingDataOccursFalseTest() {
        //given
        Double[][] M = new Double[][]{
                {10d, 10d, 10d, 10d, 10d},
                {10d, 10d, 10d, 10d, 10d},
                {10d, 10d, 10d, 10d, 10d},
                {10d, 10d, 10d, 10d, 10d},
                {10d, 10d, 10d, 10d, 10d}
        };


        MissingDataService missingDataService = new MissingDataService();

        //when
        boolean result = missingDataService.checkIfMissingDataOccurs(M);

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void checkIfMissingDataOccursTrueTest() {
        //given
        Double[][] M = new Double[][]{
                {10d, 10d, 10d, 10d, 10d},
                {10d, 10d, 10d, null, 10d},
                {10d, 10d, 10d, 10d, 10d},
                {10d, 10d, 10d, 10d, 10d},
                {10d, 10d, 10d, 10d, 10d}
        };


        MissingDataService missingDataService = new MissingDataService();

        //when
        boolean result = missingDataService.checkIfMissingDataOccurs(M);

        //then
        assertThat(result).isTrue();
    }

    @Test
    public void calculateMissingDataIfDataNotMissingTest() {
        //given
        Double[][] M = new Double[][]{
                {10d, 10d, 10d, 10d, 10d},
                {10d, 10d, 10d, 20d, 10d},
                {10d, 10d, 10d, 10d, 10d},
                {10d, 10d, 10d, 10d, 10d},
                {10d, 10d, 10d, 10d, 10d}
        };


        MissingDataService missingDataService = new MissingDataService();

        //when
        Double[][] result = missingDataService.calculateMissingDataIfOccurs(M);
        //then
        assertThat(result).isEqualTo(M);
    }

    @Test
    public void calculateMissingDataIfDataMissingButReciprocalExistsTest() {
        //given
        Double[][] M = new Double[][]{
                {1d, 10d, 10d, 10d, null},
                {10d, 1d, 10d, 20d, null},
                {10d, 10d, 1d, 10d, 10d},
                {10d, 10d, 10d, 1d, 10d},
                {10d, 10d, 10d, 10d, 1d}
        };

        final Double[][] EXPECTED_RESULT = new Double[][]{
                {1d, 10d, 10d, 10d, 0.1d},
                {10d, 1d, 10d, 20d, 0.1d},
                {10d, 10d, 1d, 10d, 10d},
                {10d, 10d, 10d, 1d, 10d},
                {10d, 10d, 10d, 10d, 1d}
        };


        MissingDataService missingDataService = new MissingDataService();

        //when
        Double[][] result = missingDataService.calculateMissingDataIfOccurs(M);
        //then
        assertThat(result).isEqualTo(EXPECTED_RESULT);
    }

    @Test
    public void findMissingData() {
        //given
        Double[][] M = new Double[][]{
                {1d, 10d, null, 10d, null},
                {10d, 1d, 10d, 20d, null},
                {10d, 10d, 1d, 10d, 10d},
                {null, 10d, 10d, null, 10d},
                {null, 10d, 10d, 10d, 1d}
        };

        final List<Pair> EXPECTED_RESULT = new ArrayList<>();
        EXPECTED_RESULT.add(new Pair(0, 2));
        EXPECTED_RESULT.add(new Pair(0, 4));
        EXPECTED_RESULT.add(new Pair(1, 4));
        EXPECTED_RESULT.add(new Pair(3, 0));
        EXPECTED_RESULT.add(new Pair(3, 3));
        EXPECTED_RESULT.add(new Pair(4, 0));


        MissingDataService missingDataService = new MissingDataService();

        //when
        final List<Pair> result = missingDataService.findMissingData(M);
        //then
        assertThat(result).isEqualTo(EXPECTED_RESULT);
    }

    @Test
    public void tryToProposeMissingData() throws Exception {
        //given
        Double[][] M = new Double[][]{
                {1d, 1d, null, 5d},
                {1d, 1d, 2d, null},
                {null, 2d, 1d, 1d},
                {0.2, null, 1d, 1d}
        };

        Double[] C = new Double[]{
                1d, null, null, null
        };

        final Double[][] EXPECTED_RESULT = new Double[][]{
                {1d, 1d, 0.5, 5d},
                {1d, 1d, 2d, 5d},
                {2d, 2d, 1d, 1d},
                {0.2, 0.2d, 1d, 1d}
        };

        final List<Pair> missingData = new ArrayList<>();
        missingData.add(new Pair(0, 2));
        missingData.add(new Pair(1, 3));
        missingData.add(new Pair(2, 0));
        missingData.add(new Pair(3, 1));


        MissingDataService missingDataService = new MissingDataService();

        //when
        final Double[][] result = missingDataService.tryToProposeMissingData(M, C, missingData);
        //then
        assertThat(result).isEqualTo(EXPECTED_RESULT);
    }

    @Test
    public void tryToProposeMissingDataV2() throws Exception {
        //given
        Double[][] M = new Double[][]{
                {1d, 1d, 4d, 5d},
                {1d, 1d, 2d, null},
                {0.25, 2d, 1d, 1d},
                {0.2, null, 1d, 1d}
        };

        Double[] C = new Double[]{
                null, 1d, null, 2d
        };

        final Double[][] EXPECTED_RESULT = new Double[][]{
                {1d, 1d, 4d, 5d},
                {1d, 1d, 2d, 0.5d},
                {0.25d, 2d, 1d, 1d},
                {0.2, 2d, 1d, 1d}
        };

        final List<Pair> missingData = new ArrayList<>();
        missingData.add(new Pair(1, 3));
        missingData.add(new Pair(3, 1));


        MissingDataService missingDataService = new MissingDataService();

        //when
        final Double[][] result = missingDataService.tryToProposeMissingData(M, C, missingData);
        //then
        assertThat(result).isEqualTo(EXPECTED_RESULT);
    }

    @Test(expected = Exception.class)
    public void tryToProposeMissingDataFail() throws Exception {
        //given
        Double[][] M = new Double[][]{
                {1d, null, 4d, 5d},
                {null, 1d, null, null},
                {0.25, null, 1d, 1d},
                {0.2, null, 1d, 1d}
        };

        Double[] C = new Double[]{
                null, null, null, 2d
        };


        final List<Pair> missingData = new ArrayList<>();
        missingData.add(new Pair(1, 0));
        missingData.add(new Pair(1, 2));
        missingData.add(new Pair(1, 3));
        missingData.add(new Pair(0, 1));
        missingData.add(new Pair(2, 1));
        missingData.add(new Pair(3, 1));


        MissingDataService missingDataService = new MissingDataService();

        //when
        final Double[][] result = missingDataService.tryToProposeMissingData(M, C, missingData);
        //then

    }

    @Test
    public void updateProposedPairs() {
        //given
        Double[][] M = new Double[][]{
                {1d, 1d, 0.5, 5d},
                {1d, 1d, 2d, 5d},
                {2d, 2d, 1d, 1d},
                {0.2, 0.2d, 1d, 1d}
        };

        Double[] C = new Double[]{
                1d, 2d, 1d, 2d
        };

        final Double[][] EXPECTED_RESULT = new Double[][]{
                {1d, 1d, 1d, 5d},
                {1d, 1d, 2d, 1d},
                {1d, 2d, 1d, 1d},
                {0.2, 1d, 1d, 1d}
        };


        final List<Pair> missingData = new ArrayList<>();
        missingData.add(new Pair(0, 2));
        missingData.add(new Pair(1, 3));
        missingData.add(new Pair(2, 0));
        missingData.add(new Pair(3, 1));


        MissingDataService missingDataService = new MissingDataService();

        //when
        final Double[][] result = missingDataService.updateProposedPairs(M, C, missingData);
        //then
        assertThat(result).isEqualTo(EXPECTED_RESULT);
    }
}