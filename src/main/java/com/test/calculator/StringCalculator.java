package com.test.calculator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author nxhoaf
 */
public class StringCalculator {
    private static final String DEFAULT_DELIMITER = ",";
    private static final String DELIMITER_SECTION = "//";
    private static final int MAX_SUPPORTED_NUMBER = 1000;
    
    public int add(String numbers) {
        if (StringUtils.isEmpty(numbers)) {
            return 0;
        }
        
        String delimiter = DEFAULT_DELIMITER;
        String content = numbers;
        if (numbers.contains(DELIMITER_SECTION)) {
            delimiter = getDelimiter(numbers);
            content = getContent(numbers);
        }
        
        ensureInputCorrect(content, delimiter);
        String[] numberStr = splitBySeparator(content, delimiter);

        return Stream.of(numberStr)
                .map(StringUtils::trim)
                .map(Integer::valueOf)
                .filter(number -> number < MAX_SUPPORTED_NUMBER)
                .reduce(0, (n1, n2) -> n1 + n2);
    }


    private void ensureInputCorrect(String content, String delimiter) {
        String[] numberStr = splitBySeparator(content, delimiter);
        
        if (isContainInvalidNumber(numberStr)) {
            throw new IllegalArgumentException("Not a valid number: " + content);
        }
        
        List<Integer> negativeNumbers = Stream.of(numberStr)
                .map(StringUtils::trim)
                .map(Integer::valueOf)
                .filter(number -> number < 0)
                .collect(Collectors.toList());
        
        if (!negativeNumbers.isEmpty()) {
            throw new UnsupportedOperationException("Negatives not allowed: " + negativeNumbers);
        }
    }

    private String getContent(String numbers) {
        int endFirstLineIndex = numbers.indexOf("\n");
        return endFirstLineIndex == -1 
                ? numbers
                : numbers.substring(endFirstLineIndex + 1);
    }

    private String getDelimiter(String numbers) {
        int endFirstLineIndex = numbers.indexOf("\n");
        if (endFirstLineIndex == -1) {
            return DEFAULT_DELIMITER;
        }
        String delimiterPart = numbers.substring(0, endFirstLineIndex);
        return delimiterPart.replace(DELIMITER_SECTION, "");
    }

    private String[] splitBySeparator(String numbers, String delimiter) {
        String numberWithoutNewline = numbers.replaceAll("\\r?\\n", delimiter);
        if (numberWithoutNewline.contains(",,")) {
            throw new IllegalArgumentException("Two consecutive separators are invalid: " + numbers);
        }
        
        return numberWithoutNewline.split(delimiter);
    }

    private boolean isContainInvalidNumber(String[] numbers) {
        return Stream.of(numbers)
                .map(StringUtils::trim)
                .anyMatch(number -> !NumberUtils.isCreatable(number));
    }
}
