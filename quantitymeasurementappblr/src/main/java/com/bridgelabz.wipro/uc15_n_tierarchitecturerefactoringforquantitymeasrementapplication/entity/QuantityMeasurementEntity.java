package com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.entity;

import com.bridgelabz.wipro.uc15_n_tierarchitecturerefactoringforquantitymeasrementapplication.model.QuantityModel;

import java.io.Serializable;
import java.util.Objects;

public class QuantityMeasurementEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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
    public String resultString; // for compare

    public boolean isError;
    public String errorMessage;

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
    }

    public QuantityMeasurementEntity(QuantityModel<?> a, QuantityModel<?> b, String op, String error, boolean isError) {
        initCommon(a, b, op);
        this.isError = isError;
        this.errorMessage = error;
    }

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
