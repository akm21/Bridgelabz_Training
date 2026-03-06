package uc6_additionoftwolengthunits;

import com.bridgelabz.wipro.uc6_additionoftwolengthunits.Length;
import com.bridgelabz.wipro.uc6_additionoftwolengthunits.QuantityMeasurementApp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuantityMeasurementAppTest6 {
    @Test
    public void testAddition_SameUnit_FeetPlusFeet() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.FEET), new Length(2.0, Length.LengthUnit.FEET));
        assertTrue(length.equals(new Length(3.0, Length.LengthUnit.FEET)));
    }

    @Test
    public void testAddition_SameUnit_InchPlusInch() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(6.0, Length.LengthUnit.INCHES), new Length(6.0, Length.LengthUnit.INCHES));
        assertTrue(length.equals(new Length(12.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void testAddition_CrossUnit_FeetPlusInches() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.FEET), new Length(12.0, Length.LengthUnit.INCHES));
        assertTrue(length.equals(new Length(2.0, Length.LengthUnit.FEET)));
    }

    @Test
    public void testAddition_CrossUnit_InchPlusFeet() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(12.0, Length.LengthUnit.INCHES), new Length(1.0, Length.LengthUnit.FEET));
        assertTrue(length.equals(new Length(24.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void testAddition_CrossUnit_YardPlusFeet() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.YARDS), new Length(3.0, Length.LengthUnit.FEET));
        assertTrue(length.equals(new Length(2.0, Length.LengthUnit.YARDS)));
    }

    @Test
    public void testAddition_CrossUnit_CentimeterPlusInch() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(2.54, Length.LengthUnit.CENTIMETERS), new Length(1.0, Length.LengthUnit.INCHES));
        assertTrue(length.equals(new Length(5.08, Length.LengthUnit.CENTIMETERS)));
    }

    @Test
    public void testAddition_Commutativity() {
        Length length1 = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.FEET), new Length(12.0, Length.LengthUnit.INCHES));
        Length length2 = QuantityMeasurementApp.demonstrateLengthAddition(new Length(12.0, Length.LengthUnit.INCHES), new Length(1.0, Length.LengthUnit.FEET));
        assertTrue(length1.equals(length2));
        assertTrue(length2.equals(length1));
    }

    @Test
    public void testAddition_WithZero() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(5.0, Length.LengthUnit.FEET), new Length(0.0, Length.LengthUnit.INCHES));
        assertTrue(length.equals(new Length(5.0, Length.LengthUnit.FEET)));
    }

    @Test
    public void testAddition_NegativeValues() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(5.0, Length.LengthUnit.FEET), new Length(-2.0, Length.LengthUnit.FEET));
        assertTrue(length.equals(new Length(3.0, Length.LengthUnit.FEET)));
    }

    @Test
    public void testAddition_NullSecondOperand() {
        try {
            Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.FEET), null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testAddition_LargeValues() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1e6, Length.LengthUnit.FEET), new Length(1e6, Length.LengthUnit.FEET));
        assertTrue(length.equals(new Length(2e6, Length.LengthUnit.FEET)));
    }

    @Test
    public void testAddition_SmallValues() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(0.001, Length.LengthUnit.FEET), new Length(0.002, Length.LengthUnit.FEET));
        assertTrue(length.equals(new Length(0.003, Length.LengthUnit.FEET)));
    }
}