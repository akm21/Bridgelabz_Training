package uc14_temperaturemeasurementwithselectivearithmeticsupportandimeasurablerefactoring;

import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.LengthUnit;
import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.VolumeUnit;
import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.WeigthUnit;
import com.bridgelabz.wipro.uc14_temperaturemeasurementwithselectivearithmeticsupportandimeasurablerefactoring.IMeasurable;
import com.bridgelabz.wipro.uc14_temperaturemeasurementwithselectivearithmeticsupportandimeasurablerefactoring.Quantity;
import com.bridgelabz.wipro.uc14_temperaturemeasurementwithselectivearithmeticsupportandimeasurablerefactoring.QuantityMeasurementApp;
import com.bridgelabz.wipro.uc14_temperaturemeasurementwithselectivearithmeticsupportandimeasurablerefactoring.TemperatureUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest14 {
    @Test
    public void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        Quantity<TemperatureUnit> temp1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> temp2 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        assert (temp1.equals(temp2));
    }

    @Test
    public void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
        Quantity<TemperatureUnit> temp1 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> temp2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assert (temp1.equals(temp2));
    }

    @Test
    public void testTemperatureEquality_CelsiusToFahrenheit_0CEquals32F() {
        Quantity<TemperatureUnit> temp1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> temp2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assert (temp1.equals(temp2));
    }

    @Test
    public void testTemperatureEquality_CelsiusToFahrenheit_100CEquals212F() {
        Quantity<TemperatureUnit> temp1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> temp2 = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        assert (temp1.equals(temp2));
    }

    @Test
    public void testTemperatureEquality_CelsiusToFahrenheit_Negative40CEqualsNegative40F() {
        Quantity<TemperatureUnit> temp1 = new Quantity<>(-40.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> temp2 = new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT);
        assert (temp1.equals(temp2));
    }

    @Test
    public void testTemperatureEquality_SymmetricProperty() {
        Quantity<TemperatureUnit> temp1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> temp2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assert (temp1.equals(temp2) && temp2.equals(temp1));
    }

    @Test
    public void testTemperatureEquality_ReflexiveProperty() {
        Quantity<TemperatureUnit> temp = new Quantity<>(25.0, TemperatureUnit.CELSIUS);
        assert (temp.equals(temp));
    }

    @Test
    public void testTemperatureConversion_CelsiusToFahrenheit_VariousValues() {
        Quantity<TemperatureUnit> temp1 = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(50.0, TemperatureUnit.CELSIUS), TemperatureUnit.FAHRENHEIT);
        assertEquals(122.0, temp1.getValue());

        temp1 = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(-20.0, TemperatureUnit.CELSIUS), TemperatureUnit.FAHRENHEIT);
        assertEquals(-4.0, temp1.getValue());
    }

    @Test
    public void testTemperatureConversion_FahrenheitToCelsius_VariousValues() {
        Quantity<TemperatureUnit> temp1 = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(122.0, TemperatureUnit.FAHRENHEIT), TemperatureUnit.CELSIUS);
        assertEquals(50.0, temp1.getValue());

        temp1 = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(-4.0, TemperatureUnit.FAHRENHEIT), TemperatureUnit.CELSIUS);
        assertEquals(-20.0, temp1.getValue());
    }

    @Test
    public void testTemperatureConversion_RoundTripConversion_PreservesValue() {
        Quantity<TemperatureUnit> originalTemp = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> convertedTemp = QuantityMeasurementApp.demonstrateConversion(
                QuantityMeasurementApp.demonstrateConversion(originalTemp, TemperatureUnit.FAHRENHEIT),
                TemperatureUnit.CELSIUS);
        assertEquals(originalTemp.getValue(), convertedTemp.getValue(), 0.0001);
    }

    @Test
    public void testTemperatureConversion_SameUnit() {
        Quantity<TemperatureUnit> temp1 = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(25.0, TemperatureUnit.CELSIUS), TemperatureUnit.CELSIUS);
        assertEquals(25.0, temp1.getValue());
    }

    @Test
    public void testTemperatureConversion_ZeroValue() {
        Quantity<TemperatureUnit> temp1 = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(0.0, TemperatureUnit.CELSIUS), TemperatureUnit.FAHRENHEIT);
        assertEquals(32.0, temp1.getValue());
    }

    @Test
    public void testTemperatureConversion_NegativeValues() {
        Quantity<TemperatureUnit> temp1 = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(-40.0, TemperatureUnit.CELSIUS), TemperatureUnit.FAHRENHEIT);
        assertEquals(-40.0, temp1.getValue());
    }

    @Test
    public void testTemperatureConversion_LargeValues() {
        Quantity<TemperatureUnit> temp1 = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1000.0, TemperatureUnit.CELSIUS), TemperatureUnit.FAHRENHEIT);
        assertEquals(1832.0, temp1.getValue());
    }

    @Test
    public void testTemperatureUnsupportedOperations_Add() {
        try {
            Quantity<TemperatureUnit> temp1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
            Quantity<TemperatureUnit> temp2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
            temp1.Add(temp2);
        } catch (UnsupportedOperationException e) {
            assert (true);
            return;
        }
        assert (false);
    }

    @Test
    public void testTemperatureUnsupportedOperations_Subtract() {
        try {
            Quantity<TemperatureUnit> temp1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
            Quantity<TemperatureUnit> temp2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
            temp1.Subtract(temp2);
        } catch (UnsupportedOperationException e) {
            assert (true);
            return;
        }
        assert (false);
    }

    @Test
    public void testTemperatureUnsupportedOperation_Divide() {
        try {
            Quantity<TemperatureUnit> temp1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
            Quantity<TemperatureUnit> temp2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
            temp1.Divide(temp2);
        } catch (UnsupportedOperationException e) {
            assert (true);
            return;
        }
        assert (false);
    }

    @Test
    public void testTemperatureUnsupportedOperation_ErrorMessage() {
        try {
            Quantity<TemperatureUnit> temp1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
            Quantity<TemperatureUnit> temp2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
            temp1.Add(temp2);
        } catch (UnsupportedOperationException e) {
            assert (e.getMessage().contains("Temperature does not support ADD operation."));
            return;
        }
        assert (false);
    }

    @Test
    public void testTemperatureVsLengthIncompatibility() {
        boolean quantity = QuantityMeasurementApp.demonstrateEquality(new Quantity<>(100.0, TemperatureUnit.CELSIUS), new Quantity<>(100.0, LengthUnit.FEET));
        assertEquals(false, quantity);
    }

    @Test
    public void testTemperatureVsWeightIncompatibility() {
        boolean quantity = QuantityMeasurementApp.demonstrateEquality(new Quantity<>(100.0, TemperatureUnit.CELSIUS), new Quantity<>(100.0, WeigthUnit.KILOGRAM));
        assertEquals(false, quantity);
    }

    @Test
    public void testTemperatureVsVolumeIncompatibility() {
        boolean quantity = QuantityMeasurementApp.demonstrateEquality(new Quantity<>(100.0, TemperatureUnit.CELSIUS), new Quantity<>(100.0, VolumeUnit.LITRE));
        assertEquals(false, quantity);
    }

    @Test
    public void testOperationSupportMethods_TemperatureUnitAddition() {
        Quantity<TemperatureUnit> temp1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertFalse(temp1.getUnit().supportsArithmetic(), "Temperature unit should not support arithmetic operations");
    }

    @Test
    public void testOperationSupportMethods_TemperatureUnitDivision() {
        Quantity<TemperatureUnit> temp1 = new Quantity<>(100.0, TemperatureUnit.FAHRENHEIT);
        assertFalse(temp1.getUnit().supportsArithmetic(), "Temperature unit should not support arithmetic operations");
    }

    @Test
    public void testOperationSupportMethods_LengthUnitAddition() {
        Quantity<LengthUnit> length = new Quantity<>(100.0, LengthUnit.FEET);
        assertTrue(length.getUnit().supportsArithmetic(), "Length unit should support arithmetic operations");
    }

    @Test
    public void testOperationSupportMethods_WeightUnitDivision() {
        Quantity<WeigthUnit> weight = new Quantity<>(100.0, WeigthUnit.KILOGRAM);
        assertTrue(weight.getUnit().supportsArithmetic(), "Weight unit should support arithmetic operations");
    }

    @Test
    public void testIMeasurableInterface_Evolution_BackwardCompatible() {
        Quantity<LengthUnit> q1 = new Quantity<>(100.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(100.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = q1.Add(q2);
        assertEquals(200.0, result.getValue());
        assertTrue(LengthUnit.FEET.supportsArithmetic());

        Quantity<WeigthUnit> q3 = new Quantity<>(10.0, WeigthUnit.KILOGRAM);
        Quantity<WeigthUnit> q4 = new Quantity<>(5000.0, WeigthUnit.GRAM);
        Quantity<WeigthUnit> result1 = q3.Add(q4);
        assertEquals(15.0, result1.getValue());
        assertTrue(WeigthUnit.KILOGRAM.supportsArithmetic());

        Quantity<VolumeUnit> v1 = new Quantity<>(10.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> result2 = v1.Add(v2);
        assertEquals(10.5, result2.getValue());
        assertTrue(VolumeUnit.LITRE.supportsArithmetic());
    }

    @Test
    public void testTemperatureUnit_NonLinearConversion() {
        Quantity<TemperatureUnit> tempC = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> tempF = tempC.convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(212.0, tempF.getValue(), 0.0001);

        Quantity<TemperatureUnit> tempC2 = tempF.convertTo(TemperatureUnit.CELSIUS);
        assertEquals(100.0, tempC2.getValue(), 0.0001);
    }

    @Test
    public void testTemperatureUnit_AllConstants() {
        assertNotNull(TemperatureUnit.CELSIUS);
        assertNotNull(TemperatureUnit.FAHRENHEIT);
        assertNotNull(TemperatureUnit.KELVIN);

        assertEquals(3, TemperatureUnit.values().length, "There should be exactly 3 temperature units defined");
    }

    @Test
    public void testTemperatureUnit_NameMethod() {
        assertEquals("CELSIUS", TemperatureUnit.CELSIUS.getUnitName());
        assertEquals("FAHRENHEIT", TemperatureUnit.FAHRENHEIT.getUnitName());
        assertEquals("KELVIN", TemperatureUnit.KELVIN.getUnitName());
    }

    @Test
    public void testTemperatureUnit_ConversionFactor() {
        assertEquals(1.0, TemperatureUnit.CELSIUS.getConversionValue().apply(1.0), 0.0001);
        assertEquals(1.0, TemperatureUnit.FAHRENHEIT.getConversionValue().apply(33.8), 0.0001);
        assertEquals(1.0, TemperatureUnit.KELVIN.getConversionValue().apply(274.15), 0.0001);
    }

    @Test
    public void testTemperatureNullUnitValidation() {
        try {
            new Quantity<>(100.0, null);
        } catch (IllegalArgumentException e) {
            assert (e.getMessage().contains("Unit cannot be null"));
            return;
        }
        assert (false);
    }

    @Test
    public void testTemperatureNullOperandValidation_InComparison() {
        Quantity<TemperatureUnit> temp1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertEquals(false, temp1.equals(null));
    }

    @Test
    public void testTemperatureDifferentValuesInequality() {
        Quantity<TemperatureUnit> temp1 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> temp2 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertEquals(false, temp1.equals(temp2));
    }

    @Test
    public void testTemperatureBackwardCompatibility_UC1_Through_UC13() {
        Quantity<LengthUnit> length1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> length2 = new Quantity<>(12.0, LengthUnit.INCHES);
        assertTrue(QuantityMeasurementApp.demonstrateEquality(length1, length2));

        Quantity<LengthUnit> convertedLength = QuantityMeasurementApp.demonstrateConversion(length1, LengthUnit.INCHES);
        assertTrue(convertedLength.equals(new Quantity<>(12.0, LengthUnit.INCHES)));

        Quantity<LengthUnit> addedLength = QuantityMeasurementApp.demonstrateAddition(length1, length2);
        assertTrue(addedLength.equals(new Quantity<>(2.0, LengthUnit.FEET)));

        Quantity<LengthUnit> addedLengthWithTarget = QuantityMeasurementApp.demonstrateAddition(length1, length2, LengthUnit.YARDS);
        assertTrue(addedLengthWithTarget.equals(new Quantity<>(2.0 / 3.0, LengthUnit.YARDS)));

        Quantity<WeigthUnit> weight1 = new Quantity<>(10.0, WeigthUnit.KILOGRAM);
        Quantity<WeigthUnit> weight2 = new Quantity<>(5000.0, WeigthUnit.GRAM);
        Quantity<WeigthUnit> addedWeight = QuantityMeasurementApp.demonstrateAddition(weight1, weight2);
        assertTrue(addedWeight.equals(new Quantity<>(15.0, WeigthUnit.KILOGRAM)));
        Quantity<WeigthUnit> subtractedWeight = QuantityMeasurementApp.demonstrateSubtraction(weight1, weight2);
        assertTrue(subtractedWeight.equals(new Quantity<>(5.0, WeigthUnit.KILOGRAM)));

        Quantity<VolumeUnit> volume1 = new Quantity<>(10.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> addedVolume = QuantityMeasurementApp.demonstrateAddition(volume1, volume2);
        assertTrue(addedVolume.equals(new Quantity<>(10.5, VolumeUnit.LITRE)));
        Quantity<VolumeUnit> dividedVolume = QuantityMeasurementApp.demonstrateDivision(volume1, volume2);
        assertTrue(dividedVolume.equals(new Quantity<>(20.0, VolumeUnit.LITRE)));


        try {
            new Quantity<>(1.0, null);
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Unit cannot be null"));
            return;
        }
        assertTrue(false);
    }
    @Test
    public void testTemperatureConversionPrecision_Epsilon(){
        Quantity<TemperatureUnit> tempC = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> tempF = tempC.convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(212.0, tempF.getValue(), 0.0001);

        Quantity<TemperatureUnit> tempC2 = tempF.convertTo(TemperatureUnit.CELSIUS);
        assertEquals(100.0, tempC2.getValue(), 0.0001);
    }
    @Test
    public void testTemperatureConversionEdgeCase_VerySmallDifference(){
        Quantity<TemperatureUnit> tempC1 = new Quantity<>(0.0001, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> tempF1 = tempC1.convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(32.00018, tempF1.getValue(), 0.00001);

        Quantity<TemperatureUnit> tempC2 = tempF1.convertTo(TemperatureUnit.CELSIUS);
        assertEquals(0.0001, tempC2.getValue(), 0.00001);
    }
    @Test
    public void testTemperatureEnumImplementsIMeasurable(){
        assertTrue(TemperatureUnit.CELSIUS instanceof IMeasurable);
        assertTrue(TemperatureUnit.FAHRENHEIT instanceof IMeasurable);
        assertTrue(TemperatureUnit.KELVIN instanceof IMeasurable);
    }
    @Test
    public void testTemperatureDefaultMethodInheritance(){
        assertTrue(LengthUnit.FEET.supportsArithmetic());
        assertTrue(WeigthUnit.KILOGRAM.supportsArithmetic());
        assertTrue(VolumeUnit.LITRE.supportsArithmetic());
        Quantity<TemperatureUnit> temp = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertFalse(temp.getUnit().supportsArithmetic(), "Temperature unit should not support arithmetic operations");
        assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
    }
    @Test
    public void testTemperatureCrossUnitAdditionAttempt(){
        try {
            Quantity<TemperatureUnit> temp = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
            Quantity<LengthUnit> length = new Quantity<>(100.0, LengthUnit.FEET);
            temp.Add((Quantity)length);
        } catch (IllegalArgumentException e) {
            assert (true);
            return;
        }
        assert (false);
    }
    @Test
    public void testTemperatureValidateOperationSupport_MethodBehavior() {
        Quantity<TemperatureUnit> temp1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> temp2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);

        try {
            temp1.Add(temp2);
        } catch (UnsupportedOperationException e) {
            assert (e.getMessage().contains("Temperature does not support ADD operation."));
        }

        try {
            temp1.Subtract(temp2);
        } catch (UnsupportedOperationException e) {
            assert (e.getMessage().contains("Temperature does not support SUBTRACT operation."));
        }

        try {
            temp1.Divide(temp2);
        } catch (UnsupportedOperationException e) {
            assert (e.getMessage().contains("Temperature does not support DIVIDE operation."));
        }
    }
    @Test
    public void testTemperatureIntegrationWithGenericQuantity(){
        Quantity<TemperatureUnit> tempC = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> tempF = tempC.convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(212.0, tempF.getValue(), 0.0001);

        Quantity<TemperatureUnit> tempC2 = tempF.convertTo(TemperatureUnit.CELSIUS);
        assertEquals(100.0, tempC2.getValue(), 0.0001);

        try {
            tempC.Add(tempF);
        } catch (UnsupportedOperationException e) {
            assert (e.getMessage().contains("Temperature does not support ADD operation."));
        }
    }
}




