package com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.service;

import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.dto.QuantityDTO;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.entity.QuantityMeasurementEntity;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.exception.CategoryMismatchException;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.exception.DivisionByZeroException;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.exception.InvalidUnitException;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.exception.QuantityMeasurementException;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.exception.UnsupportedQuantityOperationException;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.model.QuantityModel;
import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.repository.IQuantityMeasurementRepository;

import java.util.Locale;

public class QuantityMeasurementServiceImpl implements QuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    // --------- utils ---------
    private static String norm(String s) {
        return s.trim().toUpperCase(Locale.ROOT);
    }

    private static void ensureSameCategory(QuantityDTO a, QuantityDTO b) {
        if (!a.getMeasurementType().equals(b.getMeasurementType())) {
            throw new CategoryMismatchException("Cross-category: " + a.getMeasurementType() + " vs " + b.getMeasurementType());
        }
    }

    private static void ensureFinite(double... xs) {
        for (double x : xs) if (!Double.isFinite(x)) throw new QuantityMeasurementException("Non-finite numeric input");
    }

    // --------- DTO -> Model ---------
    private QuantityModel<? extends QuantityDTO.IMeasurableUnit> getQuantityModel(QuantityDTO dto) {
        if (dto == null) throw new QuantityMeasurementException("Quantity cannot be null");
        QuantityDTO.IMeasurableUnit unit = parseUnit(dto.getMeasurementType(), dto.getUnit());
        return switch (dto.getMeasurementType()) {
            case "LengthUnit" -> new QuantityModel<>(dto.getValue(), (QuantityDTO.LengthUnit) unit);
            case "WeightUnit" -> new QuantityModel<>(dto.getValue(), (QuantityDTO.WeightUnit) unit);
            case "VolumeUnit" -> new QuantityModel<>(dto.getValue(), (QuantityDTO.VolumeUnit) unit);
            case "TemperatureUnit" -> new QuantityModel<>(dto.getValue(), (QuantityDTO.TemperatureUnit) unit);
            default ->
                    throw new QuantityMeasurementException("Unsupported measurement type: " + dto.getMeasurementType());
        };
    }

    private QuantityDTO.IMeasurableUnit parseUnit(String measurementType, String unit) {
        String mt = norm(measurementType);
        String un = norm(unit);
        try {
            return switch (mt) {
                case "LENGTHUNIT" -> QuantityDTO.LengthUnit.valueOf(un);
                case "WEIGHTUNIT" -> QuantityDTO.WeightUnit.valueOf(un);
                case "VOLUMEUNIT" -> QuantityDTO.VolumeUnit.valueOf(un);
                case "TEMPERATUREUNIT" -> QuantityDTO.TemperatureUnit.valueOf(un);
                default -> throw new InvalidUnitException("Unsupported measurement type: " + measurementType);
            };
        } catch (IllegalArgumentException e) {
            throw new InvalidUnitException("Invalid unit '" + unit + "' for type " + measurementType);
        }
    }

    // --------- validations for arithmetic ---------
    private void validateArithmeticOperands(QuantityDTO thisDTO, QuantityDTO thatDTO, QuantityDTO targetUnitDTO, boolean targetRequired) {
        if (thisDTO == null || thatDTO == null) throw new QuantityMeasurementException("Operands cannot be null");
        ensureSameCategory(thisDTO, thatDTO);
        ensureFinite(thisDTO.getValue(), thatDTO.getValue());
        if ("TemperatureUnit".equals(thisDTO.getMeasurementType()))
            throw new UnsupportedQuantityOperationException("Arithmetic not supported for Temperature");

        if (targetRequired && targetUnitDTO == null)
            throw new QuantityMeasurementException("Target unit is required");
        if (targetUnitDTO != null) ensureSameCategory(thisDTO, targetUnitDTO);
    }

    // --------- base conversions (non-temperature) ---------
    private double toBase(QuantityDTO dto) {
        ensureFinite(dto.getValue());
        String mt = dto.getMeasurementType();
        String un = norm(dto.getUnit());

        switch (mt) {
            case "LengthUnit":
                return switch (un) {
                    case "INCHES" -> dto.getValue();
                    case "FEET" -> dto.getValue() * 12.0;
                    case "YARDS" -> dto.getValue() * 36.0;
                    case "CENTIMETERS" -> dto.getValue() / 2.54;
                    default -> throw new InvalidUnitException("Invalid length unit: " + un);
                };
            case "WeightUnit":
                return switch (un) {
                    case "GRAMS" -> dto.getValue();
                    case "KILOGRAMS" -> dto.getValue() * 1000.0;
                    case "POUNDS" -> dto.getValue() * 453.59237;
                    case "OUNCES" -> dto.getValue() * 28.349523125;
                    default -> throw new InvalidUnitException("Invalid weight unit: " + un);
                };
            case "VolumeUnit":
                return switch (un) {
                    case "MILLILITERS" -> dto.getValue();
                    case "LITERS" -> dto.getValue() * 1000.0;
                    case "GALLONS" -> dto.getValue() * 3785.411784;
                    default -> throw new InvalidUnitException("Invalid volume unit: " + un);
                };
            case "TemperatureUnit":
                return toCelsius(dto.getValue(), un);
            default:
                throw new QuantityMeasurementException("Unsupported measurement type: " + mt);
        }
    }

    private double fromBaseTo(double baseValue, String targetUnit, String measurementType) {
        String un = norm(targetUnit);
        switch (measurementType) {
            case "LengthUnit":
                return switch (un) {
                    case "INCHES" -> baseValue;
                    case "FEET" -> baseValue / 12.0;
                    case "YARDS" -> baseValue / 36.0;
                    case "CENTIMETERS" -> baseValue * 2.54;
                    default -> throw new InvalidUnitException("Invalid length unit: " + un);
                };
            case "WeightUnit":
                return switch (un) {
                    case "GRAMS" -> baseValue;
                    case "KILOGRAMS" -> baseValue / 1000.0;
                    case "POUNDS" -> baseValue / 453.59237;
                    case "OUNCES" -> baseValue / 28.349523125;
                    default -> throw new InvalidUnitException("Invalid weight unit: " + un);
                };
            case "VolumeUnit":
                return switch (un) {
                    case "MILLILITERS" -> baseValue;
                    case "LITERS" -> baseValue / 1000.0;
                    case "GALLONS" -> baseValue / 3785.411784;
                    default -> throw new InvalidUnitException("Invalid volume unit: " + un);
                };
            case "TemperatureUnit":
                return fromCelsius(baseValue, un);
            default:
                throw new QuantityMeasurementException("Unsupported measurement type: " + measurementType);
        }
    }

    // --------- dedicated temperature conversion (non-linear) ---------
    private double convertTemperatureUnit(QuantityDTO thisQuantity, QuantityDTO targetUnitDTO) {
        if (!"TemperatureUnit".equals(thisQuantity.getMeasurementType()) ||
                !"TemperatureUnit".equals(targetUnitDTO.getMeasurementType()))
            throw new QuantityMeasurementException("Temperature conversion requires TemperatureUnit on both sides");

        double c = toCelsius(thisQuantity.getValue(), norm(thisQuantity.getUnit()));
        return fromCelsius(c, norm(targetUnitDTO.getUnit()));
    }

    private static double toCelsius(double value, String fromUnit) {
        return switch (fromUnit) {
            case "CELSIUS" -> value;
            case "FAHRENHEIT" -> (value - 32.0) * 5.0 / 9.0;
            case "KELVIN" -> value - 273.15;
            default -> throw new InvalidUnitException("Invalid temperature unit: " + fromUnit);
        };
    }

    private static double fromCelsius(double c, String toUnit) {
        return switch (toUnit) {
            case "CELSIUS" -> c;
            case "FAHRENHEIT" -> (c * 9.0 / 5.0) + 32.0;
            case "KELVIN" -> c + 273.15;
            default -> throw new InvalidUnitException("Invalid temperature unit: " + toUnit);
        };
    }

    private enum ArithmeticOperation {ADDITION, SUBTRACTION, DIVISION}

    private double performArithmetic(QuantityDTO a, QuantityDTO b, ArithmeticOperation op) {
        double left = toBase(a);
        double right = toBase(b);
        return switch (op) {
            case ADDITION -> left + right;
            case SUBTRACTION -> left - right;
            case DIVISION -> {
                if (right == 0.0) throw new DivisionByZeroException("Cannot divide by zero");
                yield left / right;
            }
        };
    }

    // --------- API methods ---------
    @Override
    public boolean compare(QuantityDTO a, QuantityDTO b) {
        ensureSameCategory(a, b);
        boolean equal = Double.compare(toBase(a), toBase(b)) == 0;
        repository.save(new QuantityMeasurementEntity(getQuantityModel(a), getQuantityModel(b), "COMPARE",
                equal ? "Equal" : "Not Equal"));
        return equal;
    }

    @Override
    public QuantityDTO convert(QuantityDTO input, QuantityDTO target) {
        if (!input.getMeasurementType().equals(target.getMeasurementType()))
            throw new CategoryMismatchException("Conversion requires same measurement type");

        double converted;
        try {
            converted = "TemperatureUnit".equals(input.getMeasurementType())
                    ? convertTemperatureUnit(input, target)
                    : fromBaseTo(toBase(input), target.getUnit(), target.getMeasurementType());
        } catch (InvalidUnitException | UnsupportedQuantityOperationException e) {
            throw e; // specific domain error rethrown
        } catch (Exception e) {
            throw new QuantityMeasurementException("Conversion failed: " + e.getMessage(), e);
        }

        QuantityDTO result = QuantityDTO.of(converted, target.getUnit(), target.getMeasurementType());
        repository.save(new QuantityMeasurementEntity(getQuantityModel(input), getQuantityModel(target),
                "CONVERT", getQuantityModel(result)));
        return result;
    }

    @Override
    public QuantityDTO add(QuantityDTO a, QuantityDTO b) {
        validateArithmeticOperands(a, b, null, false);
        double base = performArithmetic(a, b, ArithmeticOperation.ADDITION);
        double inFirst = fromBaseTo(base, a.getUnit(), a.getMeasurementType());
        QuantityDTO out = QuantityDTO.of(inFirst, a.getUnit(), a.getMeasurementType());
        repository.save(new QuantityMeasurementEntity(getQuantityModel(a), getQuantityModel(b), "ADD", getQuantityModel(out)));
        return out;
    }

    @Override
    public QuantityDTO add(QuantityDTO a, QuantityDTO b, QuantityDTO target) {
        validateArithmeticOperands(a, b, target, true);
        double base = performArithmetic(a, b, ArithmeticOperation.ADDITION);
        double conv = fromBaseTo(base, target.getUnit(), target.getMeasurementType());
        QuantityDTO out = QuantityDTO.of(conv, target.getUnit(), target.getMeasurementType());
        repository.save(new QuantityMeasurementEntity(getQuantityModel(a), getQuantityModel(b), "ADD", getQuantityModel(out)));
        return out;
    }

    @Override
    public QuantityDTO subtract(QuantityDTO a, QuantityDTO b) {
        validateArithmeticOperands(a, b, null, false);
        double base = performArithmetic(a, b, ArithmeticOperation.SUBTRACTION);
        double inFirst = fromBaseTo(base, a.getUnit(), a.getMeasurementType());
        QuantityDTO out = QuantityDTO.of(inFirst, a.getUnit(), a.getMeasurementType());
        repository.save(new QuantityMeasurementEntity(getQuantityModel(a), getQuantityModel(b), "SUBTRACT", getQuantityModel(out)));
        return out;
    }

    @Override
    public QuantityDTO subtract(QuantityDTO a, QuantityDTO b, QuantityDTO target) {
        validateArithmeticOperands(a, b, target, true);
        double base = performArithmetic(a, b, ArithmeticOperation.SUBTRACTION);
        double conv = fromBaseTo(base, target.getUnit(), target.getMeasurementType());
        QuantityDTO out = QuantityDTO.of(conv, target.getUnit(), target.getMeasurementType());
        repository.save(new QuantityMeasurementEntity(getQuantityModel(a), getQuantityModel(b), "SUBTRACT", getQuantityModel(out)));
        return out;
    }

    @Override
    public double divide(QuantityDTO a, QuantityDTO b) {
        validateArithmeticOperands(a, b, null, false);
        double q = performArithmetic(a, b, ArithmeticOperation.DIVISION);
        // Persist quotient as an audit record (result carries first unit)
        repository.save(new QuantityMeasurementEntity(getQuantityModel(a), getQuantityModel(b), "DIVIDE",
                getQuantityModel(QuantityDTO.of(q, a.getUnit(), a.getMeasurementType()))));
        return q;
    }
}
