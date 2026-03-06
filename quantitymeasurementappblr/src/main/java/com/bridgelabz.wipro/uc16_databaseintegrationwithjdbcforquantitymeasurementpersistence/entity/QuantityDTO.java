package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.entity;

import com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.unit.IMeasurable;

import java.util.Objects;

public class QuantityDTO {
    private final double value;
    private final String unit;             // e.g., "FEET"
    private final String measurementType;  // e.g., "LengthUnit"

    // Constructors are private to enforce immutability via factories
    private QuantityDTO(double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    public static QuantityDTO of(double value, QuantityDTO unit) {
        return new QuantityDTO(value, unit.getUnit(), unit.getMeasurementType());
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
