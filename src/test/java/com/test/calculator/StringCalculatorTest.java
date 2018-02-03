package com.test.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author nxhoaf
 */
public class StringCalculatorTest {
    private StringCalculator calculator = new StringCalculator();
    
    
    @Test
    public void emptyStringShouldReturnZero() {
        int result = calculator.add("");
        assertEquals(0, result);
    }
}
