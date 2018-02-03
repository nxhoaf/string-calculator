package com.test.calculator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author nxhoaf
 */
public class StringCalculator {
    public int add(String numbers) {
        if (StringUtils.isEmpty(numbers)) {
            return 0;
        }

        if (!NumberUtils.isCreatable(numbers)) {
            throw new IllegalArgumentException("Not a valid number: " + numbers);
        }

        return Integer.valueOf(numbers);
    }
}
