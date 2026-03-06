package uc10_genericquantityclasswithunitinterfaceformulticategorysupport;

import com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.*;
import com.bridgelabz.wipro.uc11_volumemeasurementequalityconversionandaddition.VolumeUnit;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest10 {
    @Test
    public void testIMeasurableInterface_LengthUnitImplementation() {
        LengthUnit lengthUnit = LengthUnit.FEET;
        assert lengthUnit instanceof IMeasurable : "LengthUnit Should implement IMeasurable Interface";
    }

    @Test
    public void testIMeasurableInterface_WeightUnitImplementation() {
        WeightUnit weightUnit = WeightUnit.GRAM;
        assert weightUnit instanceof IMeasurable : "WeightUnit should implement IMeasurable Interface";
    }

    @Test
    public void testIMeasurable_ConsistentBehavior() {
        IMeasurable length = LengthUnit.INCHES;
        IMeasurable weight = WeightUnit.GRAM;

        assertNotNull(length.getUnitName());
        assertNotNull(weight.getUnitName());

        assertTrue(Double.isFinite(length.getConversionFactor()));
        assertTrue(Double.isFinite(weight.getConversionFactor()));

        double lengthBase = length.convertToBaseUnit(12.0);
        double lengthBack = length.convertFromBaseUnit(lengthBase);

        assertEquals(12.0, lengthBack);

        double weightBase = weight.convertToBaseUnit(500.0);
        double weightBack = weight.convertFromBaseUnit(weightBase);

        assertEquals(500.0, weightBack);
    }

    @Test
    public void testGenericQuantity_LengthOperations_Equality() {
        assertEquals(true, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES)));
    }

    @Test
    public void testGenericQuantity_WeightOperations_Equality() {
        assertEquals(true, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1.0, WeightUnit.KILOGRAM), new Quantity<>(1000.0, WeightUnit.GRAM)));
    }

    @Test
    public void testGenericQuantity_LengthOperations_Conversion() {
        Quantity quantity = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.INCHES);
        assertTrue(quantity.equals(new Quantity<>(12.0, LengthUnit.INCHES)));
    }

    @Test
    public void testGenericQuantity_WeightOperations_Conversion() {
        Quantity quantity = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM);
        assertTrue(quantity.equals(new Quantity<>(1000.0, WeightUnit.GRAM)));
    }

    @Test
    public void testGenericQuantity_LengthOperations_Addition() {
        Quantity quantity = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.FEET);
        assertTrue(quantity.equals(new Quantity<>(2.0, LengthUnit.FEET)));
    }

    @Test
    public void testGenericQuantity_WeightOperations_Addition() {
        Quantity quantity = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, WeightUnit.KILOGRAM), new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM);
        assertTrue(quantity.equals(new Quantity<>(2.0, WeightUnit.KILOGRAM)));
    }

    @Test
    public void testCrossCategoryPrevention_LengthVsWeight() {
        assertEquals(false, QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(1.0, WeightUnit.GRAM)));
    }

    @Test
    public void testCrossCategoryPrevention_CompilerTypeSafety() {
        Quantity length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertFalse(length.equals(weight));
    }

    @Test
    public void testGenericQuantity_ConstructorValidation_NullUnit() {
        try {
            Quantity quantity = new Quantity<>(1.0, null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testGenericQuantity_ConstructorValidation_InvalidValue() {
        try {
            Quantity quantity = new Quantity<>(Double.NaN, LengthUnit.FEET);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testGenericQuantity_Conversion_AllUnitCombinations() {
        Quantity feet = new Quantity<>(1.0, LengthUnit.FEET);
        assertEquals(12.0, feet.convertTo(LengthUnit.INCHES).getValue());
        assertEquals(1.0 / 3.0, feet.convertTo(LengthUnit.YARDS).getValue());

        Quantity kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertEquals(1000.0, kg.convertTo(WeightUnit.GRAM).getValue());
        assertEquals(1000 / 453.592, kg.convertTo(WeightUnit.POUND).getValue());
    }

    @Test
    public void testGenericQuantity_Addition_AllUnitCombinations() {
        Quantity length1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity length2 = new Quantity<>(12.0, LengthUnit.INCHES);

        assertEquals(2.0, QuantityMeasurementApp.demonstrateAddition(length1, length2, LengthUnit.FEET).getValue());

        Quantity weight1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity weight2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(2.0, QuantityMeasurementApp.demonstrateAddition(weight1, weight2, WeightUnit.KILOGRAM).getValue());
    }

    @Test
    public void testBackwardCompatibility_AllUC1Through9Tests() {
        assertTrue(new Quantity<>(1.0, LengthUnit.FEET).equals(new Quantity<>(12.0, LengthUnit.INCHES)));
        Quantity converted = new Quantity<>(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
        assertEquals(12.0, converted.getValue());
        Quantity sum = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.FEET);
        assertEquals(2.0, sum.getValue());
        assertFalse(new Quantity<>(1.0, LengthUnit.FEET).equals(new Quantity<>(1.0, WeightUnit.KILOGRAM)));
    }

    @Test
    public void testQuantityMeasurementApp_SimplifiedDemonstration_Equality() {
        Quantity length1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity length2 = new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity weight1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity weight2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertTrue(QuantityMeasurementApp.demonstrateEquality(length1, length2));
        assertTrue(QuantityMeasurementApp.demonstrateEquality(weight1, weight2));
    }

    @Test
    public void testQuantityMeasurementApp_SimplifiedDemonstration_Conversion() {
        Quantity length = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.INCHES);
        assertEquals(12.0, length.getValue());

        Quantity weight = QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM);
        assertEquals(1000.0, weight.getValue());
    }

    @Test
    public void testQuantityMeasurementApp_SimplifiedDemonstration_Addition() {
        Quantity lengthsum = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.FEET);
        assertEquals(2.0, lengthsum.getValue());

        Quantity weightsum = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, WeightUnit.KILOGRAM), new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM);
        assertEquals(2.0, weightsum.getValue());
    }

    @Test
    public void testTypeWildcard_FlexibleSignatures() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertNotNull(process(length));
        assertNotNull(process(weight));

    }

    private Quantity<?> process(Quantity<?> q) {
        return q;
    }

    @Test
    public void testScalability_NewUnitEnumIntegration() {
        com.bridgelabz.wipro.uc11_volumemeasurementequalityconversionandaddition.Quantity litre = new com.bridgelabz.wipro.uc11_volumemeasurementequalityconversionandaddition.Quantity<>(1.0, VolumeUnit.LITRE);
        com.bridgelabz.wipro.uc11_volumemeasurementequalityconversionandaddition.Quantity millilitre = new com.bridgelabz.wipro.uc11_volumemeasurementequalityconversionandaddition.Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertTrue(litre.equals(millilitre));
    }

    @Test
    public void testScalability_MultipleNewCategories() {
        Quantity<TestTemperatureUnit> temp =
                new Quantity<>(100.0, TestTemperatureUnit.CELSIUS);

        Quantity<TestTimeUnit> time =
                new Quantity<>(60.0, TestTimeUnit.SECOND);

        assertNotNull(temp);
        assertNotNull(time);
    }

    @Test
    public void testGenericBoundedTypeParameter_Enforcement() {
        // This WILL NOT COMPILE (intentional proof):
        //Quantity<String> invalid = new Quantity<>("1", "Invalid");

        Quantity<LengthUnit> valid =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertNotNull(valid);
    }

    @Test
    public void testHashCode_GenericQuantity_Consistency() {
        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(12.0, LengthUnit.INCHES);

        assertEquals(q1.hashCode(), q2.hashCode());
    }

    @Test
    public void testEquals_GenericQuantity_ContractPreservation() {
        Quantity<LengthUnit> a =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> b =
                new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> c =
                new Quantity<>(0.333333, LengthUnit.YARDS);

        // Reflexive
        assertTrue(a.equals(a));

        // Symmetric
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));

        // Transitive
        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));
    }

    @Test
    public void testEnumAsUnitCarrier_BehaviorEncapsulation() {
        IMeasurable unit = LengthUnit.INCHES;

        double base = unit.convertToBaseUnit(12.0);
        double back = unit.convertFromBaseUnit(base);

        assertEquals(12.0, back);
    }

    @Test
    public void testTypeErasure_RuntimeSafety() {
        Quantity<?> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<?> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(length.equals(weight));
    }

    @Test
    public void testCompositionOverInheritance_Flexibility() {
        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertNotNull(length.getUnit());
    }

    @Test
    public void CodeReduction_DRYValidation() {
        Method[] methods = Quantity.class.getDeclaredMethods();
        System.out.println(Arrays.stream(methods).toList());
        assertTrue(methods.length < 15); // heuristic validation
    }

    @Test
    public void testMaintainability_SingleSourceOfTruth() {
        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<WeightUnit> weight =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertNotNull(length);
        assertNotNull(weight);
    }

    @Test
    public void testArchitecturalReadiness_MultipleNewCategories() {
        com.bridgelabz.wipro.uc11_volumemeasurementequalityconversionandaddition.Quantity v =
                new com.bridgelabz.wipro.uc11_volumemeasurementequalityconversionandaddition.Quantity(1.0, VolumeUnit.LITRE);

        Quantity<TestTemperatureUnit> t =
                new Quantity<>(100.0, TestTemperatureUnit.CELSIUS);

        Quantity<TestTimeUnit> time =
                new Quantity<>(60.0, TestTimeUnit.SECOND);

        assertNotNull(v);
        assertNotNull(t);
        assertNotNull(time);
    }

    @Test
    public void testPerformance_GenericOverhead() {
        long start = System.nanoTime();

        for (int i = 0; i < 10000; i++) {
            new Quantity<>(1.0, LengthUnit.FEET)
                    .equals(new Quantity<>(12.0, LengthUnit.INCHES));
        }

        long duration = System.nanoTime() - start;

        assertTrue(duration < 50_000_000); // <50ms heuristic
    }

    @Test
    public void testDocumentation_PatternClarity() {
        assertTrue(IMeasurable.class.isInterface());
    }

    @Test
    public void testInterfaceSegregation_MinimalContract() {
        Method[] methods = IMeasurable.class.getDeclaredMethods();
        System.out.println(Arrays.stream(methods).toList());
        assertTrue(methods.length <= 5);
    }

    @Test
    public void testImmutability_GenericQuantity() {
        assertTrue(Modifier.isFinal(Quantity.class.getModifiers()));

        for (Method method : Quantity.class.getDeclaredMethods()) {
            assertFalse(method.getName().startsWith("set"));
        }
    }
}
