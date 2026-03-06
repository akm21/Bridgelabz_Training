package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence;


import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.controller.QuantityMeasurementController;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.entity.QuantityDTO;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.repository.IQuantityMeasurementRepository;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.repository.QuantityMeasurementDatabaseRepository;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.service.QuantityMeasurementServiceImpl;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.unit.LengthUnit;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.unit.VolumeUnit;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.unit.WeightUnit;

import java.util.logging.Logger;

public class QuantityMeasurementApp {

    private static final Logger logger = Logger.getLogger(QuantityMeasurementApp.class.getName());

    private static QuantityMeasurementApp instance;
    private final QuantityMeasurementController controller;
    private final IQuantityMeasurementRepository repository;

    private QuantityMeasurementApp() {
        this.repository = QuantityMeasurementDatabaseRepository.getInstance();
        QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(this.repository);
        this.controller = new QuantityMeasurementController(service);
        logger.info("QuantityMeasurementApp initialized with Database Repository.");
    }

    public static QuantityMeasurementApp getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementApp();
        }
        return instance;
    }

    public static void main(String[] args) {
        QuantityMeasurementController controller = getInstance().controller;

        // ----------------------------------------------------------------
        // 1. COMPARISON — 2 feet vs 24 inches (should be Equal)
        // ----------------------------------------------------------------
        QuantityDTO feet2 = QuantityDTO.of(2, LengthUnit.FEET.getUnitName(), LengthUnit.FEET.getMeasurementType());
        QuantityDTO inches24 = QuantityDTO.of(24, LengthUnit.INCHES.getUnitName(), LengthUnit.INCHES.getMeasurementType());

        boolean comparisonResult = controller.performComparison(feet2, inches24);
        logger.info("1. COMPARISON  | 2 FEET vs 24 INCHES  -> " + (comparisonResult ? "Equal" : "Not Equal"));

        // ----------------------------------------------------------------
        // 2. CONVERSION — 1 yard -> inches
        // ----------------------------------------------------------------
        QuantityDTO yard1 = QuantityDTO.of(1, LengthUnit.YARDS.getUnitName(), LengthUnit.YARDS.getMeasurementType());
        QuantityDTO inchTarget = QuantityDTO.of(0, LengthUnit.INCHES.getUnitName(), LengthUnit.INCHES.getMeasurementType());

        QuantityDTO convResult = controller.performConversion(yard1, inchTarget);
        logger.info("2. CONVERSION  | 1 YARD -> INCHES     -> " + convResult);

        // ----------------------------------------------------------------
        // 3. ADDITION — 2 feet + 2 feet (result in FEET)
        // ----------------------------------------------------------------
        QuantityDTO feet2a = QuantityDTO.of(2, LengthUnit.FEET.getUnitName(), LengthUnit.FEET.getMeasurementType());
        QuantityDTO feet2b = QuantityDTO.of(2, LengthUnit.FEET.getUnitName(), LengthUnit.FEET.getMeasurementType());

        QuantityDTO addResult = controller.performAddition(feet2a, feet2b);
        logger.info("3. ADDITION    | 2 FEET + 2 FEET      -> " + addResult);

        // ----------------------------------------------------------------
        // 4. ADDITION with target unit — 1 foot + 12 inches (result in INCHES)
        // ----------------------------------------------------------------
        QuantityDTO foot1 = QuantityDTO.of(1, LengthUnit.FEET.getUnitName(), LengthUnit.FEET.getMeasurementType());
        QuantityDTO inches12 = QuantityDTO.of(12, LengthUnit.INCHES.getUnitName(), LengthUnit.INCHES.getMeasurementType());
        QuantityDTO inchUnit = QuantityDTO.of(0, LengthUnit.INCHES.getUnitName(), LengthUnit.INCHES.getMeasurementType());

        QuantityDTO addWithTargetResult = controller.performAddition(foot1, inches12, inchUnit);
        logger.info("4. ADDITION    | 1 FOOT + 12 INCHES -> INCHES -> " + addWithTargetResult);

        // ----------------------------------------------------------------
        // 5. SUBTRACTION — 5 litres - 500 millilitres (result in LITRES)
        // ----------------------------------------------------------------
        QuantityDTO litre5 = QuantityDTO.of(5, VolumeUnit.LITRE.getUnitName(), VolumeUnit.LITRE.getMeasurementType());
        QuantityDTO ml500 = QuantityDTO.of(500, VolumeUnit.MILLILITRE.getUnitName(), VolumeUnit.MILLILITRE.getMeasurementType());

        QuantityDTO subResult = controller.performSubtraction(litre5, ml500);
        logger.info("5. SUBTRACTION | 5 LITRES - 500 ML    -> " + subResult);

        // ----------------------------------------------------------------
        // 6. SUBTRACTION with target unit — 1 kg - 500 g (result in GRAMS)
        // ----------------------------------------------------------------
        QuantityDTO kg1 = QuantityDTO.of(1, WeightUnit.KILOGRAM.getUnitName(), WeightUnit.KILOGRAM.getMeasurementType());
        QuantityDTO gram500 = QuantityDTO.of(500, WeightUnit.GRAM.getUnitName(), WeightUnit.GRAM.getMeasurementType());
        QuantityDTO gramUnit = QuantityDTO.of(0, WeightUnit.GRAM.getUnitName(), WeightUnit.GRAM.getMeasurementType());
        System.out.println("---------------------------" + gramUnit);
        QuantityDTO subWithTargetResult = controller.performSubtraction(kg1, gram500, gramUnit);
        logger.info("6. SUBTRACTION | 1 KG - 500 GRAMS -> GRAMS -> " + subWithTargetResult);

        // ----------------------------------------------------------------
        // 7. DIVISION — 10 feet / 2 feet
        // ----------------------------------------------------------------
        QuantityDTO feet10 = QuantityDTO.of(10, LengthUnit.FEET.getUnitName(), LengthUnit.FEET.getMeasurementType());
        QuantityDTO feet2c = QuantityDTO.of(2, LengthUnit.FEET.getUnitName(), LengthUnit.FEET.getMeasurementType());

        double divisionResult = controller.performDivision(feet10, feet2c);
        logger.info("7. DIVISION    | 10 FEET / 2 FEET    -> " + divisionResult);
    }
}