package com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements;

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

    public <U extends IMeasurable> Quantity convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
        double baseValue = convertToBaseUnit();
        double targetValue = targetUnit.convertFromBaseUnit(baseValue);
        return new Quantity(targetValue, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        if (!Double.isFinite(other.value))
            throw new IllegalArgumentException("Invalid numeric value");
        if (other == null)
            throw new IllegalArgumentException("Quantity cannot be null");
        if (targetUnit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        double baseValue1 = this.convertToBaseUnit();
        double baseValue2 = other.convertToBaseUnit();
        double sumbaseValue = baseValue1 + baseValue2;
        return new Quantity<>(targetUnit.convertFromBaseUnit(sumbaseValue), targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        if (other == null)
            throw new IllegalArgumentException("Quantity cannot be null");
        if(!unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Cross-category operation not allowed");
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        if (!Double.isFinite(other.value))
            throw new IllegalArgumentException("Invalid numeric value");
        if (other == null)
            throw new IllegalArgumentException("Quantity cannot be null");
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
        double baseValue1 = this.convertToBaseUnit();
        double baseValue2 = other.convertToBaseUnit();
        double sumbaseValue = baseValue1 - baseValue2;
        return new Quantity<>(targetUnit.convertFromBaseUnit(sumbaseValue), targetUnit);
    }
    public Quantity<U> divide(Quantity<U> other){
        if(other==null)
            throw new IllegalArgumentException("Quantity cannot be null");
        if(other.getValue()==0.0)
            throw new ArithmeticException("Other Quantity should not be zero");
        if(!unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Cross-category operation not allowed");
        double baseValue1=this.convertToBaseUnit();
        double baseValue2=other.convertToBaseUnit();
        double divisonValue=baseValue1/baseValue2;
        return new Quantity<>(divisonValue,this.unit);
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
    /*private double round(double value){
        return Math.round(value*100.0)/100.0;
    }*/
}
