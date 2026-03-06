package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class QuantityMeasurementEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;

    public double thisValue;
    public String thisUnit;
    public String thisMeasurementType;
    public double thatValue;
    public String thatUnit;
    public String thatMeasurementType;

    public String operation; // COMPARE, CONVERT, ADD, SUBTRACT, DIVIDE
    public double resultValue;
    public String resultUnit;
    public String resultMeasurementType;
    public String resultType;
    public String resultString; // for compare

    public boolean isError;
    public String errorMessage;

    private Instant createdAt;
    private Instant updatedAt;

    // No-arg constructor (required by repository)
    public QuantityMeasurementEntity() {}

    private void initCommon(QuantityModel<?> a, QuantityModel<?> b, String op) {
        this.thisValue = a.getValue();
        this.thisUnit = a.getUnit().getUnitName();
        this.thisMeasurementType = a.getUnit().getMeasurementType();
        if (b != null) {
            this.thatValue = b.getValue();
            this.thatUnit = b.getUnit().getUnitName();
            this.thatMeasurementType = b.getUnit().getMeasurementType();
        }
        this.operation = op;
    }

    public QuantityMeasurementEntity(QuantityModel<?> a, QuantityModel<?> b, String op, String compareResult) {
        initCommon(a, b, op);
        this.resultString = compareResult;
    }

    public QuantityMeasurementEntity(QuantityModel<?> a, QuantityModel<?> b, String op, QuantityModel<?> result) {
        initCommon(a, b, op);
        this.resultValue = result.getValue();
        this.resultUnit = result.getUnit().getUnitName();
        this.resultMeasurementType = result.getUnit().getMeasurementType();
        this.resultType = result.getUnit().getMeasurementType();
    }

    public QuantityMeasurementEntity(QuantityModel<?> a, QuantityModel<?> b, String op, String error, boolean isError) {
        initCommon(a, b, op);
        this.isError = isError;
        this.errorMessage = error;
    }

    // --- Getters and Setters ---

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public double getThisValue() { return thisValue; }
    public void setThisValue(double thisValue) { this.thisValue = thisValue; }

    public String getThisUnit() { return thisUnit; }
    public void setThisUnit(String thisUnit) { this.thisUnit = thisUnit; }

    public String getThisMeasurementType() { return thisMeasurementType; }
    public void setThisMeasurementType(String thisMeasurementType) { this.thisMeasurementType = thisMeasurementType; }

    public double getThatValue() { return thatValue; }
    public void setThatValue(double thatValue) { this.thatValue = thatValue; }

    public String getThatUnit() { return thatUnit; }
    public void setThatUnit(String thatUnit) { this.thatUnit = thatUnit; }

    public String getThatMeasurementType() { return thatMeasurementType; }
    public void setThatMeasurementType(String thatMeasurementType) { this.thatMeasurementType = thatMeasurementType; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public double getResultValue() { return resultValue; }
    public void setResultValue(double resultValue) { this.resultValue = resultValue; }

    public String getResultUnit() { return resultUnit; }
    public void setResultUnit(String resultUnit) { this.resultUnit = resultUnit; }

    public String getResultMeasurementType() { return resultMeasurementType; }
    public void setResultMeasurementType(String resultMeasurementType) { this.resultMeasurementType = resultMeasurementType; }

    public String getResultType() { return resultType; }
    public void setResultType(String resultType) { this.resultType = resultType; }

    public String getResultString() { return resultString; }
    public void setResultString(String resultString) { this.resultString = resultString; }

    public boolean isError() { return isError; }
    public void setError(boolean error) { isError = error; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QuantityMeasurementEntity other)) return false;
        return Double.compare(thisValue, other.thisValue) == 0 &&
                Double.compare(thatValue, other.thatValue) == 0 &&
                Double.compare(resultValue, other.resultValue) == 0 &&
                Objects.equals(thisUnit, other.thisUnit) &&
                Objects.equals(thatUnit, other.thatUnit) &&
                Objects.equals(thisMeasurementType, other.thisMeasurementType) &&
                Objects.equals(thatMeasurementType, other.thatMeasurementType) &&
                Objects.equals(resultUnit, other.resultUnit) &&
                Objects.equals(resultMeasurementType, other.resultMeasurementType) &&
                Objects.equals(resultString, other.resultString) &&
                Objects.equals(errorMessage, other.errorMessage) &&
                isError == other.isError &&
                Objects.equals(operation, other.operation);
    }

    @Override
    public String toString() {
        if (isError) return "[%s %s] ERROR: %s".formatted(thisMeasurementType, operation, errorMessage);
        if ("COMPARE".equals(operation))
            return "[%s COMPARE] %s %s vs %s %s -> %s"
                    .formatted(thisMeasurementType, thisValue, thisUnit, thatValue, thatUnit, resultString);
        return "[%s %s] %s %s & %s %s -> %s %s"
                .formatted(thisMeasurementType, operation,
                        thisValue, thisUnit, thatValue, thatUnit, resultValue, resultUnit);
    }
}

