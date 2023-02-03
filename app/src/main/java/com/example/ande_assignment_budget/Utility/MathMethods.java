package com.example.ande_assignment_budget.Utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathMethods {
    // round off to decimal place
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
