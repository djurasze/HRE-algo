package com.juraszek.algorithm.heuristics;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReciprocalMatrixConverterTest {

    @Test
    public void convertToReciprocalMatrixTest() {
        //given
        Double[][] M = new Double[][]{
                {1d, 10d, 0.05d},
                {0.2d, 1d, 0.5d},
                {20d, 3d, 1d}
        };

        final Double[][] EXPECTED_MATRIX = new Double[][]{
                {1.0d, 7.0710678118654755, 0.05},
                {0.14142135623730953, 1.0, 0.408248290463863},
                {20.0, 2.449489742783178, 1.0d}
        };

        ReciprocalMatrixConverter reciprocalMatrixConverter = new ReciprocalMatrixConverter();


        //when
        Double[][] result = reciprocalMatrixConverter.convertToReciprocalMatrix(M);

        //then
        assertThat(result).isEqualTo(EXPECTED_MATRIX);
    }


    @Test
    public void convertToReciprocalMatrixIfMissingDataTest() {
        //given
        Double[][] M = new Double[][]{
                {1d, null, 0.05d},
                {0.2d, 1d, 0.5d},
                {20d, 3d, 1d}
        };

        final Double[][] EXPECTED_MATRIX = new Double[][]{
                {1.0d, null, 0.05d},
                {0.2d, 1.0d, 0.408248290463863},
                {20.0d, 2.449489742783178, 1.0d}
        };

        ReciprocalMatrixConverter reciprocalMatrixConverter = new ReciprocalMatrixConverter();


        //when
        Double[][] result = reciprocalMatrixConverter.convertToReciprocalMatrix(M);

        //then
        assertThat(result).isEqualTo(EXPECTED_MATRIX);
    }

}