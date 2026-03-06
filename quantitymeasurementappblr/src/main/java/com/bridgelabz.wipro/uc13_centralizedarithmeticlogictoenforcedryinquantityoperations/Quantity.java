package com.bridgelabz.wipro.uc13_centralizedarithmeticlogictoenforcedryinquantityoperations;

import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.IMeasurable;

public class Quantity<U extends IMeasurable> {
    private static final double ABS_EPS_IN = 5e-5;
    private static final double REL_EPS = 1e-9;


    private double value;
    private U unit;

    public Quantity() {
    }

    public Quantity(double value, U unit) {
        /*if(!Double.isFinite(value))
            throw new IllegalArgumentException("Value cannot be null");
        if(unit==null)
            throw new IllegalArgumentException("Unit cannot be null");*/
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
        if (other == null) {
            throw new IllegalArgumentException("Other Operand cannot be null");
        }
        if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException("Incompatible measurement categories");
        }
        if (!Double.isFinite(this.value) || !Double.isFinite(other.value)) {
            throw new IllegalArgumentException("Values must be finite numbers");
        }
        if (targetUnitRequired && targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        if (targetUnitRequired && !this.unit.getClass().equals(targetUnit.getClass())) {
            throw new IllegalArgumentException("Target unit category mismatch");
        }
    }

    public <U extends IMeasurable> Quantity convertTo(U targetUnit) {
        validateArithmeticOperands(this, this.unit, true);
        double baseValue = convertToBaseUnit();
        double targetValue = targetUnit.convertFromBaseUnit(baseValue);
        return new Quantity<>(targetValue, targetUnit);
    }

    public double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {
        double thisBase = this.unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);
        return operation.compute(thisBase, otherBase);
    }

    public Quantity<U> add(Quantity<U> other) {
        validateArithmeticOperands(other, this.unit, true);
        double resultBase = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double result = this.unit.convertFromBaseUnit(resultBase);
        return new Quantity<>(result, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double resultBase = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double result = targetUnit.convertFromBaseUnit(resultBase);
        return new Quantity<>(result, targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        validateArithmeticOperands(other, this.unit, true);
        double resultBase = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double result = this.unit.convertFromBaseUnit(resultBase);
        return new Quantity<>(result, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double resultBase = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double result = targetUnit.convertFromBaseUnit(resultBase);
        return new Quantity<>(result, targetUnit);
    }

    public Quantity<U> divide(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        double resultBase = performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
        //double result=this.unit.convertFromBaseUnit(resultBase);
        return new Quantity<>(resultBase, this.unit);
    }

    private static boolean almostEqual(double a, double b) {
        double diff = Math.abs(a - b);
        double scale = Math.max(1.0, Math.max(Math.abs(a), Math.abs(b)));
        double toll = Math.max(ABS_EPS_IN, REL_EPS * scale);
        return diff <= toll;
    }

    public boolean compare(Quantity<U> that) {
        return almostEqual(this.convertToBaseUnit(), that.convertToBaseUnit());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        if (!(obj instanceof Quantity<?>))
            return false;
        Quantity other = (Quantity) obj;
        if (!unit.getClass().equals(other.unit.getClass()))
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
