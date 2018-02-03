package com.test.calculator;

import com.sun.media.sound.InvalidFormatException;
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
        expectedException.expect(InvalidFormatException.class);
        expectedException.expectMessage("Not a valid numberaaa");
        calculator.add("invalid");
    }
}
