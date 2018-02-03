package com.test.calculator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.stream.Stream;

/**
 * @author nxhoaf
 */
public class StringCalculator {
    private static final String DEFAULT_SEPARATOR = ",";
    
    public int add(String numbers) {
        if (StringUtils.isEmpty(numbers)) {
            return 0;
        }
        
        String[] numberStr = splitBySeparator(numbers);
        if (isContainInvalidNumber(numberStr)) {
            throw new IllegalArgumentException("Not a valid number: " + numbers);
        }
        
        return Stream.of(numberStr)
                .map(Integer::valueOf)
                .reduce(0, (n1, n2) -> n1 + n2);
    }

    private String[] splitBySeparator(String numbers) {
        return numbers.replaceAll("\\r?\\n", ",").split(DEFAULT_SEPARATOR);
    }

    private boolean isContainInvalidNumber(String[] numbers) {
        return Stream.of(numbers)
                .anyMatch(number -> !NumberUtils.isCreatable(number));
    }
}
