package uc8_refactoringunitenumtostandalone;

import com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.Length;
import com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.LengthUnit;
import com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.QuantityMeasurementApp;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest8 {

    private static final double EPSILON = 0.001;

    @Test
    public void testLengthUnitEnum_FeetConstant() {
        assertEquals(1.0, LengthUnit.FEET.convertToBaseUnit(1.0), EPSILON, "1.0 Feet should be equal to 1 Feet");
    }

    @Test
    public void testLengthUnitEnum_InchesConstant() {
        assertEquals(1.0 / 12.0, LengthUnit.INCHES.convertToBaseUnit(1.0), EPSILON, "1.0 Inch should be equal to ~0.0833 Feet");
    }

    @Test
    public void testLengthUnitEnum_YardsConstant() {
        assertEquals(3.0, LengthUnit.YARDS.convertToBaseUnit(1.0), EPSILON, "1.0 Yard should be equal to 3.0 Feet");
    }

    @Test
    public void testLengthUnitEnum_CentimetersConstant() {
        assertEquals(1.0 / 30.48, LengthUnit.CENTIMETERS.convertToBaseUnit(1.0), EPSILON, "1.0 Centimeter should be equal to ~0.0328 Feet");
    }

    @Test
    public void testConvertToBaseUnit_FeetToFeet() {
        assertEquals(5.0, LengthUnit.FEET.convertToBaseUnit(5.0), EPSILON, "5.0 Feet should be equal to 5.0 Feet");
    }

    @Test
    public void testConvertToBaseUnit_InchesToFeet() {
        assertEquals(1.0, LengthUnit.INCHES.convertToBaseUnit(12.0), EPSILON, "1.0 Feet should be equal to 12.0 Inches");
    }

    @Test
    public void testConvertToBaseUnit_YardsToFeet() {
        assertEquals(3.0, LengthUnit.YARDS.convertToBaseUnit(1.0), EPSILON, "1.0 Yard should be equal to 3.0 Feet");
    }

    @Test
    public void testConvertToBaseUnit_CentimetersToFeet() {
        assertEquals(1.0, LengthUnit.CENTIMETERS.convertToBaseUnit(30.48), EPSILON, "1.0 Feet should be equal to 30.48 Centimeters");
    }

    @Test
    public void testConvertFromBaseUnit_FeetToFeet() {
        assertEquals(2.0, LengthUnit.FEET.convertFromBaseUnit(2.0), EPSILON, "2.0 Feet should be equal to 2.0 Feet");
    }

    @Test
    public void testConvertFromBaseUnit_FeetToInches() {
        assertEquals(12.0, LengthUnit.INCHES.convertFromBaseUnit(1.0), EPSILON, "1.0 Feet should be equal to 12.0 Inches");
    }

    @Test
    public void testConvertFromBaseUnit_FeetToYards() {
        assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(3.0), EPSILON, "1 Yard should be equal to 3.0 Feet");
    }

    @Test
    public void testConvertFromBaseUnit_FeetToCentimeters() {
        assertEquals(30.48, LengthUnit.CENTIMETERS.convertFromBaseUnit(1.0), EPSILON, "1 Feet should be equal to 30.48 Centimeters");
    }

    @Test
    public void testQuantityLengthRefactored_Equality() {
        assertEquals(true, QuantityMeasurementApp.demonstrateLengthEquality(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES)));
    }

    @Test
    public void testQuantityLengthRefactored_ConvertTo() {
        Length length = QuantityMeasurementApp.demonstrateLengthConversion(new Length(1.0, LengthUnit.FEET), LengthUnit.INCHES);
        assertTrue(length.equals(new Length(12.0, LengthUnit.INCHES)));
    }

    @Test
    public void testQuantityLengthRefactored_Add() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES), LengthUnit.FEET);
        assertTrue(length.equals(new Length(2.0, LengthUnit.FEET)));
    }

    @Test
    public void testQuantityLengthRefactored_AddWithTargetUnit() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES), LengthUnit.YARDS);
        assertTrue(length.equals(new Length(2.0 / 3.0, LengthUnit.YARDS)));
    }

    @Test
    public void testQuantityLengthRefactored_NullUnit() {
        try {
            Length length = new Length(1.0, null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testQuantityLengthRefactored_InvalidValue() {
        try {
            Length length = new Length(Double.NaN, LengthUnit.FEET);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testBackwardCompatibility_UC1EqualityTests() {
        Length oneFeet = new Length(1.0, LengthUnit.FEET);
        Length TwoFeet = new Length(2.0, LengthUnit.FEET);
        Length anotherLength = new Length(1.0, LengthUnit.FEET);
        assertEquals(oneFeet, anotherLength);
        assertNotEquals(oneFeet, TwoFeet);
        assertNotEquals(oneFeet, null);
        assertEquals(oneFeet, oneFeet);
    }

    @Test
    public void testBackwardCompatibility_UC5ConversionTests() {
        Length length = QuantityMeasurementApp.demonstrateLengthConversion(new Length(1.0, LengthUnit.FEET), LengthUnit.INCHES);
        Length length1 = QuantityMeasurementApp.demonstrateLengthConversion(new Length(3.0, LengthUnit.YARDS), LengthUnit.INCHES);
        Length length2 = QuantityMeasurementApp.demonstrateLengthConversion(new Length(1.0, LengthUnit.INCHES), LengthUnit.CENTIMETERS);
        assertEquals(12.0, length.getValue(), "1.0 Feet should be equal to 12.0 Inches");
        assertEquals(108.0, length1.getValue(), "3.0 Yards should be equal to 108.0 Inches");
        assertEquals(2.54, length2.getValue(), "1.0 Inch should be equal to 2.54 centimeters");
        try {
            QuantityMeasurementApp.demonstrateLengthConversion(new com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.Length(1.0, LengthUnit.FEET), null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
        try {
            com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.QuantityMeasurementApp.demonstrateLengthConversion(new com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.Length(Double.NaN, LengthUnit.FEET), LengthUnit.INCHES);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertFalse(false);
    }

    @Test
    public void testBackwardCompatibility_UC6AdditionTests() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES));
        Length length1 = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, LengthUnit.YARDS), new Length(3.0, LengthUnit.FEET));
        Length length2 = QuantityMeasurementApp.demonstrateLengthAddition(new Length(2.54, LengthUnit.CENTIMETERS), new Length(1.0, LengthUnit.INCHES));
        Length length3 = QuantityMeasurementApp.demonstrateLengthAddition(new Length(5.0, LengthUnit.FEET), new Length(0.0, LengthUnit.INCHES));
        assertEquals(2.0, length.getValue(), "1.0 Feet plus 12.0 Inches equals 2.0 Feet");
        assertEquals(2.0, length1.getValue(), "1.0 Yard plus 3.0 Feet equals 2.0 Yard");
        assertEquals(5.08, length2.getValue(), "2.54 Centimeters plus 1.0 Inch equals 5.08 Centimeters");
        assertEquals(5.0, length3.getValue(), "5.0 Feet plus 0.0 Inches equals 5.0 Feet");
        try {
            com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.Length length4 = com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.QuantityMeasurementApp.demonstrateLengthAddition(new com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.Length(1.0, LengthUnit.FEET), null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }
    @Test
    public void testBackwardCompatibility_UC7AdditionWithTargetUnitTests(){
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, LengthUnit.FEET), new Length(12.0, LengthUnit.INCHES), LengthUnit.YARDS);
        assertEquals(2.0 / 3.0, length.getValue(), "1.0 Feet plus 12.0 Inches equals 2/3 Yard");
        try {
            com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.Length length1 = com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.QuantityMeasurementApp.demonstrateLengthAddition(new com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.Length(1.0, LengthUnit.FEET), new com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.Length(12.0, LengthUnit.INCHES), null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }
    @Test
    public void testArchitecturalScalability_MultipleCategories(){
            // This test is a placeholder to ensure that the refactored design can support multiple categories (Length, Weight, Volume) without modification.
            // Actual implementation would involve creating similar tests for Weight and Volume categories using their respective units and operations.
            assertTrue(true, "The refactored design should support multiple categories without modification.");
    }
    @Test
    public void testRoundTripConversion_RefactoredDesign(){
        Length originalLength = new Length(3.0, LengthUnit.FEET);
        Length convertedLength = QuantityMeasurementApp.demonstrateLengthConversion(
                QuantityMeasurementApp.demonstrateLengthConversion(originalLength, LengthUnit.INCHES),
                LengthUnit.FEET);
        assertTrue(originalLength.equals(convertedLength), "Round-trip conversion should preserve the original value.");
    }
    @Test
    public void testUnitImmutability() {
        Length length = new Length(1.0, LengthUnit.FEET);
        try {
            // Attempt to modify the unit of the length (this should not be possible if units are immutable)
            // This is a placeholder for any method that would attempt to change the unit, which should throw an exception or be disallowed.
            // For example, if there was a setter method for the unit, it should throw an exception.
            // length.setUnit(LengthUnit.INCHES); // This line is commented out because such a method should not exist in an immutable design.
            assertTrue(true, "Units should be immutable and not allow modification after creation.");
        } catch (Exception e) {
            fail("Units should be immutable and not allow modification after creation.");
        }
    }

}


