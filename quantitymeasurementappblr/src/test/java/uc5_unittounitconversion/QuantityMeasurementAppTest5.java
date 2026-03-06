package uc5_unittounitconversion;

import com.bridgelabz.wipro.uc5_unittounitconversion.Length;
import com.bridgelabz.wipro.uc5_unittounitconversion.QuantityMeasurementApp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuantityMeasurementAppTest5 {
   @Test
    public void testConversion_FeetToInches() {
       Length length = QuantityMeasurementApp.demonstrateLengthConversion(new Length(1.0, Length.LengthUnit.FEET), Length.LengthUnit.INCHES);
       assertTrue(length.equals(new Length(12.0, Length.LengthUnit.INCHES)));
    }
    @Test
    public void testConversion_InchesToFeet() {
       Length length = QuantityMeasurementApp.demonstrateLengthConversion(new Length(24.0, Length.LengthUnit.INCHES), Length.LengthUnit.FEET);
       assertTrue(length.equals(new Length(2.0, Length.LengthUnit.FEET)));
    }
    @Test
    public void testConversion_YardsToInches() {
       Length length = QuantityMeasurementApp.demonstrateLengthConversion(new Length(1.0, Length.LengthUnit.YARDS), Length.LengthUnit.INCHES);
       assertTrue(length.equals(new Length(36.0, Length.LengthUnit.INCHES)));
    }
    @Test
    public void testConversion_InchesToYards() {
       Length length = QuantityMeasurementApp.demonstrateLengthConversion(new Length(72.0, Length.LengthUnit.INCHES), Length.LengthUnit.YARDS);
       assertTrue(length.equals(new Length(2.0, Length.LengthUnit.YARDS)));
    }
    @Test
    public void testConversion_CentimetersToInches() {
       Length length = QuantityMeasurementApp.demonstrateLengthConversion(new Length(2.54, Length.LengthUnit.CENTIMETERS), Length.LengthUnit.INCHES);
       assertTrue(length.equals(new Length(1.0, Length.LengthUnit.INCHES)));
    }
    @Test
    public void testConversion_FeetToYards() {
       Length length = QuantityMeasurementApp.demonstrateLengthConversion(new Length(6.0, Length.LengthUnit.FEET), Length.LengthUnit.YARDS);
       assertTrue(length.equals(new Length(2.0, Length.LengthUnit.YARDS)));
    }
    @Test
    public void testConversion_RoundTrip_PreservesValue() {
       Length originalLength = new Length(3.0, Length.LengthUnit.FEET);
       Length convertedLength = QuantityMeasurementApp.demonstrateLengthConversion(
               QuantityMeasurementApp.demonstrateLengthConversion(originalLength, Length.LengthUnit.INCHES),
               Length.LengthUnit.FEET);
       assertTrue(originalLength.equals(convertedLength));
    }
    @Test
    public void testConversion_ZeroValue() {
       Length length = QuantityMeasurementApp.demonstrateLengthConversion(new Length(0.0, Length.LengthUnit.FEET), Length.LengthUnit.INCHES);
       assertTrue(length.equals(new Length(0.0, Length.LengthUnit.INCHES)));
    }
    @Test
    public void testConversion_NegativeValue() {
       Length length = QuantityMeasurementApp.demonstrateLengthConversion(new Length(-1.0, Length.LengthUnit.FEET), Length.LengthUnit.INCHES);
       assertTrue(length.equals(new Length(-12.0, Length.LengthUnit.INCHES)));
    }
    @Test
    public void testConversion_InValidUnit_Throws(){
       try {
           QuantityMeasurementApp.demonstrateLengthConversion(new Length(1.0, Length.LengthUnit.FEET), null);
       } catch (IllegalArgumentException e) {
           assertTrue(true);
           return;
       }
       assertTrue(false);
    }
    @Test
    public void testConversion_NaNOrInfinite_Throws(){
       try {
           QuantityMeasurementApp.demonstrateLengthConversion(new Length(Double.NaN, Length.LengthUnit.FEET), Length.LengthUnit.INCHES);
       } catch (IllegalArgumentException e) {
           assertTrue(true);
           return;
       }
       assertFalse(false);
    }
    @Test
    public void testConversion_PrecisionTolerance() {
       Length length = QuantityMeasurementApp.demonstrateLengthConversion(new Length(1.00001, Length.LengthUnit.FEET), Length.LengthUnit.INCHES);
       assertTrue(length.equals(new Length(12.00012, Length.LengthUnit.INCHES)));
    }
}
