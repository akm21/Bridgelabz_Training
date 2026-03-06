package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.service;

import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.entity.QuantityDTO;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.entity.QuantityMeasurementEntity;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.entity.QuantityModel;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.exception.QuantityMeasurementException;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.repository.IQuantityMeasurementRepository;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.unit.IMeasurable;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.unit.LengthUnit;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.unit.TemperatureUnit;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.unit.VolumeUnit;
import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.unit.WeightUnit;

import java.util.Locale;
import java.util.logging.Logger;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementServiceImpl.class.getName());

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        if (repository == null) throw new IllegalArgumentException("Repository must not be null");
        this.repository = repository;
    }

    // ----------------------------------------------------------------
    // IComparisonService
    // ----------------------------------------------------------------

    @Override
    public boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        validateNotNull(thisQuantityDTO, "thisQuantityDTO");
        validateNotNull(thatQuantityDTO, "thatQuantityDTO");
        ensureSameCategory(thisQuantityDTO, thatQuantityDTO);

        double baseA = toBase(thisQuantityDTO);
        double baseB = toBase(thatQuantityDTO);
        boolean equal = Double.compare(baseA, baseB) == 0;

        String compareResult = equal ? "Equal" : "Not Equal";
        logger.info("COMPARE %s vs %s -> %s".formatted(thisQuantityDTO, thatQuantityDTO, compareResult));

        persist(new QuantityMeasurementEntity(
                toModel(thisQuantityDTO), toModel(thatQuantityDTO), "COMPARE", compareResult));
        return equal;
    }

    // ----------------------------------------------------------------
    // IConversionService
    // ----------------------------------------------------------------

    @Override
    public QuantityDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO targetUnitDTO) {
        validateNotNull(thisQuantityDTO, "thisQuantityDTO");
        validateNotNull(targetUnitDTO, "targetUnitDTO");
        ensureSameCategory(thisQuantityDTO, targetUnitDTO);

        double converted;
        if ("TemperatureUnit".equals(thisQuantityDTO.getMeasurementType())) {
            converted = convertTemperature(thisQuantityDTO.getValue(),
                    norm(thisQuantityDTO.getUnit()), norm(targetUnitDTO.getUnit()));
        } else {
            IMeasurable sourceUnit = resolveUnit(thisQuantityDTO);
            IMeasurable targetUnit = resolveUnit(targetUnitDTO);
            converted = targetUnit.convertFromBaseUnit(sourceUnit.convertToBaseUnit(thisQuantityDTO.getValue()));
        }

        QuantityDTO result = QuantityDTO.of(converted, targetUnitDTO.getUnit(), targetUnitDTO.getMeasurementType());
        logger.info("CONVERT %s -> %s".formatted(thisQuantityDTO, result));

        persist(new QuantityMeasurementEntity(
                toModel(thisQuantityDTO), toModel(targetUnitDTO), "CONVERT", toModel(result)));
        return result;
    }

    // ----------------------------------------------------------------
    // IArithmeticService
    // ----------------------------------------------------------------

    @Override
    public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        validateArithmetic(thisQuantityDTO, thatQuantityDTO);
        double base = toBase(thisQuantityDTO) + toBase(thatQuantityDTO);
        return buildArithmeticResult(thisQuantityDTO, thatQuantityDTO, base, thisQuantityDTO, "ADD");
    }

    @Override
    public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
        validateArithmetic(thisQuantityDTO, thatQuantityDTO);
        validateNotNull(targetUnitDTO, "targetUnitDTO");
        ensureSameCategory(thisQuantityDTO, targetUnitDTO);
        double base = toBase(thisQuantityDTO) + toBase(thatQuantityDTO);
        return buildArithmeticResult(thisQuantityDTO, thatQuantityDTO, base, targetUnitDTO, "ADD");
    }

    @Override
    public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        validateArithmetic(thisQuantityDTO, thatQuantityDTO);
        double base = toBase(thisQuantityDTO) - toBase(thatQuantityDTO);
        return buildArithmeticResult(thisQuantityDTO, thatQuantityDTO, base, thisQuantityDTO, "SUBTRACT");
    }

    @Override
    public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) {
        validateArithmetic(thisQuantityDTO, thatQuantityDTO);
        validateNotNull(targetUnitDTO, "targetUnitDTO");
        ensureSameCategory(thisQuantityDTO, targetUnitDTO);
        double base = toBase(thisQuantityDTO) - toBase(thatQuantityDTO);
        return buildArithmeticResult(thisQuantityDTO, thatQuantityDTO, base, targetUnitDTO, "SUBTRACT");
    }

    @Override
    public double divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        validateArithmetic(thisQuantityDTO, thatQuantityDTO);
        double denominator = toBase(thatQuantityDTO);
        if (denominator == 0.0)
            throw new QuantityMeasurementException("Cannot divide by zero");

        double quotient = toBase(thisQuantityDTO) / denominator;
        logger.info("DIVIDE %s / %s -> %f".formatted(thisQuantityDTO, thatQuantityDTO, quotient));

        // Persist quotient expressed in the unit of the first operand
        QuantityDTO resultDTO = QuantityDTO.of(quotient, thisQuantityDTO.getUnit(), thisQuantityDTO.getMeasurementType());
        persist(new QuantityMeasurementEntity(
                toModel(thisQuantityDTO), toModel(thatQuantityDTO), "DIVIDE", toModel(resultDTO)));
        return quotient;
    }

    // ----------------------------------------------------------------
    // Private helpers
    // ----------------------------------------------------------------

    /**
     * Resolves a QuantityDTO's unit string to the matching IMeasurable enum constant.
     */
    private IMeasurable resolveUnit(QuantityDTO dto) {
        String mt = norm(dto.getMeasurementType());
        String un = norm(dto.getUnit());
        try {
            return switch (mt) {
                case "LENGTHUNIT" -> LengthUnit.valueOf(un);
                case "WEIGHTUNIT" -> WeightUnit.valueOf(un);
                case "VOLUMEUNIT" -> VolumeUnit.valueOf(un);
                case "TEMPERATUREUNIT" -> TemperatureUnit.valueOf(un);
                default -> throw new QuantityMeasurementException(
                        "Unsupported measurement type: " + dto.getMeasurementType());
            };
        } catch (IllegalArgumentException e) {
            throw new QuantityMeasurementException(
                    "Invalid unit '" + dto.getUnit() + "' for type " + dto.getMeasurementType());
        }
    }

    /**
     * Converts a QuantityDTO value to its base unit (using IMeasurable).
     */
    private double toBase(QuantityDTO dto) {
        ensureFinite(dto.getValue());
        if ("TemperatureUnit".equals(dto.getMeasurementType())) {
            TemperatureUnit unit = (TemperatureUnit) resolveUnit(dto);
            return unit.convertToBaseUnit(dto.getValue()); // base = Celsius
        }
        return resolveUnit(dto).convertToBaseUnit(dto.getValue());
    }

    /**
     * Converts a base-unit value back into a QuantityDTO for the given target.
     */
    private QuantityDTO fromBase(double baseValue, QuantityDTO targetUnitDTO) {
        IMeasurable targetUnit = resolveUnit(targetUnitDTO);
        double value = targetUnit.convertFromBaseUnit(baseValue);
        return QuantityDTO.of(value, targetUnitDTO.getUnit(), targetUnitDTO.getMeasurementType());
    }

    /**
     * Temperature conversion via Celsius as the intermediate base.
     */
    private double convertTemperature(double value, String fromUnit, String toUnit) {
        double celsius = switch (fromUnit) {
            case "CELSIUS" -> value;
            case "FAHRENHEIT" -> (value - 32.0) * 5.0 / 9.0;
            case "KELVIN" -> value - 273.15;
            default -> throw new QuantityMeasurementException("Invalid temperature unit: " + fromUnit);
        };
        return switch (toUnit) {
            case "CELSIUS" -> celsius;
            case "FAHRENHEIT" -> (celsius * 9.0 / 5.0) + 32.0;
            case "KELVIN" -> celsius + 273.15;
            default -> throw new QuantityMeasurementException("Invalid temperature unit: " + toUnit);
        };
    }

    /**
     * Wraps an arithmetic result into a QuantityDTO, persists the entity and returns it.
     */
    private QuantityDTO buildArithmeticResult(QuantityDTO a, QuantityDTO b,
                                              double baseResult, QuantityDTO targetUnitDTO,
                                              String operation) {
        QuantityDTO result = fromBase(baseResult, targetUnitDTO);
        logger.info("%s %s + %s -> %s".formatted(operation, a, b, result));
        persist(new QuantityMeasurementEntity(
                toModel(a), toModel(b), operation, toModel(result)));
        return result;
    }

    /**
     * Builds a QuantityModel from a QuantityDTO (used for entity construction).
     */
    @SuppressWarnings("unchecked")
    private <U extends IMeasurable> QuantityModel<U> toModel(QuantityDTO dto) {
        U unit = (U) resolveUnit(dto);
        return new QuantityModel<>(dto.getValue(), unit);
    }

    private void persist(QuantityMeasurementEntity entity) {
        try {
            repository.save(entity);
        } catch (Exception e) {
            // Log but do not fail the operation if persistence is unavailable
            logger.warning("Failed to persist entity: " + e.getMessage());
        }
    }

    private static void validateNotNull(Object obj, String name) {
        if (obj == null) throw new QuantityMeasurementException(name + " must not be null");
    }

    private static void ensureSameCategory(QuantityDTO a, QuantityDTO b) {
        if (!a.getMeasurementType().equals(b.getMeasurementType()))
            throw new QuantityMeasurementException(
                    "Category mismatch: " + a.getMeasurementType() + " vs " + b.getMeasurementType());
    }

    private static void validateArithmetic(QuantityDTO a, QuantityDTO b) {
        validateNotNull(a, "thisQuantityDTO");
        validateNotNull(b, "thatQuantityDTO");
        ensureSameCategory(a, b);
        ensureFinite(a.getValue(), b.getValue());
        if ("TemperatureUnit".equals(a.getMeasurementType()))
            throw new QuantityMeasurementException("Arithmetic operations are not supported for TemperatureUnit");
    }

    private static void ensureFinite(double... values) {
        for (double v : values)
            if (!Double.isFinite(v))
                throw new QuantityMeasurementException("Non-finite numeric input: " + v);
    }

    private static String norm(String s) {
        return s == null ? "" : s.trim().toUpperCase(Locale.ROOT);
    }
}
