package com.juraszek.algorithm.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HREResultConverterTest {

    @Test
    public void convertToResult() {
        //given
        Double[] GIVEN_RESULT = new Double[]{
                1d, null, null, null, null
        };

        Double[] JACOBI_RESULT = new Double[]{
                0.8473692767223608, 0.4946218909967829, 0.3005314477574792, 0.07756750376557794
        };

        Double[] EXPECTED_RESULT = new Double[]{
                1d, 0.8473692767223608, 0.4946218909967829, 0.3005314477574792, 0.07756750376557794
        };

        HREResultConverter hreResultConverter = new HREResultConverter();

        //when
        Double[] result = hreResultConverter.convertToResult(GIVEN_RESULT, JACOBI_RESULT);

        //then
        assertThat(result).isEqualTo(EXPECTED_RESULT);
    }

    @Test
    public void normalizeResult() {
        //given
        Double[] GIVEN_RESULT = new Double[]{
                1d, 0.8473692767223608, 0.4946218909967829, 0.3005314477574792, 0.07756750376557794
        };

        Double[] EXPECTED_RESULT = new Double[]{
                0.368, 0.312, 0.182, 0.11, 0.029
        };


        HREResultConverter hreResultConverter = new HREResultConverter();

        //when
        Double[] result = hreResultConverter.normalizeResult(GIVEN_RESULT);

        //then
        assertThat(result).isEqualTo(EXPECTED_RESULT);
    }

    @Test
    public void rescaleResult() {

        //given
        Double[] GIVEN_RESULT = new Double[]{
                0.5578699999999988, 0.29520999999999875, 0.19706999999999875
        };

        Double[] EXPECTED_RESULT = new Double[]{
                3.6130169582445144, 1.9733767176914996, 1.574236580976425
        };


        HREResultConverter hreResultConverter = new HREResultConverter();

        //when
        Double[] result = hreResultConverter.rescaleResult(GIVEN_RESULT);

        //then
        assertThat(result).isEqualTo(EXPECTED_RESULT);
    }
}