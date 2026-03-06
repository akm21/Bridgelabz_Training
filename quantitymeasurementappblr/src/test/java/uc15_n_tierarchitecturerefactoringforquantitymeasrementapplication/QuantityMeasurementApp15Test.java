package uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication;

import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.dto.QuantityDTO;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.dto.QuantityDTOv2;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.entity.QuantityMeasurementEntity;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.exception.CategoryMismatchException;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.exception.DivisionByZeroException;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.exception.InvalidUnitException;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.exception.PersistenceException;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.exception.QuantityMeasurementException;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.exception.UnsupportedQuantityOperationException;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.model.QuantityModel;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.repository.IQuantityMeasurementRepository;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.repository.QuantityMeasurementCacheRepository;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.service.QuantityMeasurementService;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.service.QuantityMeasurementServiceImpl;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.web.QuantityMeasurementApp;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.web.QuantityMeasurementController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementApp15Test {

    private QuantityMeasurementServiceImpl service;

    @BeforeEach
    public void setUp() {
        IQuantityMeasurementRepository stubRepo = new IQuantityMeasurementRepository() {
            @Override
            public void save(com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.entity.QuantityMeasurementEntity e) {
            }

            @Override
            public List<com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.entity.QuantityMeasurementEntity> findAll() {
                return new ArrayList<>();
            }
        };
        service = new QuantityMeasurementServiceImpl(stubRepo);
    }

    @Test
    public void testQuantityEntity_SingleOperandConstruction() {
        // Given
        double value = 10.0;
        String unit = "FEET";

        // When
        QuantityModel<QuantityDTO.LengthUnit> quantity = new QuantityModel<>(value, QuantityDTO.LengthUnit.FEET);

        // Then
        assert quantity.getValue() == value : "Value should be set correctly";
        //System.out.println(quantity.getUnit());
        assert quantity.getUnit().toString().equals(unit) : "Unit should be set correctly";
    }

    @Test
    public void testQuantityEntity_BinaryOperandConstruction() {
        // Given
        double value1 = 10.0;
        double value2 = 12.0;
        String unit1 = "FEET";
        String unit2 = "INCHES";

        // When
        QuantityModel<QuantityDTO.LengthUnit> quantity1 = new QuantityModel<>(value1, QuantityDTO.LengthUnit.FEET);
        QuantityModel<QuantityDTO.LengthUnit> quantity2 = new QuantityModel<>(value2, QuantityDTO.LengthUnit.INCHES);

        // Then
        assert quantity1.getValue() == value1 : "First value should be set correctly";
        assert quantity2.getValue() == value2 : "Second value should be set correctly";
        assert quantity1.getUnit().toString().equals(unit1) : "First unit should be set correctly";
        assert quantity2.getUnit().toString().equals(unit2) : "Second unit should be set correctly";
    }

    @Test
    public void testQuantityEntity_ErrorConstructin() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                new QuantityModel<>(10.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(12.0, QuantityDTO.VolumeUnit.LITERS),
                "COMPARE",
                "Incompatible measurement types",
                true
        );
        assert entity.isError : "Entity should indicate an error";
        assert entity.errorMessage.equals("Incompatible measurement types") : "Error message should be set correctly";
    }

    @Test
    public void testQuantityEntity_ToString_Success() {
        QuantityModel<QuantityDTO.LengthUnit> quantity = new QuantityModel<>(10.0, QuantityDTO.LengthUnit.FEET);
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                quantity,
                new QuantityModel<>(12.0, QuantityDTO.LengthUnit.INCHES),
                "COMPARE",
                "Equal"
        );
        String result = entity.toString();
        assert result.contains("COMPARE") : "toString should contain operation";
        assert result.contains("10.0") : "toString should contain thisValue";
    }

    @Test
    public void quantityModel_storesValueAndUnit() {
        QuantityModel<QuantityDTO.WeightUnit> model = new QuantityModel<>(5.0, QuantityDTO.WeightUnit.KILOGRAMS);
        assertEquals(5.0, model.getValue());
        assertEquals(QuantityDTO.WeightUnit.KILOGRAMS, model.getUnit());
    }

    @Test
    public void quantityModel_toString_containsValueAndUnit() {
        QuantityModel<QuantityDTO.VolumeUnit> model = new QuantityModel<>(3.5, QuantityDTO.VolumeUnit.LITERS);
        String s = model.toString();
        assert s.contains("3.5") : "toString should contain value";
        assert s.contains("LITERS") : "toString should contain unit";
    }

    @Test
    public void quantityDTO_of_withIMeasurableUnit_storesCorrectFields() {
        QuantityDTO dto = QuantityDTO.of(100.0, QuantityDTO.LengthUnit.CENTIMETERS);
        assertEquals(100.0, dto.getValue());
        assertEquals("CENTIMETERS", dto.getUnit());
        assertEquals("LengthUnit", dto.getMeasurementType());
    }

    @Test
    public void quantityDTO_equality_sameValueAndUnit() {
        QuantityDTO a = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO b = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        assertEquals(a, b);
    }

    @Test
    public void quantityDTO_inequality_differentValues() {
        QuantityDTO a = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO b = QuantityDTO.of(2.0, QuantityDTO.LengthUnit.FEET);
        assertNotEquals(a, b);
    }

    @Test
    public void compare_equalLengths_returnsTrue() {
        QuantityDTO oneFoot = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO twelveInches = QuantityDTO.of(12.0, QuantityDTO.LengthUnit.INCHES);
        assertTrue(service.compare(oneFoot, twelveInches));
    }

    @Test
    public void compare_unequalLengths_returnsFalse() {
        QuantityDTO twoFeet = QuantityDTO.of(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO twelveInches = QuantityDTO.of(12.0, QuantityDTO.LengthUnit.INCHES);
        assertFalse(service.compare(twoFeet, twelveInches));
    }

    @Test
    public void compare_crossCategory_throwsCategoryMismatchException() {
        QuantityDTO length = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO weight = QuantityDTO.of(1.0, QuantityDTO.WeightUnit.KILOGRAMS);
        assertThrows(CategoryMismatchException.class, () -> service.compare(length, weight));
    }

    @Test
    public void convert_feetToInches_returnsCorrectValue() {
        QuantityDTO twoFeet = QuantityDTO.of(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO targetInches = QuantityDTO.of(0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = service.convert(twoFeet, targetInches);
        assertEquals(24.0, result.getValue(), 1e-9);
        assertEquals("INCHES", result.getUnit());
    }

    @Test
    public void convert_kilogramsToGrams_returnsCorrectValue() {
        QuantityDTO oneKg = QuantityDTO.of(1.0, QuantityDTO.WeightUnit.KILOGRAMS);
        QuantityDTO targetGrams = QuantityDTO.of(0.0, QuantityDTO.WeightUnit.GRAMS);
        QuantityDTO result = service.convert(oneKg, targetGrams);
        assertEquals(1000.0, result.getValue(), 1e-9);
    }

    @Test
    public void convert_celsiusToFahrenheit_returnsCorrectValue() {
        QuantityDTO boiling = QuantityDTO.of(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO targetF = QuantityDTO.of(0.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        QuantityDTO result = service.convert(boiling, targetF);
        assertEquals(212.0, result.getValue(), 1e-9);
    }

    @Test
    public void convert_crossCategory_throwsCategoryMismatchException() {
        QuantityDTO length = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO weight = QuantityDTO.of(0.0, QuantityDTO.WeightUnit.GRAMS);
        assertThrows(CategoryMismatchException.class, () -> service.convert(length, weight));
    }

    @Test
    public void add_feetAndInches_returnsResultInFeet() {
        QuantityDTO oneFoot = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO twelveInches = QuantityDTO.of(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = service.add(oneFoot, twelveInches);
        assertEquals(2.0, result.getValue(), 1e-9);
        assertEquals("FEET", result.getUnit());
    }

    @Test
    public void add_withTargetUnit_returnsResultInTargetUnit() {
        QuantityDTO oneFoot = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO twelveInches = QuantityDTO.of(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO targetYards = QuantityDTO.of(0.0, QuantityDTO.LengthUnit.YARDS);
        QuantityDTO result = service.add(oneFoot, twelveInches, targetYards);
        assertEquals(24.0 / 36.0, result.getValue(), 1e-9);
        assertEquals("YARDS", result.getUnit());
    }

    @Test
    public void add_crossCategory_throwsCategoryMismatchException() {
        QuantityDTO length = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO volume = QuantityDTO.of(1.0, QuantityDTO.VolumeUnit.LITERS);
        assertThrows(CategoryMismatchException.class, () -> service.add(length, volume));
    }

    @Test
    public void add_temperature_throwsUnsupportedQuantityOperationException() {
        QuantityDTO c1 = QuantityDTO.of(20.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO c2 = QuantityDTO.of(10.0, QuantityDTO.TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedQuantityOperationException.class, () -> service.add(c1, c2));
    }

    @Test
    public void subtract_twoWeights_returnsCorrectDifference() {
        QuantityDTO oneKg = QuantityDTO.of(1.0, QuantityDTO.WeightUnit.KILOGRAMS);
        QuantityDTO fiveHundredGrams = QuantityDTO.of(500.0, QuantityDTO.WeightUnit.GRAMS);
        QuantityDTO result = service.subtract(oneKg, fiveHundredGrams);
        assertEquals(0.5, result.getValue(), 1e-9);
        assertEquals("KILOGRAMS", result.getUnit());
    }

    @Test
    public void subtract_withTargetUnit_returnsResultInTargetUnit() {
        QuantityDTO twoFeet = QuantityDTO.of(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO sixInches = QuantityDTO.of(6.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO targetInches = QuantityDTO.of(0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = service.subtract(twoFeet, sixInches, targetInches);
        assertEquals(18.0, result.getValue(), 1e-9);
        assertEquals("INCHES", result.getUnit());
    }

    @Test
    public void divide_twoLengths_returnsCorrectQuotient() {
        QuantityDTO twoFeet = QuantityDTO.of(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO oneFoot = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        double result = service.divide(twoFeet, oneFoot);
        assertEquals(2.0, result, 1e-9);
    }

    @Test
    public void divide_byZero_throwsDivisionByZeroException() {
        QuantityDTO twoFeet = QuantityDTO.of(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO zero = QuantityDTO.of(0.0, QuantityDTO.LengthUnit.FEET);
        assertThrows(DivisionByZeroException.class, () -> service.divide(twoFeet, zero));
    }

    @Test
    public void quantityMeasurementEntity_compareResult_toString_containsCompareOperation() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                new QuantityModel<>(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(12.0, QuantityDTO.LengthUnit.INCHES),
                "COMPARE",
                "Equal"
        );
        String s = entity.toString();
        assert s.contains("COMPARE") : "toString should mention COMPARE";
        assert s.contains("Equal") : "toString should mention result string";
    }

    @Test
    public void quantityMeasurementEntity_arithmeticResult_toString_containsResultValue() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                new QuantityModel<>(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(12.0, QuantityDTO.LengthUnit.INCHES),
                "ADD",
                new QuantityModel<>(2.0, QuantityDTO.LengthUnit.FEET)
        );
        String s = entity.toString();
        assert s.contains("ADD") : "toString should mention ADD";
        assert s.contains("2.0") : "toString should mention result value";
    }

    @Test
    public void quantityMeasurementEntity_error_toString_containsErrorMessage() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                new QuantityModel<>(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(1.0, QuantityDTO.VolumeUnit.LITERS),
                "COMPARE",
                "Incompatible types",
                true
        );
        String s = entity.toString();
        assert s.contains("ERROR") : "toString should indicate ERROR";
        assert s.contains("Incompatible types") : "toString should contain error message";
    }

    @Test
    public void quantityMeasurementEntity_equals_sameFields_returnsTrue() {
        QuantityMeasurementEntity a = new QuantityMeasurementEntity(
                new QuantityModel<>(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(12.0, QuantityDTO.LengthUnit.INCHES),
                "COMPARE",
                "Equal"
        );
        QuantityMeasurementEntity b = new QuantityMeasurementEntity(
                new QuantityModel<>(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(12.0, QuantityDTO.LengthUnit.INCHES),
                "COMPARE",
                "Equal"
        );
        assertEquals(a, b);
    }

    @Test
    public void quantityMeasurementEntity_equals_differentResultString_returnsFalse() {
        QuantityMeasurementEntity a = new QuantityMeasurementEntity(
                new QuantityModel<>(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(12.0, QuantityDTO.LengthUnit.INCHES),
                "COMPARE",
                "Equal"
        );
        QuantityMeasurementEntity b = new QuantityMeasurementEntity(
                new QuantityModel<>(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(12.0, QuantityDTO.LengthUnit.INCHES),
                "COMPARE",
                "Not Equal"
        );
        assertNotEquals(a, b);
    }

    @Test
    public void quantityDTO_of_withStringUnitAndType_storesCorrectFields() {
        QuantityDTO dto = QuantityDTO.of(5.0, "GRAMS", "WeightUnit");
        assertEquals(5.0, dto.getValue());
        assertEquals("GRAMS", dto.getUnit());
        assertEquals("WeightUnit", dto.getMeasurementType());
    }

    @Test
    public void quantityDTO_toString_containsValueAndUnit() {
        QuantityDTO dto = QuantityDTO.of(2.5, QuantityDTO.LengthUnit.YARDS);
        String s = dto.toString();
        assert s.contains("2.5") : "toString should contain value";
        assert s.contains("YARDS") : "toString should contain unit";
    }

    @Test
    public void quantityDTO_hashCode_equalObjects_haveSameHashCode() {
        QuantityDTO a = QuantityDTO.of(3.0, QuantityDTO.WeightUnit.POUNDS);
        QuantityDTO b = QuantityDTO.of(3.0, QuantityDTO.WeightUnit.POUNDS);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void quantityDTO_inequality_differentUnits_sameValue() {
        QuantityDTO a = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO b = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.INCHES);
        assertNotEquals(a, b);
    }

    @Test
    public void quantityDTO_inequality_differentMeasurementType() {
        QuantityDTO a = QuantityDTO.of(1.0, "FEET", "LengthUnit");
        QuantityDTO b = QuantityDTO.of(1.0, "FEET", "WeightUnit");
        assertNotEquals(a, b);
    }

    @Test
    public void quantityModel_zeroValue_storesCorrectly() {
        QuantityModel<QuantityDTO.LengthUnit> model = new QuantityModel<>(0.0, QuantityDTO.LengthUnit.CENTIMETERS);
        assertEquals(0.0, model.getValue());
        assertEquals(QuantityDTO.LengthUnit.CENTIMETERS, model.getUnit());
    }

    @Test
    public void quantityModel_negativeValue_storesCorrectly() {
        QuantityModel<QuantityDTO.TemperatureUnit> model = new QuantityModel<>(-40.0, QuantityDTO.TemperatureUnit.CELSIUS);
        assertEquals(-40.0, model.getValue());
        assertEquals(QuantityDTO.TemperatureUnit.CELSIUS, model.getUnit());
    }

    @Test
    public void compare_equalWeights_returnsTrue() {
        QuantityDTO oneKg = QuantityDTO.of(1.0, QuantityDTO.WeightUnit.KILOGRAMS);
        QuantityDTO thousandGrams = QuantityDTO.of(1000.0, QuantityDTO.WeightUnit.GRAMS);
        assertTrue(service.compare(oneKg, thousandGrams));
    }

    @Test
    public void compare_equalVolumes_returnsTrue() {
        QuantityDTO oneLiter = QuantityDTO.of(1.0, QuantityDTO.VolumeUnit.LITERS);
        QuantityDTO thousandMl = QuantityDTO.of(1000.0, QuantityDTO.VolumeUnit.MILLILITERS);
        assertTrue(service.compare(oneLiter, thousandMl));
    }

    @Test
    public void compare_equalTemperatures_returnsTrue() {
        QuantityDTO celsius = QuantityDTO.of(0.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO fahrenheit = QuantityDTO.of(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        assertTrue(service.compare(celsius, fahrenheit));
    }

    @Test
    public void compare_unequalTemperatures_returnsFalse() {
        QuantityDTO celsius = QuantityDTO.of(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO fahrenheit = QuantityDTO.of(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        assertFalse(service.compare(celsius, fahrenheit));
    }

    @Test
    public void compare_sameUnit_sameMagnitude_returnsTrue() {
        QuantityDTO a = QuantityDTO.of(5.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO b = QuantityDTO.of(5.0, QuantityDTO.LengthUnit.FEET);
        assertTrue(service.compare(a, b));
    }

    @Test
    public void convert_inchesToFeet_returnsCorrectValue() {
        QuantityDTO twentyFourInches = QuantityDTO.of(24.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO targetFeet = QuantityDTO.of(0.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO result = service.convert(twentyFourInches, targetFeet);
        assertEquals(2.0, result.getValue(), 1e-9);
        assertEquals("FEET", result.getUnit());
    }

    @Test
    public void convert_centimetersToInches_returnsCorrectValue() {
        QuantityDTO twoPtFourFourCm = QuantityDTO.of(2.54, QuantityDTO.LengthUnit.CENTIMETERS);
        QuantityDTO targetInches = QuantityDTO.of(0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = service.convert(twoPtFourFourCm, targetInches);
        assertEquals(1.0, result.getValue(), 1e-9);
    }

    @Test
    public void convert_fahrenheitToCelsius_returnsCorrectValue() {
        QuantityDTO freezing = QuantityDTO.of(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        QuantityDTO targetC = QuantityDTO.of(0.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO result = service.convert(freezing, targetC);
        assertEquals(0.0, result.getValue(), 1e-9);
    }

    @Test
    public void convert_celsiusToKelvin_returnsCorrectValue() {
        QuantityDTO absoluteZeroC = QuantityDTO.of(0.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO targetK = QuantityDTO.of(0.0, QuantityDTO.TemperatureUnit.KELVIN);
        QuantityDTO result = service.convert(absoluteZeroC, targetK);
        assertEquals(273.15, result.getValue(), 1e-9);
    }

    @Test
    public void convert_litersToMilliliters_returnsCorrectValue() {
        QuantityDTO twoLiters = QuantityDTO.of(2.0, QuantityDTO.VolumeUnit.LITERS);
        QuantityDTO targetMl = QuantityDTO.of(0.0, QuantityDTO.VolumeUnit.MILLILITERS);
        QuantityDTO result = service.convert(twoLiters, targetMl);
        assertEquals(2000.0, result.getValue(), 1e-9);
    }

    @Test
    public void convert_sameUnit_returnsIdenticalValue() {
        QuantityDTO fiveKg = QuantityDTO.of(5.0, QuantityDTO.WeightUnit.KILOGRAMS);
        QuantityDTO targetKg = QuantityDTO.of(0.0, QuantityDTO.WeightUnit.KILOGRAMS);
        QuantityDTO result = service.convert(fiveKg, targetKg);
        assertEquals(5.0, result.getValue(), 1e-9);
        assertEquals("KILOGRAMS", result.getUnit());
    }

    @Test
    public void add_twoWeights_returnsCorrectSum() {
        QuantityDTO fiveHundredGrams = QuantityDTO.of(500.0, QuantityDTO.WeightUnit.GRAMS);
        QuantityDTO anotherFiveHundredGrams = QuantityDTO.of(500.0, QuantityDTO.WeightUnit.GRAMS);
        QuantityDTO result = service.add(fiveHundredGrams, anotherFiveHundredGrams);
        assertEquals(1000.0, result.getValue(), 1e-9);
        assertEquals("GRAMS", result.getUnit());
    }

    @Test
    public void add_twoVolumes_returnsCorrectSum() {
        QuantityDTO halfLiter = QuantityDTO.of(500.0, QuantityDTO.VolumeUnit.MILLILITERS);
        QuantityDTO anotherHalfLiter = QuantityDTO.of(500.0, QuantityDTO.VolumeUnit.MILLILITERS);
        QuantityDTO result = service.add(halfLiter, anotherHalfLiter);
        assertEquals(1000.0, result.getValue(), 1e-9);
    }

    @Test
    public void subtract_crossCategory_throwsCategoryMismatchException() {
        QuantityDTO length = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO weight = QuantityDTO.of(1.0, QuantityDTO.WeightUnit.KILOGRAMS);
        assertThrows(CategoryMismatchException.class, () -> service.subtract(length, weight));
    }

    @Test
    public void subtract_temperature_throwsUnsupportedQuantityOperationException() {
        QuantityDTO c1 = QuantityDTO.of(50.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO c2 = QuantityDTO.of(20.0, QuantityDTO.TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedQuantityOperationException.class, () -> service.subtract(c1, c2));
    }

    @Test
    public void subtract_inchesFromFeet_returnsCorrectResult() {
        QuantityDTO twoFeet = QuantityDTO.of(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO twelveInches = QuantityDTO.of(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = service.subtract(twoFeet, twelveInches);
        assertEquals(1.0, result.getValue(), 1e-9);
        assertEquals("FEET", result.getUnit());
    }

    @Test
    public void divide_crossCategory_throwsCategoryMismatchException() {
        QuantityDTO length = QuantityDTO.of(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO weight = QuantityDTO.of(1.0, QuantityDTO.WeightUnit.KILOGRAMS);
        assertThrows(CategoryMismatchException.class, () -> service.divide(length, weight));
    }

    @Test
    public void divide_temperature_throwsUnsupportedQuantityOperationException() {
        QuantityDTO c1 = QuantityDTO.of(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO c2 = QuantityDTO.of(50.0, QuantityDTO.TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedQuantityOperationException.class, () -> service.divide(c1, c2));
    }

    @Test
    public void divide_mixedLengthUnits_returnsCorrectQuotient() {
        QuantityDTO twentyFourInches = QuantityDTO.of(24.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO oneFoot = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        double result = service.divide(twentyFourInches, oneFoot);
        assertEquals(2.0, result, 1e-9);
    }

    @Test
    public void quantityMeasurementEntity_subtractResult_toString_containsSubtractOperation() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                new QuantityModel<>(2.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(6.0, QuantityDTO.LengthUnit.INCHES),
                "SUBTRACT",
                new QuantityModel<>(1.5, QuantityDTO.LengthUnit.FEET)
        );
        String s = entity.toString();
        assert s.contains("SUBTRACT") : "toString should mention SUBTRACT";
        assert s.contains("1.5") : "toString should mention result value";
    }

    @Test
    public void quantityMeasurementEntity_convertResult_toString_containsConvertOperation() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                new QuantityModel<>(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(0.0, QuantityDTO.LengthUnit.INCHES),
                "CONVERT",
                new QuantityModel<>(12.0, QuantityDTO.LengthUnit.INCHES)
        );
        String s = entity.toString();
        assert s.contains("CONVERT") : "toString should mention CONVERT";
        assert s.contains("12.0") : "toString should mention result value";
    }

    @Test
    public void quantityMeasurementEntity_equals_differentOperation_returnsFalse() {
        QuantityMeasurementEntity a = new QuantityMeasurementEntity(
                new QuantityModel<>(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(12.0, QuantityDTO.LengthUnit.INCHES),
                "COMPARE",
                "Equal"
        );
        QuantityMeasurementEntity b = new QuantityMeasurementEntity(
                new QuantityModel<>(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(12.0, QuantityDTO.LengthUnit.INCHES),
                "CONVERT",
                new QuantityModel<>(12.0, QuantityDTO.LengthUnit.INCHES)
        );
        assertNotEquals(a, b);
    }

    @Test
    public void quantityMeasurementEntity_isError_false_byDefault_onArithmeticConstructor() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                new QuantityModel<>(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(12.0, QuantityDTO.LengthUnit.INCHES),
                "ADD",
                new QuantityModel<>(2.0, QuantityDTO.LengthUnit.FEET)
        );
        assertFalse(entity.isError);
    }

    @Test
    public void quantityMeasurementEntity_errorConstructor_setsErrorFlagAndMessage() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                new QuantityModel<>(5.0, QuantityDTO.WeightUnit.KILOGRAMS),
                new QuantityModel<>(100.0, QuantityDTO.LengthUnit.CENTIMETERS),
                "ADD",
                "Cross-category addition not allowed",
                true
        );
        assertTrue(entity.isError);
        assertEquals("Cross-category addition not allowed", entity.errorMessage);
    }

    @Test
    public void convert_yardsToFeet_returnsCorrectValue() {
        QuantityDTO oneYard = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.YARDS);
        QuantityDTO targetFeet = QuantityDTO.of(0.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO result = service.convert(oneYard, targetFeet);
        assertEquals(3.0, result.getValue(), 1e-9);
        assertEquals("FEET", result.getUnit());
    }

    @Test
    public void add_withTargetUnit_crossCategoryTarget_throwsCategoryMismatchException() {
        QuantityDTO oneFoot = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO anotherFoot = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO targetKg = QuantityDTO.of(0.0, QuantityDTO.WeightUnit.KILOGRAMS);
        assertThrows(CategoryMismatchException.class, () -> service.add(oneFoot, anotherFoot, targetKg));
    }

    // ── QuantityMeasurementApp ──────────────────────────────────────────────

    @Test
    public void quantityMeasurementApp_getInstance_returnsSameInstanceOnRepeatedCalls() {
        QuantityMeasurementApp first = QuantityMeasurementApp.getInstance();
        QuantityMeasurementApp second = QuantityMeasurementApp.getInstance();
        assertSame(first, second);
    }

    @Test
    public void quantityMeasurementApp_controllerIsInitialised() {
        QuantityMeasurementApp app = QuantityMeasurementApp.getInstance();
        assertNotNull(app.controller);
    }

    @Test
    public void quantityMeasurementApp_repositoryIsInitialised() {
        QuantityMeasurementApp app = QuantityMeasurementApp.getInstance();
        assertNotNull(app.repository);
    }

    // ── QuantityMeasurementController ──────────────────────────────────────

    private QuantityMeasurementController controllerWithStubRepo() {
        IQuantityMeasurementRepository stubRepo = new IQuantityMeasurementRepository() {
            @Override
            public void save(QuantityMeasurementEntity e) {
            }

            @Override
            public List<QuantityMeasurementEntity> findAll() {
                return new ArrayList<>();
            }
        };
        QuantityMeasurementService svc = new QuantityMeasurementServiceImpl(stubRepo);
        return new QuantityMeasurementController(svc);
    }

    @Test
    public void controller_performComparison_oneFeetEqualstwelveInches_returnsTrue() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO oneFoot = QuantityDTO.of(1, QuantityDTO.LengthUnit.FEET);
        QuantityDTO twelveInches = QuantityDTO.of(12, QuantityDTO.LengthUnit.INCHES);
        assertTrue(controller.performComparison(oneFoot, twelveInches));
    }

    @Test
    public void controller_performComparison_differentLengths_returnsFalse() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO twoFeet = QuantityDTO.of(2, QuantityDTO.LengthUnit.FEET);
        QuantityDTO twelveInches = QuantityDTO.of(12, QuantityDTO.LengthUnit.INCHES);
        assertFalse(controller.performComparison(twoFeet, twelveInches));
    }

    @Test
    public void controller_performComparison_crossCategory_throwsCategoryMismatchException() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO length = QuantityDTO.of(1, QuantityDTO.LengthUnit.FEET);
        QuantityDTO weight = QuantityDTO.of(1, QuantityDTO.WeightUnit.KILOGRAMS);
        assertThrows(CategoryMismatchException.class, () -> controller.performComparison(length, weight));
    }

    @Test
    public void controller_performConversion_212FahrenheitToCelsius_returns100() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO f212 = QuantityDTO.of(212, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        QuantityDTO toC = QuantityDTO.of(0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO result = controller.performConversion(f212, toC);
        assertEquals(100.0, result.getValue(), 1e-9);
        assertEquals("CELSIUS", result.getUnit());
    }

    @Test
    public void controller_performConversion_oneFeetToInches_returns12() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO oneFoot = QuantityDTO.of(1, QuantityDTO.LengthUnit.FEET);
        QuantityDTO toInches = QuantityDTO.of(0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = controller.performConversion(oneFoot, toInches);
        assertEquals(12.0, result.getValue(), 1e-9);
        assertEquals("INCHES", result.getUnit());
    }

    @Test
    public void controller_performConversion_crossCategory_throwsCategoryMismatchException() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO length = QuantityDTO.of(1, QuantityDTO.LengthUnit.FEET);
        QuantityDTO weight = QuantityDTO.of(0, QuantityDTO.WeightUnit.GRAMS);
        assertThrows(CategoryMismatchException.class, () -> controller.performConversion(length, weight));
    }

    @Test
    public void controller_performAddition_oneFeetAndTwelveInches_returnsTwoFeet() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO oneFoot = QuantityDTO.of(1, QuantityDTO.LengthUnit.FEET);
        QuantityDTO twelveInches = QuantityDTO.of(12, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = controller.performAddition(oneFoot, twelveInches);
        assertEquals(2.0, result.getValue(), 1e-9);
        assertEquals("FEET", result.getUnit());
    }

    @Test
    public void controller_performAddition_withTargetUnit_oneFeetAndTwelveInches_returnsTwentyFourInches() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO oneFoot = QuantityDTO.of(1, QuantityDTO.LengthUnit.FEET);
        QuantityDTO twelveInches = QuantityDTO.of(12, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO targetInches = QuantityDTO.of(0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = controller.performAddition(oneFoot, twelveInches, targetInches);
        assertEquals(24.0, result.getValue(), 1e-9);
        assertEquals("INCHES", result.getUnit());
    }

    @Test
    public void controller_performAddition_temperature_throwsUnsupportedQuantityOperationException() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO c1 = QuantityDTO.of(20, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO c2 = QuantityDTO.of(10, QuantityDTO.TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedQuantityOperationException.class, () -> controller.performAddition(c1, c2));
    }

    @Test
    public void controller_performSubtraction_threeFeetMinusTwelveInches_returnsTwentyFourInches() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO threeFeet = QuantityDTO.of(3, QuantityDTO.LengthUnit.FEET);
        QuantityDTO twelveInches = QuantityDTO.of(12, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO targetInches = QuantityDTO.of(0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = controller.performSubtraction(threeFeet, twelveInches, targetInches);
        assertEquals(24.0, result.getValue(), 1e-9);
        assertEquals("INCHES", result.getUnit());
    }

    @Test
    public void controller_performSubtraction_withoutTargetUnit_returnsResultInFirstUnit() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO twoFeet = QuantityDTO.of(2, QuantityDTO.LengthUnit.FEET);
        QuantityDTO twelveInches = QuantityDTO.of(12, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = controller.performSubtraction(twoFeet, twelveInches);
        assertEquals(1.0, result.getValue(), 1e-9);
        assertEquals("FEET", result.getUnit());
    }

    @Test
    public void controller_performSubtraction_crossCategory_throwsCategoryMismatchException() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO length = QuantityDTO.of(1, QuantityDTO.LengthUnit.FEET);
        QuantityDTO weight = QuantityDTO.of(1, QuantityDTO.WeightUnit.KILOGRAMS);
        assertThrows(CategoryMismatchException.class, () -> controller.performSubtraction(length, weight));
    }

    @Test
    public void controller_performDivision_twoFeetByTwelveInches_returnsTwo() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO twoFeet = QuantityDTO.of(2, QuantityDTO.LengthUnit.FEET);
        QuantityDTO twelveInches = QuantityDTO.of(12, QuantityDTO.LengthUnit.INCHES);
        double result = controller.performDivision(twoFeet, twelveInches);
        assertEquals(2.0, result, 1e-9);
    }

    @Test
    public void controller_performDivision_byZeroLength_throwsDivisionByZeroException() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO twoFeet = QuantityDTO.of(2, QuantityDTO.LengthUnit.FEET);
        QuantityDTO zeroInches = QuantityDTO.of(0, QuantityDTO.LengthUnit.INCHES);
        assertThrows(DivisionByZeroException.class, () -> controller.performDivision(twoFeet, zeroInches));
    }

    @Test
    public void controller_performDivision_crossCategory_throwsCategoryMismatchException() {
        QuantityMeasurementController controller = controllerWithStubRepo();
        QuantityDTO length = QuantityDTO.of(2, QuantityDTO.LengthUnit.FEET);
        QuantityDTO weight = QuantityDTO.of(1, QuantityDTO.WeightUnit.KILOGRAMS);
        assertThrows(CategoryMismatchException.class, () -> controller.performDivision(length, weight));
    }

    // ── InvalidUnitException ───────────────────────────────────────────────

    @Test
    public void invalidUnitException_getMessage_returnsConstructedMessage() {
        InvalidUnitException exception = new InvalidUnitException("Invalid unit: XYZ");
        assertEquals("Invalid unit: XYZ", exception.getMessage());
    }

    @Test
    public void invalidUnitException_isInstanceOfQuantityMeasurementException() {
        InvalidUnitException exception = new InvalidUnitException("bad unit");
        assertInstanceOf(QuantityMeasurementException.class, exception);
    }

    @Test
    public void invalidUnitException_isInstanceOfRuntimeException() {
        InvalidUnitException exception = new InvalidUnitException("bad unit");
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    public void invalidUnitException_thrownAndCaughtAsQuantityMeasurementException() {
        assertThrows(QuantityMeasurementException.class, () -> {
            throw new InvalidUnitException("bad unit");
        });
    }

    @Test
    public void invalidUnitException_thrownWhenParsingUnknownLengthUnit() {
        QuantityDTO dto = QuantityDTO.of(1.0, "MILES", "LengthUnit");
        assertThrows(InvalidUnitException.class, () -> service.convert(
                dto, QuantityDTO.of(0.0, QuantityDTO.LengthUnit.FEET)));
    }

    @Test
    public void invalidUnitException_thrownWhenParsingUnknownWeightUnit() {
        QuantityDTO dto = QuantityDTO.of(1.0, "STONES", "WeightUnit");
        assertThrows(InvalidUnitException.class, () -> service.convert(
                dto, QuantityDTO.of(0.0, QuantityDTO.WeightUnit.GRAMS)));
    }

    @Test
    public void invalidUnitException_thrownWhenParsingUnknownVolumeUnit() {
        QuantityDTO dto = QuantityDTO.of(1.0, "CUPS", "VolumeUnit");
        assertThrows(InvalidUnitException.class, () -> service.convert(
                dto, QuantityDTO.of(0.0, QuantityDTO.VolumeUnit.LITERS)));
    }

    @Test
    public void invalidUnitException_thrownWhenParsingUnknownTemperatureUnit() {
        QuantityDTO dto = QuantityDTO.of(1.0, "RANKINE", "TemperatureUnit");
        assertThrows(InvalidUnitException.class, () -> service.convert(
                dto, QuantityDTO.of(0.0, QuantityDTO.TemperatureUnit.CELSIUS)));
    }

    @Test
    public void invalidUnitException_messageIsPreservedWhenCaughtAsParentType() {
        String expectedMessage = "Invalid unit 'MILES' for type LengthUnit";
        try {
            throw new InvalidUnitException(expectedMessage);
        } catch (QuantityMeasurementException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    // ── PersistenceException ───────────────────────────────────────────────

    @Test
    public void persistenceException_getMessage_returnsConstructedMessage() {
        Throwable cause = new RuntimeException("disk full");
        PersistenceException exception = new PersistenceException("Failed to persist entity", cause);
        assertEquals("Failed to persist entity", exception.getMessage());
    }

    @Test
    public void persistenceException_getCause_returnsConstructedCause() {
        Throwable cause = new RuntimeException("disk full");
        PersistenceException exception = new PersistenceException("Failed to persist entity", cause);
        assertSame(cause, exception.getCause());
    }

    @Test
    public void persistenceException_isInstanceOfQuantityMeasurementException() {
        PersistenceException exception = new PersistenceException("error", new RuntimeException());
        assertInstanceOf(QuantityMeasurementException.class, exception);
    }

    @Test
    public void persistenceException_isInstanceOfRuntimeException() {
        PersistenceException exception = new PersistenceException("error", new RuntimeException());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    public void persistenceException_thrownAndCaughtAsQuantityMeasurementException() {
        Throwable cause = new RuntimeException("io error");
        assertThrows(QuantityMeasurementException.class, () -> {
            throw new PersistenceException("Failed to persist entity", cause);
        });
    }

    @Test
    public void persistenceException_causeMessageIsAccessible() {
        Throwable cause = new RuntimeException("underlying io error");
        PersistenceException exception = new PersistenceException("Failed to persist entity", cause);
        assertEquals("underlying io error", exception.getCause().getMessage());
    }

    @Test
    public void persistenceException_messageAndCauseArePreservedWhenCaughtAsParentType() {
        Throwable cause = new RuntimeException("io failure");
        String expectedMessage = "Failed to persist entity";
        try {
            throw new PersistenceException(expectedMessage, cause);
        } catch (QuantityMeasurementException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertSame(cause, e.getCause());
        }
    }

    // ── AppendableObjectOutputStream / writeStreamHeader ──────────────────

    private static final String REPO_FILE = "quantity_measurement_repo.ser";

    private void resetCacheRepositorySingleton() throws Exception {
        Field field = QuantityMeasurementCacheRepository.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);
    }

    private void deleteRepoFile() {
        File file = new File(REPO_FILE);
        if (file.exists()) assertTrue(file.delete(), "Repo file should be deletable");
    }

    private QuantityMeasurementEntity sampleEntity() {
        return new QuantityMeasurementEntity(
                new QuantityModel<>(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityModel<>(12.0, QuantityDTO.LengthUnit.INCHES),
                "COMPARE",
                "Equal"
        );
    }

    @Test
    public void writeStreamHeader_onNonExistentFile_writesValidSerialStreamReadableByFindAll() throws Exception {
        deleteRepoFile();
        resetCacheRepositorySingleton();

        QuantityMeasurementCacheRepository repo = QuantityMeasurementCacheRepository.getInstance();
        repo.save(sampleEntity());

        assertEquals(1, repo.findAll().size());

        deleteRepoFile();
        resetCacheRepositorySingleton();
    }

    @Test
    public void writeStreamHeader_onEmptyFile_writesValidSerialStreamReadableByFindAll() throws Exception {
        deleteRepoFile();
        File repoFile = new File(REPO_FILE);
        assertTrue(repoFile.createNewFile(), "Empty repo file should be creatable");
        resetCacheRepositorySingleton();

        QuantityMeasurementCacheRepository repo = QuantityMeasurementCacheRepository.getInstance();
        repo.save(sampleEntity());

        assertEquals(1, repo.findAll().size());

        deleteRepoFile();
        resetCacheRepositorySingleton();
    }

    @Test
    public void writeStreamHeader_onExistingFile_appendsSecondEntityWithoutCorruptingStream() throws Exception {
        deleteRepoFile();
        resetCacheRepositorySingleton();

        QuantityMeasurementCacheRepository repo = QuantityMeasurementCacheRepository.getInstance();
        repo.save(sampleEntity());
        repo.save(sampleEntity());

        assertEquals(2, repo.findAll().size());

        deleteRepoFile();
        resetCacheRepositorySingleton();
    }

    @Test
    public void writeStreamHeader_onExistingFile_persistedEntitiesSurviveRepositoryRestart() throws Exception {
        deleteRepoFile();
        resetCacheRepositorySingleton();

        QuantityMeasurementCacheRepository first = QuantityMeasurementCacheRepository.getInstance();
        first.save(sampleEntity());
        first.save(sampleEntity());

        resetCacheRepositorySingleton();

        QuantityMeasurementCacheRepository reloaded = QuantityMeasurementCacheRepository.getInstance();
        assertEquals(2, reloaded.findAll().size());

        deleteRepoFile();
        resetCacheRepositorySingleton();
    }

    @Test
    public void writeStreamHeader_multipleAppends_allEntitiesDeserialiseCorrectly() throws Exception {
        deleteRepoFile();
        resetCacheRepositorySingleton();

        QuantityMeasurementCacheRepository repo = QuantityMeasurementCacheRepository.getInstance();
        repo.save(sampleEntity());
        repo.save(sampleEntity());
        repo.save(sampleEntity());

        resetCacheRepositorySingleton();

        QuantityMeasurementCacheRepository reloaded = QuantityMeasurementCacheRepository.getInstance();
        assertEquals(3, reloaded.findAll().size());

        deleteRepoFile();
        resetCacheRepositorySingleton();
    }

    @Test
    public void writeStreamHeader_savedEntityEqualsReloadedEntity() throws Exception {
        deleteRepoFile();
        resetCacheRepositorySingleton();

        QuantityMeasurementCacheRepository repo = QuantityMeasurementCacheRepository.getInstance();
        QuantityMeasurementEntity saved = sampleEntity();
        repo.save(saved);

        resetCacheRepositorySingleton();

        QuantityMeasurementCacheRepository reloaded = QuantityMeasurementCacheRepository.getInstance();
        QuantityMeasurementEntity loaded = reloaded.findAll().getFirst();
        assertEquals(saved, loaded);

        deleteRepoFile();
        resetCacheRepositorySingleton();
    }

    // ── QuantityDTOv2 ──────────────────────────────────────────────────────

    @Test
    public void quantityDTOv2_fromV1_copiesValueFromV1() {
        QuantityDTO v1 = QuantityDTO.of(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTOv2 v2 = QuantityDTOv2.fromV1(v1, 1000L);
        assertEquals(1.0, v2.getValue());
    }

    @Test
    public void quantityDTOv2_fromV1_copiesUnitFromV1() {
        QuantityDTO v1 = QuantityDTO.of(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTOv2 v2 = QuantityDTOv2.fromV1(v1, 2000L);
        assertEquals("INCHES", v2.getUnit());
    }

    @Test
    public void quantityDTOv2_fromV1_copiesMeasurementTypeFromV1() {
        QuantityDTO v1 = QuantityDTO.of(1.0, QuantityDTO.WeightUnit.KILOGRAMS);
        QuantityDTOv2 v2 = QuantityDTOv2.fromV1(v1, 3000L);
        assertEquals("WeightUnit", v2.getMeasurementType());
    }

    @Test
    public void quantityDTOv2_fromV1_storesSuppliedTimestamp() {
        QuantityDTO v1 = QuantityDTO.of(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        long timestamp = 1_700_000_000_000L;
        QuantityDTOv2 v2 = QuantityDTOv2.fromV1(v1, timestamp);
        assertEquals(timestamp, v2.getTimestamp());
    }

    @Test
    public void quantityDTOv2_fromV1_differentTimestamps_produceIndependentInstances() {
        QuantityDTO v1 = QuantityDTO.of(5.0, QuantityDTO.VolumeUnit.LITERS);
        QuantityDTOv2 first = QuantityDTOv2.fromV1(v1, 1000L);
        QuantityDTOv2 second = QuantityDTOv2.fromV1(v1, 2000L);
        assertNotSame(first, second);
        assertEquals(1000L, first.getTimestamp());
        assertEquals(2000L, second.getTimestamp());
    }

    @Test
    public void quantityDTOv2_fromV1_zeroTimestamp_isStoredCorrectly() {
        QuantityDTO v1 = QuantityDTO.of(3.0, QuantityDTO.LengthUnit.YARDS);
        QuantityDTOv2 v2 = QuantityDTOv2.fromV1(v1, 0L);
        assertEquals(0L, v2.getTimestamp());
    }

    @Test
    public void quantityDTOv2_fromV1_negativeTimestamp_isStoredCorrectly() {
        QuantityDTO v1 = QuantityDTO.of(3.0, QuantityDTO.LengthUnit.YARDS);
        QuantityDTOv2 v2 = QuantityDTOv2.fromV1(v1, -1L);
        assertEquals(-1L, v2.getTimestamp());
    }

    @Test
    public void quantityDTOv2_fromV1_zeroValue_isStoredCorrectly() {
        QuantityDTO v1 = QuantityDTO.of(0.0, QuantityDTO.LengthUnit.CENTIMETERS);
        QuantityDTOv2 v2 = QuantityDTOv2.fromV1(v1, 5000L);
        assertEquals(0.0, v2.getValue());
    }

    @Test
    public void quantityDTOv2_fromV1_preservesAllV1FieldsTogether() {
        QuantityDTO v1 = QuantityDTO.of(2.54, QuantityDTO.LengthUnit.CENTIMETERS);
        long timestamp = 9_999L;
        QuantityDTOv2 v2 = QuantityDTOv2.fromV1(v1, timestamp);
        assertAll(
                () -> assertEquals(2.54, v2.getValue()),
                () -> assertEquals("CENTIMETERS", v2.getUnit()),
                () -> assertEquals("LengthUnit", v2.getMeasurementType()),
                () -> assertEquals(timestamp, v2.getTimestamp())
        );
    }

    @Test
    public void quantityDTOv2_fromV1_volumeUnit_preservesAllFields() {
        QuantityDTO v1 = QuantityDTO.of(3785.411784, QuantityDTO.VolumeUnit.MILLILITERS);
        QuantityDTOv2 v2 = QuantityDTOv2.fromV1(v1, 42L);
        assertAll(
                () -> assertEquals(3785.411784, v2.getValue(), 1e-9),
                () -> assertEquals("MILLILITERS", v2.getUnit()),
                () -> assertEquals("VolumeUnit", v2.getMeasurementType()),
                () -> assertEquals(42L, v2.getTimestamp())
        );
    }

    @Test
    public void quantityDTOv2_fromV1_temperatureUnit_preservesAllFields() {
        QuantityDTO v1 = QuantityDTO.of(-40.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        QuantityDTOv2 v2 = QuantityDTOv2.fromV1(v1, 7L);
        assertAll(
                () -> assertEquals(-40.0, v2.getValue()),
                () -> assertEquals("FAHRENHEIT", v2.getUnit()),
                () -> assertEquals("TemperatureUnit", v2.getMeasurementType()),
                () -> assertEquals(7L, v2.getTimestamp())
        );
    }
}
