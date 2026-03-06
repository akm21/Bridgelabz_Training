package com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.dto;

import java.util.Objects;

public final class QuantityDTO {

    /**
     * Marker for measurable units
     */
    public interface IMeasurableUnit {
        String getUnitName();

        String getMeasurementType();
    }

    // ---------- Length ----------
    public enum LengthUnit implements IMeasurableUnit {
        FEET, INCHES, YARDS, CENTIMETERS;

        public String getUnitName() {
            return name();
        }

        public String getMeasurementType() {
            return "LengthUnit";
        }
    }

    // ---------- Volume ----------
    public enum VolumeUnit implements IMeasurableUnit {
        LITERS, MILLILITERS, GALLONS;

        public String getUnitName() {
            return name();
        }

        public String getMeasurementType() {
            return "VolumeUnit";
        }
    }

    // ---------- Weight ----------
    public enum WeightUnit implements IMeasurableUnit {
        GRAMS, KILOGRAMS, POUNDS, OUNCES;

        public String getUnitName() {
            return name();
        }

        public String getMeasurementType() {
            return "WeightUnit";
        }
    }

    // ---------- Temperature ----------
    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS, FAHRENHEIT, KELVIN;

        public String getUnitName() {
            return name();
        }

        public String getMeasurementType() {
            return "TemperatureUnit";
        }
    }

    private final double value;
    private final String unit;             // e.g., "FEET"
    private final String measurementType;  // e.g., "LengthUnit"

    // Constructors are private to enforce immutability via factories
    private QuantityDTO(double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    public static QuantityDTO of(double value, IMeasurableUnit unit) {
        return new QuantityDTO(value, unit.getUnitName(), unit.getMeasurementType());
    }

    public static QuantityDTO of(double value, String unit, String measurementType) {
        return new QuantityDTO(value, unit, measurementType);
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }

    // Value-based equality (benefit of immutability)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantityDTO q)) return false;
        return Double.compare(q.value, value) == 0 &&
                Objects.equals(unit, q.unit) &&
                Objects.equals(measurementType, q.measurementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit, measurementType);
    }
}
