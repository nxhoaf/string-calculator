package com.test.calculator;

import org.apache.commons.lang3.builder.ToStringExclude;
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
    public void twoConsectutiveSeparatorsAreInvalid1() {
        String numbers = "1,\n";
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Two consecutive separators are invalid: " + numbers);
        
        calculator.add(numbers);
    }

    @Test
    public void twoConsectutiveSeparatorsAreInvalid2() {
        String numbers = "1,\n,2,3";
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Two consecutive separators are invalid: " + numbers);

        calculator.add(numbers);
    }
    
    @Test
    public void addShouldSupportDifferentDelimiter() {
        assertEquals(3, calculator.add("//;\n1;2"));
    }
    
    @Test
    public void negativeNumbersAreNotSupported() {
        String number = "1,-2,3,-4,5";
        
        expectedException.expect(UnsupportedOperationException.class);
        expectedException.expectMessage("Negatives not allowed: [-2, -4]");

        calculator.add(number);
    }
}
