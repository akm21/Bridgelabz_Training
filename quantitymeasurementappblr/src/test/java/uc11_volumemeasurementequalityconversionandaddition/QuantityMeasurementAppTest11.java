package uc11_volumemeasurementequalityconversionandaddition;

import com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.LengthUnit;
import com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.WeightUnit;
import com.bridgelabz.wipro.uc11_volumemeasurementequalityconversionandaddition.Quantity;
import com.bridgelabz.wipro.uc11_volumemeasurementequalityconversionandaddition.QuantityMeasurementApp;
import com.bridgelabz.wipro.uc11_volumemeasurementequalityconversionandaddition.VolumeUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest11 {
    @Test
    public void testEquality_LitreToLitre_SameValue() {
        assertEquals(true, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testEquality_LitreToLitre_DifferentValue() {
        assertEquals(false, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(2.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testEquality_LitreToMillilitre_EquivalentValue() {
        assertEquals(true, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testEquality_MillilitreToLitre_EquivalentValue() {
        assertEquals(true, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), new Quantity<>(1.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testEquality_LitreToGallon_EquivalentValue() {
        assertEquals(true, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(0.264172, VolumeUnit.GALLON)));
    }

    @Test
    public void testEquality_GallonToLitre_EquivalentValue() {
        assertEquals(true, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1.0, VolumeUnit.GALLON), new Quantity<>(3.78541, VolumeUnit.LITRE)));
    }

    @Test
    public void testEquality_VolumeVsLength_Incompatible() {
        Quantity litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity length = new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(1.0, LengthUnit.FEET);
        assertEquals(false, litre.equals(length));
    }

    @Test
    public void testEquality_VolumeVsWeight_Incompatible() {
        Quantity litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity weight = new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertEquals(false, litre.equals(weight));
    }

    @Test
    public void testEquality_NullComparison() {
        assertEquals(false, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1.0, VolumeUnit.LITRE), null));
    }

    @Test
    public void testEquality_SameReference() {
        Quantity volume = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertEquals(true, volume.equals(volume));
    }

    @Test
    public void testEquality_NullUnit() {
        try {
            com.bridgelabz.wipro.uc11_volumemeasurementequalityconversionandaddition.Quantity quantity = new com.bridgelabz.wipro.uc11_volumemeasurementequalityconversionandaddition.Quantity<>(1.0, null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testEquality_TransitiveProperty() {
        Quantity volume1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity volume2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity volume3 = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertTrue(volume1.equals(volume2));
        assertTrue(volume2.equals(volume3));
        assertTrue(volume1.equals(volume3));
    }

    @Test
    public void testEquality_ZeroValue() {
        assertEquals(true, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(0.0, VolumeUnit.LITRE), new Quantity<>(0.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testEquality_NegativeVolume() {
        assertEquals(true, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(-1.0, VolumeUnit.LITRE), new Quantity<>(-1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testEquality_LargeVolumeValue() {
        assertEquals(true, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1000000.0, VolumeUnit.MILLILITRE), new Quantity<>(1000.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testEquality_SmallVolumeValue() {
        assertEquals(true, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(0.001, VolumeUnit.LITRE), new Quantity<>(1.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testConversion_LitreToMillilitre() {
        Quantity volume = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
        assertTrue(volume.equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testConversion_MillilitreToLitre() {
        Quantity volume = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE);
        assertTrue(volume.equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testConversion_GallonToLitre() {
        Quantity volume = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1.0, VolumeUnit.GALLON), VolumeUnit.LITRE);
        assertTrue(volume.equals(new Quantity<>(3.78541, VolumeUnit.LITRE)));
    }

    @Test
    public void testConversion_LitreToGallon() {
        Quantity volume = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON);
        assertTrue(volume.equals(new Quantity<>(1.0, VolumeUnit.GALLON)));
    }

    @Test
    public void testConversion_MillilitreToGallon() {
        Quantity volume = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.GALLON);
        assertTrue(volume.equals(new Quantity<>(0.264172, VolumeUnit.GALLON)));
    }

    @Test
    public void testConversion_SameUnit() {
        Quantity volume = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(5.0, VolumeUnit.LITRE), VolumeUnit.LITRE);
        assertTrue(volume.equals(new Quantity<>(5.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testConversion_ZeroValue() {
        Quantity volume = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(0.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
        assertTrue(volume.equals(new Quantity<>(0.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testConversion_NegativeValue() {
        Quantity volume = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(-1.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
        assertTrue(volume.equals(new Quantity<>(-1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testConversion_RoundTrip() {
        Quantity volume1 = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1.5, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
        Quantity volume2 = QuantityMeasurementApp.demonstrateConversion(volume1, VolumeUnit.LITRE);
        assertTrue(volume2.equals(new Quantity<>(1.5, VolumeUnit.LITRE)));
    }

    @Test
    public void testAddition_SameUnit_LitrePlusLitre() {
        Quantity volume = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(2.0, VolumeUnit.LITRE));
        assertTrue(volume.equals(new Quantity<>(3.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testAddition_SameUnit_MillilitrePlusMillilitre() {
        Quantity volume = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(500.0, VolumeUnit.MILLILITRE), new Quantity<>(500.0, VolumeUnit.MILLILITRE));
        assertTrue(volume.equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testAddition_CrossUnit_LitrePlusMillilitre() {
        Quantity volume = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
        assertTrue(volume.equals(new Quantity<>(2.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testAddition_CrossUnit_MillilitrePlusLitre() {
        Quantity volume = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), new Quantity<>(1.0, VolumeUnit.LITRE));
        assertTrue(volume.equals(new Quantity<>(2000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testAddition_CrossUnit_GallonPlusLitre() {
        Quantity volume = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, VolumeUnit.GALLON), new Quantity<>(3.78541, VolumeUnit.LITRE));
        assertTrue(volume.equals(new Quantity<>(2.0, VolumeUnit.GALLON)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Litre() {
        Quantity volume = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE);
        assertTrue(volume.equals(new Quantity<>(2.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Millilitre() {
        Quantity volume = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE);
        assertTrue(volume.equals(new Quantity<>(2000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Gallon() {
        Quantity volume = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(3.78541, VolumeUnit.LITRE), new Quantity<>(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON);
        assertTrue(volume.equals(new Quantity<>(2.0, VolumeUnit.GALLON)));
    }

    @Test
    public void testAddition_Commutativity() {
        Quantity volume1 = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
        Quantity volume2 = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), new Quantity<>(1.0, VolumeUnit.LITRE));
        assertTrue(volume1.equals(volume2));
    }

    @Test
    public void testAddition_WithZero() {
        Quantity volume = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(0.0, VolumeUnit.MILLILITRE));
        assertTrue(volume.equals(new Quantity<>(5.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testAddition_NegativeValues() {
        Quantity volume = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(-2000.0, VolumeUnit.MILLILITRE));
        assertTrue(volume.equals(new Quantity<>(3.0, VolumeUnit.LITRE)));
    }

    @Test
    public void testAddition_LargeValues() {
        Quantity volume = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1e6, VolumeUnit.LITRE), new Quantity<>(1e6, VolumeUnit.LITRE));
        assertTrue(volume.equals(new Quantity<>(2e6, VolumeUnit.LITRE)));
    }

    @Test
    public void testAddition_SmallValues() {
        Quantity volume = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(0.001, VolumeUnit.LITRE), new Quantity<>(0.002, VolumeUnit.LITRE));
        assertTrue(volume.equals(new Quantity<>(0.003, VolumeUnit.LITRE)));
    }

    @Test
    public void testVolumeUnitEnum_LitreConstant() {
        assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor());
    }

    @Test
    public void testVolumeUnitEnum_MillilitreConstant() {
        assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor());
    }

    @Test
    public void testVolumeUnitEnum_GallonConstant() {
        assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor());
    }

    @Test
    public void testConvertToBaseUnit_LitreToLitre() {
        assertEquals(5.0, VolumeUnit.LITRE.convertToBaseUnit(5.0));
    }

    @Test
    public void testConvertToBaseUnit_MillilitreToLitre() {
        assertEquals(1.0, VolumeUnit.MILLILITRE.convertToBaseUnit(1000.0));
    }

    @Test
    public void testConvertToBaseUnit_GallonToLitre() {
        assertEquals(3.78541, VolumeUnit.GALLON.convertToBaseUnit(1.0));
    }

    @Test
    public void testConvertFromBaseUnit_LitreToLitre() {
        assertEquals(2.0, VolumeUnit.LITRE.convertFromBaseUnit(2.0));
    }

    @Test
    public void testConvertFromBaseUnit_LitreToMillilitre() {
        assertEquals(1000.0, VolumeUnit.MILLILITRE.convertFromBaseUnit(1.0));
    }

    @Test
    public void testConvertFromBaseUnit_LitreToGallon() {
        assertEquals(1.0, VolumeUnit.GALLON.convertFromBaseUnit(3.78541));
    }

    @Test
    public void testBackwardCompatibility_AllUC1Through10Tests() {
        assertTrue(new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(1.0, LengthUnit.FEET).equals(new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(12.0, LengthUnit.INCHES)));

        com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity lengthsum = com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.QuantityMeasurementApp.demonstrateAddition(new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(1.0, LengthUnit.FEET), new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.FEET);
        assertEquals(2.0, lengthsum.getValue());

        com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity lengthconvert = com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.QuantityMeasurementApp.demonstrateConversion(new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(1.0, LengthUnit.FEET), LengthUnit.INCHES);
        assertEquals(12.0, lengthconvert.getValue());

        assertTrue(new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(1.0, WeightUnit.KILOGRAM).equals(new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(1000.0, WeightUnit.GRAM)));

        com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity weightsum = com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.QuantityMeasurementApp.demonstrateAddition(new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(1.0, WeightUnit.KILOGRAM), new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM);
        assertEquals(2.0, weightsum.getValue());

        com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity weightconvert = com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.QuantityMeasurementApp.demonstrateConversion(new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM);
        assertEquals(1000.0, weightconvert.getValue());

        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE).equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));

        Quantity volumesum = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE);
        assertEquals(2.0, volumesum.getValue());

        Quantity volumeconvert = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
        assertEquals(1000.0, volumeconvert.getValue());
    }

    @Test
    public void testGenericQuantity_VolumeOperations_Consistency() {
        Quantity litre =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity millilitre =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertTrue(litre.equals(millilitre));

        Quantity converted =
                litre.convertTo(VolumeUnit.MILLILITRE);

        assertEquals(1000.0, converted.getValue());

        Quantity sum =
                litre.add(millilitre, VolumeUnit.LITRE);

        assertEquals(2.0, sum.getValue());

        Quantity roundTrip =
                litre.convertTo(VolumeUnit.GALLON)
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(1.0, roundTrip.getValue());
    }

    @Test
    public void testScalability_VolumeIntegration() {
        com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity length =
                new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity(1.0, LengthUnit.FEET);

        com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity weight =
                new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity(1.0, WeightUnit.KILOGRAM);

        Quantity volume =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertNotNull(length);
        assertNotNull(weight);
        assertNotNull(volume);

        // All are handled through same abstraction
        assertTrue(length instanceof com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<?>);
        assertTrue(weight instanceof com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<?>);
        assertTrue(volume instanceof Quantity<?>);

        // Generic processing method works for all
        assertNotNull(process1(length));
        assertNotNull(process1(weight));
        assertNotNull(process(volume));
    }

    private com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<?> process1(com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<?> quantity) {
        return quantity;
    }

    private Quantity<?> process(Quantity<?> quantity) {
        return quantity;
    }

}
