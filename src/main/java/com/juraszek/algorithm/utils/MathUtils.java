package com.juraszek.algorithm.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

    public static Double round(Double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        if (value == null)
            return null;
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double sum(Double... values) {
        double result = 0;
        for (double value : values)
            result += value;
        return result;
    }

}
