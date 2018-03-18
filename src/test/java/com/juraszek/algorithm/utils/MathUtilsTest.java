package com.juraszek.algorithm.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Dominik on 2017-05-21.
 */
public class MathUtilsTest {
    @Test
    public void roundDown() {
        //given
        Double givenNumber = 123.2312343243;
        Double expectedNumber = 123.2312;

        //when
        Double resultNumber = MathUtils.round(givenNumber, 4);

        //then
        assertThat(resultNumber).isEqualTo(expectedNumber);

    }

    @Test
    public void roundUp() {
        //given
        Double givenNumber = 123.2312943243;
        Double expectedNumber = 123.2313;

        //when
        Double resultNumber = MathUtils.round(givenNumber, 4);

        //then
        assertThat(resultNumber).isEqualTo(expectedNumber);

    }

    @Test
    public void sum() {
        //given
        Double[] givenNumbers = new Double[]{
                1.1, 3.2, 4.3, 2.3, 4.444, 5.1213
        };
        Double expectedResult = 20.4653;

        //when
        Double resultNumber = MathUtils.sum(givenNumbers);

        //then
        assertThat(resultNumber).isEqualTo(expectedResult);
    }
}