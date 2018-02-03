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
        assertEquals(0, calculator.add(""));
    }

    @Test 
    public void addWithOneNumberShouldReturnItself() {
        assertEquals(1, calculator.add("1"));
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
        assertEquals(15, calculator.add("1,2,3,4,5"));
    }
    
    @Test
    public void newLineCharacterShouldBeConsideredAsSeparator() {
        assertEquals(6, calculator.add("1\n2,3"));
    }
    
    @Test
    public void twoConsectutiveSeparatorsAreInvalid() {
        String numbers = "1,\n";
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Two consecutive Separator is invalid: " + numbers);
        
        calculator.add(numbers);
    }
}
