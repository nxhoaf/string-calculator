package com.test.calculator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nxhoaf
 */
public class StringCalculator {
    private static final String DEFAULT_DELIMITER = ",";
    private static final char NEW_LINE = '\n';
    private static final String DELIMITER_SECTION = "//";
    private static final char START_DELIMITER = '[';
    private static final char END_DELIMITER = ']';
    private static final int MAX_SUPPORTED_NUMBER = 1000;
    
    public int add(String input) {
        if (StringUtils.isEmpty(input)) {
            return 0;
        }
        
        List<String> delimiters = Collections.singletonList(DEFAULT_DELIMITER);
        String content = input;
        if (input.contains(DELIMITER_SECTION)) {
            delimiters = getDelimiter(input);
            content = getContent(input);
        }
        
        ensureInputCorrect(content, delimiters);
        List<String> numbers = splitBySeparator(content, delimiters);

        return numbers.stream()
                .map(Integer::valueOf)
                .filter(number -> number < MAX_SUPPORTED_NUMBER)
                .reduce(0, (n1, n2) -> n1 + n2);
    }

    private String getContent(String numbers) {
        int endFirstLineIndex = numbers.indexOf(NEW_LINE);
        return numbers.substring(endFirstLineIndex + 1);
    }

    private List<String> getDelimiter(String numbers) {
        int endFirstLineIndex = numbers.indexOf(NEW_LINE);
        String delimiterPart = numbers.substring(0, endFirstLineIndex)
                .replace(DELIMITER_SECTION, "");
        if (!delimiterPart.contains(String.valueOf(START_DELIMITER))) {
            return Collections.singletonList(delimiterPart);
        }

        int beginDelim = 0;
        int endDelim = 0;
        List<String> delimiters = new ArrayList<>();
        for (int i = 0; i < delimiterPart.length(); i++) {
            if (delimiterPart.charAt(i) == START_DELIMITER) {
                beginDelim = i + 1;
            }

            if (delimiterPart.charAt(i) == END_DELIMITER) {
                endDelim = i;
                delimiters.add(delimiterPart.substring(beginDelim, endDelim));
            }
        }

        return delimiters;
    }

    private void ensureInputCorrect(String content, List<String> delimiters) {
        List<String> numbers = splitBySeparator(content, delimiters);
        
        if (isContainInvalidNumber(numbers)) {
            throw new IllegalArgumentException("Not a valid number: " + content);
        }

        List<Integer> negativeNumbers = isContainNegativeNumbers(numbers);
        if (!negativeNumbers.isEmpty()) {
            throw new UnsupportedOperationException("Negatives not allowed: " + negativeNumbers);
        }
    }

    private List<Integer> isContainNegativeNumbers(List<String> numberStr) {
        return numberStr.stream()
                    .map(Integer::valueOf)
                    .filter(number -> number < 0)
                    .collect(Collectors.toList());
    }

    private List<String> splitBySeparator(String numbers, List<String> delimiters) {
        for (String delimiter : delimiters) {
            numbers = numbers.replace(delimiter, DEFAULT_DELIMITER);
        }
        
        String numberWithoutNewline = numbers.replaceAll("\\r?\\n", DEFAULT_DELIMITER);
        if (numberWithoutNewline.contains(",,")) {
            throw new IllegalArgumentException("Two consecutive separators are invalid: " + numbers);
        }
        return Arrays.stream(numberWithoutNewline.split(DEFAULT_DELIMITER))
                .map(StringUtils::trim)
                .collect(Collectors.toList());
    }

    private boolean isContainInvalidNumber(List<String> numbers) {
        return numbers.stream().anyMatch(number -> !NumberUtils.isCreatable(number));
    }
}
