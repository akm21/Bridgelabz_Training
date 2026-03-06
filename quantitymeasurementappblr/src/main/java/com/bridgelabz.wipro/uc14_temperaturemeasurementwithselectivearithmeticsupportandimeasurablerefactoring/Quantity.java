package com.bridgelabz.wipro.uc14_temperaturemeasurementwithselectivearithmeticsupportandimeasurablerefactoring;

import com.bridgelabz.wipro.uc13_centralizedarithmeticlogictoenforcedryinquantityoperations.ArithmeticOperation;

public class Quantity<U extends IMeasurable> {
    private static final double ABS_EPS_IN = 5e-5;
    private static final double REL_EPS = 1e-9;

    private double value;
    private U unit;

    public Quantity(double value, U unit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value cannot be null");
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    public double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public double convertFromBaseUnit(double baseValue) {
        return unit.convertFromBaseUnit(baseValue);
    }

    private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetUnitRequired) {
        if (other == null)
            throw new IllegalArgumentException("Other Operand cannot be null");
        if (!this.unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Incompatible measurement categories");
        if (!Double.isFinite(this.value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Values must be finite numbers");
        if (targetUnitRequired && targetUnit == null)
            throw new IllegalArgumentException("Target Unit cannot be null");
        if (targetUnitRequired && !this.unit.getClass().equals(targetUnit.getClass()))
            throw new IllegalArgumentException("Target unit category mismatch");
    }

    public <U extends IMeasurable> Quantity convertTo(U targetUnit) {
        validateArithmeticOperands(this, this.unit, true);
        double basevalue = convertToBaseUnit();
        double targetvalue = targetUnit.convertFromBaseUnit(basevalue);
        return new Quantity(targetvalue, targetUnit);
    }

    public double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {
        validateArithmeticOperands(other,this.unit,false);
        unit.validateOperationSupport(operation.name());
        double thisvalue = this.unit.convertToBaseUnit(this.value);
        double othervalue = other.unit.convertToBaseUnit(other.value);
        return operation.compute(thisvalue, othervalue);
    }

    public Quantity<U> Add(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        double sumBaseValue = performBaseArithmetic(other, ArithmeticOperation.ADD);
        return new Quantity<>(convertFromBaseUnit(sumBaseValue), this.unit);
    }

    public Quantity<U> Add(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double sumBaseValue = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double targetValue = targetUnit.convertFromBaseUnit(sumBaseValue);
        return new Quantity<>(targetValue, targetUnit);
    }

    public Quantity<U> Subtract(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        double diffBaseValue = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        return new Quantity<>(convertFromBaseUnit(diffBaseValue), this.unit);
    }
    public Quantity<U> Subtract(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double diffBaseValue = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double targetValue = targetUnit.convertFromBaseUnit(diffBaseValue);
        return new Quantity<>(targetValue, targetUnit);
    }
    public Quantity<U> Divide(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        if (other.value == 0)
            throw new IllegalArgumentException("Cannot divide by zero");
        double quotientBaseValue = performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
        return new Quantity<>(convertFromBaseUnit(quotientBaseValue), this.unit);
    }

    private static boolean almostEqual(double a, double b) {
        double diff = Math.abs(a - b);
        double scale = Math.max(1.0, Math.max(Math.abs(a), Math.abs(b)));
        double toll = Math.max(ABS_EPS_IN, REL_EPS * scale);
        return diff <= toll;
    }

    public boolean compare(Quantity that) {
        return almostEqual(this.convertToBaseUnit(), that.convertToBaseUnit());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        if(!(obj instanceof Quantity<?>))
            return false;
        Quantity<?> other = (Quantity<?>) obj;
        if (!this.unit.getClass().equals(other.unit.getClass()))
            return false;
        return compare(other);
    }

    @Override
    public int hashCode() {
        double quantized = Math.round(this.convertToBaseUnit() / ABS_EPS_IN) * ABS_EPS_IN;
        return Double.hashCode(quantized);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
    private double round(double value){
        return Math.round(value*100.0)/100.0;
    }
}
