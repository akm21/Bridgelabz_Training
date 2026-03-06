package uc12_subtractionanddivisionoperationsonquantitymeasurements;

import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest12 {

    @Test
    public void testSubtraction_SameUnit_FeetMinusFeet() {
        Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(5.0, LengthUnit.FEET));
        assertTrue(length.equals(new Quantity<>(5.0, LengthUnit.FEET)));
    }

    @Test
    public void testSubtraction_SameUnit_LitreMinusLitre() {
        Quantity volume = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, VolumeUnit.LITRE), new Quantity<>(3.0, VolumeUnit.LITRE));
        assertTrue(volume.equals(new Quantity<>(7.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testSubtraction_CrossUnit_FeetMinusInches() {
        Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(6.0, LengthUnit.INCHES));
        assertTrue(length.equals(new Quantity<>(9.5, LengthUnit.FEET)));
    }

    @Test
    public void testSubtraction_CrossUnit_InchesMinusFeet() {
        Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(120.0, LengthUnit.INCHES), new Quantity<>(5.0, LengthUnit.FEET));
        assertTrue(length.equals(new Quantity<>(60.0, LengthUnit.INCHES)));
    }

    @Test
    public void testSubtraction_ExplicitTargetUnit_Feet() {
        Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.FEET);
        assertTrue(length.equals(new Quantity<>(9.5, LengthUnit.FEET)));
    }

    @Test
    public void testSubtraction_ExplicitTargetUnit_Inches() {
        Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.INCHES);
        assertTrue(length.equals(new Quantity<>(114.0, LengthUnit.INCHES)));
    }

    @Test
    public void testSubtraction_ExplicitTargetUnit_Millilitre() {
        Quantity volume = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
        assertTrue(volume.equals(new Quantity<>(3000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testSubtraction_ResultingInNegative() {
        Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(5.0, LengthUnit.FEET), new Quantity<>(10.0, LengthUnit.FEET));
        assertTrue(length.equals(new Quantity<>(-5.0, LengthUnit.FEET)));
    }

    @Test
    public void testSubtraction_ResultingInZero() {
        Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(120.0, LengthUnit.INCHES));
        assertTrue(length.equals(new Quantity<>(0.0, LengthUnit.FEET)));
    }

    @Test
    public void testSubtraction_WithZeroOperand() {
        Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(5.0, LengthUnit.FEET), new Quantity<>(0.0, LengthUnit.INCHES));
        assertTrue(length.equals(new Quantity<>(5.0, LengthUnit.FEET)));
    }

    @Test
    public void testSubtraction_WithNegativeValues() {
        Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(5.0, LengthUnit.FEET), new Quantity<>(-2.0, LengthUnit.FEET));
        assertTrue(length.equals(new Quantity<>(7.0, LengthUnit.FEET)));
    }

    @Test
    public void testSubtraction_NonCommutative() {
        Quantity length1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity length2 = new Quantity<>(5.0, LengthUnit.FEET);
        assertEquals(5.0, length1.subtract(length2).getValue());
        assertEquals(-5.0, length2.subtract(length1).getValue());
    }

    @Test
    public void testSubtraction_WithLargeValues() {
        Quantity weight = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(1e6, WeigthUnit.KILOGRAM), new Quantity<>(5e5, WeigthUnit.KILOGRAM));
        assertTrue(weight.equals(new Quantity<>(5e5, WeigthUnit.KILOGRAM)));
    }

    @Test
    public void testSubtraction_WithSmallValues() {
        Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(0.001, LengthUnit.FEET), new Quantity<>(0.0005, LengthUnit.FEET));
        assertTrue(length.equals(new Quantity<>(0.0005, LengthUnit.FEET)));
    }

    @Test
    public void testSubtraction_NullOperand() {
        try {
            Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testSubtraction_NullTargetUnit() {
        try {
            Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(5.0, LengthUnit.FEET), null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testSubtraction_CrossCategory() {
        try {
            Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(5.0, WeigthUnit.KILOGRAM));
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testSubtraction_AllMeasurementCategories() {
        Quantity length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(6.0, LengthUnit.INCHES));
        assertEquals(9.5, length.getValue());
        Quantity weight = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, WeigthUnit.KILOGRAM), new Quantity<>(5000.0, WeigthUnit.GRAM));
        assertEquals(5.0, weight.getValue());
        Quantity volume = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(500.0, VolumeUnit.MILLILITRE));
        assertEquals(4.5, volume.getValue());
    }

    @Test
    public void testSubtraction_ChainedOperations() {
        Quantity length1 = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(2.0, LengthUnit.FEET));
        Quantity length2 = QuantityMeasurementApp.demonstrateSubtraction(length1, new Quantity<>(1.0, LengthUnit.FEET));
        assertEquals(7.0, length2.getValue());
    }

    @Test
    public void testDivision_SameUnit_FeetDividedByFeet() {
        Quantity length = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(5.0, length.getValue());
    }

    @Test
    public void testDivision_SameUnit_LitreDividedByLitre() {
        Quantity volume = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(10.0, VolumeUnit.LITRE), new Quantity<>(5.0, VolumeUnit.LITRE));
        assertEquals(2.0, volume.getValue());
    }

    @Test
    public void testDivision_CrossUnit_FeetDividedByInches() {
        Quantity quantity = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(24.0, LengthUnit.INCHES), new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(1.0, quantity.getValue());
    }

    @Test
    public void testDivision_CrossUnit_KilogramDividedByGram() {
        Quantity weight = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(2.0, WeigthUnit.KILOGRAM), new Quantity<>(2000.0, WeigthUnit.GRAM));
        assertEquals(1.0, weight.getValue());
    }

    @Test
    public void testDivision_RatioGreaterThanOne() {
        Quantity length = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(5.0, length.getValue());
        assertTrue(length.getValue() > 1.0);
    }

    @Test
    public void testDivision_RatioLessThanOne() {
        Quantity length = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(5.0, LengthUnit.FEET), new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(0.5, length.getValue());
        assertTrue(length.getValue() < 1.0);
    }

    @Test
    public void testDivision_RatioEqualToOne() {
        Quantity length = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(1.0, length.getValue());
        assertTrue(length.getValue() == 1.0);
    }

    @Test
    public void testDivision_NonCommutative() {
        Quantity forward = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(5.0, LengthUnit.FEET));
        Quantity reverse = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(5.0, LengthUnit.FEET), new Quantity<>(10.0, LengthUnit.FEET));
        assertEquals(2.0, forward.getValue());
        assertEquals(0.5, reverse.getValue());
    }

    @Test
    public void testDivision_ByZero() {
        try {
            Quantity length = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(0.0, LengthUnit.FEET));
        } catch (ArithmeticException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testDivision_WithLargeRatio() {
        Quantity weight = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(1e6, WeigthUnit.KILOGRAM), new Quantity<>(1.0, WeigthUnit.KILOGRAM));
        assertEquals(1e6, weight.getValue());
    }

    @Test
    public void testDivision_WithSmallRatio() {
        Quantity weight = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(1.0, WeigthUnit.KILOGRAM), new Quantity<>(1e6, WeigthUnit.KILOGRAM));
        assertEquals(1e-6, weight.getValue());
    }

    @Test
    public void testDivision_NullOperand() {
        try {
            Quantity length = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testDivision_CrossCategory() {
        try {
            Quantity length = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(5.0, WeigthUnit.KILOGRAM));
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testDivision_AllMeasurementCategories() {
        // Length
        assertEquals(2.0,
                new Quantity<>(10.0, LengthUnit.FEET)
                        .divide(new Quantity<>(5.0, LengthUnit.FEET)).getValue());

        // Weight
        assertEquals(2.0,
                new Quantity<>(2000.0, WeigthUnit.GRAM)
                        .divide(new Quantity<>(1.0, WeigthUnit.KILOGRAM)).getValue());

        // Volume
        assertEquals(1.0,
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                        .divide(new Quantity<>(1.0, VolumeUnit.LITRE)).getValue());
    }

    @Test
    public void testDivision_Associativity() {
        //Verifying (A/B)/C ≠ A/(B/C)
        Quantity<LengthUnit> A =
                new Quantity<>(20.0, LengthUnit.FEET);

        Quantity<LengthUnit> B =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> C =
                new Quantity<>(2.0, LengthUnit.FEET);

        Quantity LHS1 = QuantityMeasurementApp.demonstrateDivision(A, B);
        Quantity LHS2 = QuantityMeasurementApp.demonstrateDivision(LHS1, C);
        // System.out.println(LHS2);

        Quantity RHS1 = QuantityMeasurementApp.demonstrateDivision(B, C);
        Quantity RHS2 = QuantityMeasurementApp.demonstrateDivision(A, RHS1);
        // System.out.println(RHS2);

        assertNotEquals(LHS2, RHS2);
    }

    @Test
    public void testSubtractionAndDivision_Integration() {
        //Verifying (A-B)/C
        Quantity<LengthUnit> A = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> B = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> C = new Quantity<>(4.0, LengthUnit.FEET);

        Quantity result1 = QuantityMeasurementApp.demonstrateSubtraction(A, B);
        Quantity result = QuantityMeasurementApp.demonstrateDivision(result1, C);

        assertEquals(2.0, result.getValue());
    }

    @Test
    public void testSubtractionAddition_Inverse() {
        //Verifying A.add(B).Subtract(B)== ~A
        Quantity<LengthUnit> A = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> B = new Quantity<>(2.0, LengthUnit.FEET);

        Quantity result1 = QuantityMeasurementApp.demonstrateAddition(A, B);
        Quantity result = QuantityMeasurementApp.demonstrateSubtraction(result1, B);

        assertEquals(10.0, result.getValue());
    }

    @Test
    public void testSubtraction_Immutability() {
        Quantity<WeigthUnit> original = new Quantity<>(5.0, WeigthUnit.KILOGRAM);
        Quantity<WeigthUnit> result = QuantityMeasurementApp.demonstrateSubtraction(original, new Quantity<>(2.0, WeigthUnit.KILOGRAM));

        assertEquals(5.0, original.getValue());
        assertEquals(3.0, result.getValue());
    }

    @Test
    public void testDivision_Immutability() {
        Quantity<VolumeUnit> original = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> result = QuantityMeasurementApp.demonstrateDivision(original, new Quantity<>(5.0, VolumeUnit.LITRE));

        assertEquals(5.0, original.getValue());
        assertEquals(1.0, result.getValue());
    }

    @Test
    public void testSubtraction_PrecisionAndRounding() {
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(4.0/3.0, LengthUnit.FEET));
        assertEquals(8.666666666666666, result.getValue());
    }

    @Test
    public void testDivision_PrecisionHandling() {
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(3.0, LengthUnit.FEET));
        assertEquals(3.3333333333333335, result.getValue(),1e-7);
    }

}
