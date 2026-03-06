package com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.dto;

public final class QuantityDTOv2 {
    private final double value;
    private final String unit;
    private final String measurementType;
    private final long timestamp;

    private QuantityDTOv2(double value, String unit, String measurementType, long timestamp) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
        this.timestamp = timestamp;
    }

    public static QuantityDTOv2 fromV1(QuantityDTO v1, long timestamp) {
        return new QuantityDTOv2(v1.getValue(), v1.getUnit(), v1.getMeasurementType(), timestamp);
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

    public long getTimestamp() {
        return timestamp;
    }
}
