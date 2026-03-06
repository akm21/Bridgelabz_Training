package com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport;

public final class Quantity<U extends IMeasurable> {
    private static final double ABS_EPS_IN = 5e-5;
    private static final double REL_EPS = 1e-9;

    private double value;
    private U unit;

    public Quantity(double value, U unit) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value cannot be Null");
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
        if(targetUnit==null)
            throw new IllegalArgumentException("Target unit cannot be null");
        double baseValue = convertToBaseUnit();
        double targetValue = targetUnit.convertFromBaseUnit(baseValue);
        return new Quantity<>(targetValue, targetUnit);
    }

    public <U extends IMeasurable> Quantity<U> add(Quantity<U> other) {
        if(other==null)
            throw new IllegalArgumentException("Other Quantity cannot be null");
        return add(other, (U) this.unit);
    }

    public <U extends IMeasurable> Quantity<U> add(Quantity<U> other, U targetUnit) {
        if(other==null)
            throw new IllegalArgumentException("Other Quantity cannot be null");
        if(targetUnit==null)
            throw new IllegalArgumentException("Target unit cannot be null");
        double baseValue1 = this.convertToBaseUnit();
        double baseValue2 = other.convertToBaseUnit();
        double sumBaseValue = baseValue1 + baseValue2;
        return new Quantity<>(targetUnit.convertFromBaseUnit(sumBaseValue), targetUnit);
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
        if (!(obj instanceof Quantity))
            return false;
        Quantity other = (Quantity) obj;
        if(!unit.getClass().equals(other.unit.getClass()))
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

    public static void main(String[] args) {
        Quantity<LengthUnit> lengthInFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> lengthInInches = new Quantity<>(120.0, LengthUnit.INCHES);
        boolean isEqual = lengthInFeet.equals(lengthInInches);
        System.out.println("Are lengths equal? " + isEqual);

        Quantity<WeightUnit> weightInKilograms = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> weightInGrams = new Quantity<>(1000.0, WeightUnit.GRAM);
        isEqual = weightInKilograms.equals(weightInGrams);
        System.out.println("Are weights equal? " + isEqual);

        double convertedLength = lengthInFeet.convertTo(LengthUnit.INCHES).getValue();
        System.out.println("10 feet in inches: " + convertedLength);

        Quantity<LengthUnit> totalLength = lengthInFeet.add(lengthInInches, LengthUnit.FEET);
        System.out.println("Total Length in feet: " + totalLength.getValue() + " " + totalLength.getUnit());

        Quantity<WeightUnit> weightInPounds = new Quantity<>(2.0, WeightUnit.POUND);
        Quantity<WeightUnit> totalWeight = weightInKilograms.add(weightInPounds, WeightUnit.KILOGRAM);
        System.out.println("Total Weight in Kilograms: " + totalWeight.getValue() + " " + totalWeight.getUnit());
    }
}
