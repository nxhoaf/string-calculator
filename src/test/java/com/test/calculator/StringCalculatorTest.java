package com.test.calculator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * @author nxhoaf
 */
public class StringCalculatorTest {
    @Rule 
    public ExpectedException expectedException = ExpectedException.none();

    private StringCalculator calculator = new StringCalculator();

    @Test 
    public void addWithEmptyStringShouldReturnZero() {
        int result = calculator.add("");
        assertEquals(0, result);
    }

    @Test 
    public void addWithOneNumberShouldReturnItself() {
        int result = calculator.add("1");
        assertEquals(1, result);
    }
    
    @Test 
    public void addWithInvalidNumberShouldThrowAnException() {
        String number = "invalid";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Not a valid number: " + number);
        calculator.add(number);
    }
    
    @Test
    public void addTwoNumbersWithCommaSeparatorShouldReturnCorrectValue() {
        int result = calculator.add("1,2");
        assertEquals(3, result);
    }
    
    @Test
    public void addFiveNumbersShouldWork() {
        int result = calculator.add("1,2,3,4,5");
        assertEquals(15, result);
    }
}
