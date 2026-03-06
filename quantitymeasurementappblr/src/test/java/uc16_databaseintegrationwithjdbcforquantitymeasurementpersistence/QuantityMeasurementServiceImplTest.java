package uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence;

import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.controller.QuantityMeasurementController;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.entity.QuantityDTO;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.repository.IQuantityMeasurementRepository;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.repository.QuantityMeasurementCacheRepository;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.service.QuantityMeasurementServiceImpl;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.unit.LengthUnit;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.unit.WeightUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementServiceImplTest {

    private IQuantityMeasurementRepository repository;
    private QuantityMeasurementController controller;

    @BeforeEach
    void setUp() {
        repository = QuantityMeasurementCacheRepository.getInstance();
        repository.deleteAll();
        controller = new QuantityMeasurementController(new QuantityMeasurementServiceImpl(repository));
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
        repository.releaseResources();
    }

    @Test
    void compare_shouldReturnTrue_for2FeetAnd24Inches() {
        QuantityDTO feet2 = QuantityDTO.of(2, LengthUnit.FEET.getUnitName(), LengthUnit.FEET.getMeasurementType());
        QuantityDTO inches24 = QuantityDTO.of(24, LengthUnit.INCHES.getUnitName(), LengthUnit.INCHES.getMeasurementType());

        assertTrue(controller.performComparison(feet2, inches24));
    }

    @Test
    void convert_shouldConvert1YardTo36Inches() {
        QuantityDTO yard1 = QuantityDTO.of(1, LengthUnit.YARDS.getUnitName(), LengthUnit.YARDS.getMeasurementType());
        QuantityDTO inchTarget = QuantityDTO.of(0, LengthUnit.INCHES.getUnitName(), LengthUnit.INCHES.getMeasurementType());

        QuantityDTO result = controller.performConversion(yard1, inchTarget);
        assertEquals(36.0, result.getValue(), 1e-9);
        assertEquals(LengthUnit.INCHES.getUnitName(), result.getUnit());
    }

    @Test
    void add_shouldReturnSumInTargetUnit_whenUnitsDiffer() {
        QuantityDTO foot1 = QuantityDTO.of(1, LengthUnit.FEET.getUnitName(), LengthUnit.FEET.getMeasurementType());
        QuantityDTO inches12 = QuantityDTO.of(12, LengthUnit.INCHES.getUnitName(), LengthUnit.INCHES.getMeasurementType());
        QuantityDTO inchUnit = QuantityDTO.of(0, LengthUnit.INCHES.getUnitName(), LengthUnit.INCHES.getMeasurementType());

        QuantityDTO result = controller.performAddition(foot1, inches12, inchUnit);
        assertEquals(24.0, result.getValue(), 1e-9);
        assertEquals(LengthUnit.INCHES.getUnitName(), result.getUnit());
    }

    @Test
    void subtract_shouldHandleWeightUnitsAndTargetGrams() {
        QuantityDTO kg1 = QuantityDTO.of(1, WeightUnit.KILOGRAM.getUnitName(), WeightUnit.KILOGRAM.getMeasurementType());
        QuantityDTO gram500 = QuantityDTO.of(500, WeightUnit.GRAM.getUnitName(), WeightUnit.GRAM.getMeasurementType());
        QuantityDTO gramTarget = QuantityDTO.of(0, WeightUnit.GRAM.getUnitName(), WeightUnit.GRAM.getMeasurementType());

        QuantityDTO result = controller.performSubtraction(kg1, gram500, gramTarget);
        assertEquals(500.0, result.getValue(), 1e-9);
        assertEquals(WeightUnit.GRAM.getUnitName(), result.getUnit());
    }

    @Test
    void divide_shouldReturnQuotientForSameCategory() {
        QuantityDTO feet10 = QuantityDTO.of(10, LengthUnit.FEET.getUnitName(), LengthUnit.FEET.getMeasurementType());
        QuantityDTO feet2 = QuantityDTO.of(2, LengthUnit.FEET.getUnitName(), LengthUnit.FEET.getMeasurementType());

        double result = controller.performDivision(feet10, feet2);
        assertEquals(5.0, result, 1e-9);
    }
}

