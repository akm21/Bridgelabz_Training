package uc7_additionwithtargetunitspecification;

import com.bridgelabz.wipro.uc7_additionwithtargetunitspecification.Length;
import com.bridgelabz.wipro.uc7_additionwithtargetunitspecification.QuantityMeasurementApp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuantityMeasurementAppTest7 {

    private static final double EPSILON = 0.001;

    @Test
    public void testAddition_ExplicitTargetUnit_Feet() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.FEET), new Length(12.0, Length.LengthUnit.INCHES), Length.LengthUnit.FEET);
        assertTrue(length.equals(new Length(2.0, Length.LengthUnit.FEET)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Inches() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.FEET), new Length(12.0, Length.LengthUnit.INCHES), Length.LengthUnit.INCHES);
        assertTrue(length.equals(new Length(24.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Yards() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.FEET), new Length(12.0, Length.LengthUnit.INCHES), Length.LengthUnit.YARDS);
        assertTrue(length.equals(new Length(2.0 / 3.0, Length.LengthUnit.YARDS)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Centimeters() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.INCHES), new Length(1.0, Length.LengthUnit.INCHES), Length.LengthUnit.CENTIMETERS);
        assertTrue(length.equals(new Length(5.08, Length.LengthUnit.CENTIMETERS)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(2.0, Length.LengthUnit.YARDS), new Length(3.0, Length.LengthUnit.FEET), Length.LengthUnit.YARDS);
        assertTrue(length.equals(new Length(3.0, Length.LengthUnit.YARDS)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(2.0, Length.LengthUnit.YARDS), new Length(3.0, Length.LengthUnit.FEET), Length.LengthUnit.FEET);
        assertTrue(length.equals(new Length(9.0, Length.LengthUnit.FEET)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Commutativity() {
        Length length1 = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.FEET), new Length(12.0, Length.LengthUnit.INCHES), Length.LengthUnit.YARDS);
        Length length2 = QuantityMeasurementApp.demonstrateLengthAddition(new Length(12.0, Length.LengthUnit.INCHES), new Length(1.0, Length.LengthUnit.FEET), Length.LengthUnit.YARDS);
        assertTrue(length1.equals(length2));
        assertTrue(length2.equals(length1));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_WithZero() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(5.0, Length.LengthUnit.FEET), new Length(0.0, Length.LengthUnit.INCHES), Length.LengthUnit.YARDS);
        assertTrue(length.equals(new Length(5.0 / 3.0, Length.LengthUnit.YARDS)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NegativeValues() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(5.0, Length.LengthUnit.FEET), new Length(-2.0, Length.LengthUnit.FEET), Length.LengthUnit.INCHES);
        assertTrue(length.equals(new Length(36.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NullTargetUnit() {
        try {
            Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.FEET), new Length(12.0, Length.LengthUnit.INCHES), null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(1000.0, Length.LengthUnit.FEET), new Length(500.0, Length.LengthUnit.FEET), Length.LengthUnit.INCHES);
        assertTrue(length.equals(new Length(18000.0, Length.LengthUnit.INCHES)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
        Length length = QuantityMeasurementApp.demonstrateLengthAddition(new Length(12.0, Length.LengthUnit.INCHES), new Length(12.0, Length.LengthUnit.INCHES), Length.LengthUnit.YARDS);
        assertTrue(length.equals(new Length(2.0 / 3.0, Length.LengthUnit.YARDS)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_AllUnitCombinations() {
        Length oneFoot = new Length(1.0, Length.LengthUnit.FEET);
        Length tweleveInches = new Length(12.0, Length.LengthUnit.INCHES);
        Length oneYard = new Length(1.0, Length.LengthUnit.YARDS);
        Length thirtySixInches = new Length(36.0, Length.LengthUnit.INCHES);
        Length oneInch = new Length(1.0, Length.LengthUnit.INCHES);
        Length twopointfivefourCm = new Length(2.54, Length.LengthUnit.CENTIMETERS);

        assertEquals(new Length(2.0, Length.LengthUnit.FEET), QuantityMeasurementApp.demonstrateLengthAddition(oneFoot, tweleveInches, Length.LengthUnit.FEET));
        assertEquals(new Length(24.0, Length.LengthUnit.INCHES), QuantityMeasurementApp.demonstrateLengthAddition(oneFoot, tweleveInches, Length.LengthUnit.INCHES));
        assertEquals(new Length(2.0 / 3.0, Length.LengthUnit.YARDS), QuantityMeasurementApp.demonstrateLengthAddition(oneFoot, tweleveInches, Length.LengthUnit.YARDS));
        assertEquals(
                new Length(4.0/3.0, Length.LengthUnit.YARDS),
                QuantityMeasurementApp.demonstrateLengthAddition(oneYard, oneFoot, Length.LengthUnit.YARDS));

        assertEquals(
                new Length(6.0, Length.LengthUnit.FEET),
                QuantityMeasurementApp.demonstrateLengthAddition(thirtySixInches, oneYard, Length.LengthUnit.FEET));


        assertEquals(
                new Length(5.08, Length.LengthUnit.CENTIMETERS),
                QuantityMeasurementApp.demonstrateLengthAddition(twopointfivefourCm, oneInch, Length.LengthUnit.CENTIMETERS));

        assertEquals(
                new Length(5.0 / 3.0, Length.LengthUnit.YARDS),
                QuantityMeasurementApp.demonstrateLengthAddition(
                        new Length(5.0, Length.LengthUnit.FEET),
                        new Length(0.0, Length.LengthUnit.INCHES),
                        Length.LengthUnit.YARDS
                )
        );

        assertEquals(
                new Length(36.0, Length.LengthUnit.INCHES),
                QuantityMeasurementApp.demonstrateLengthAddition(
                        new Length(5.0, Length.LengthUnit.FEET),
                        new Length(-2.0, Length.LengthUnit.FEET),
                        Length.LengthUnit.INCHES
                )
        );
    }

    @Test
    public void testAddition_ExplicitTargetUnit_PrecisionTolerance() {
        Length length1 = new Length(1.0, Length.LengthUnit.FEET);
        Length length2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length resultInYards =
                QuantityMeasurementApp.demonstrateLengthAddition(length1, length2, Length.LengthUnit.YARDS);

        assertEquals(
                0.667,
                resultInYards.getValue(),
                EPSILON,
                "Precision must be maintained during FEET + INCHES → YARDS conversion"
        );

        Length cmResult =
                QuantityMeasurementApp.demonstrateLengthAddition(
                        new Length(2.54, Length.LengthUnit.CENTIMETERS),
                        new Length(1.0, Length.LengthUnit.INCHES),
                        Length.LengthUnit.CENTIMETERS
                );

        assertEquals(
                5.08,
                cmResult.getValue(),
                EPSILON,
                "Precision must be maintained during CM + INCH → CM conversion"
        );

        Length inchesResult =
                QuantityMeasurementApp.demonstrateLengthAddition(
                        new Length(5.0, Length.LengthUnit.FEET),
                        new Length(-2.0, Length.LengthUnit.FEET),
                        Length.LengthUnit.INCHES
                );

        assertEquals(
                36.0,
                inchesResult.getValue(),
                EPSILON,
                "Precision must be maintained for negative-value arithmetic"
        );
        
        
    }
}