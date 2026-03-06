package com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple;

public class QuantityLength {
    private final double value;
    private final com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    private double valueInFeet() {
        return unit.toFeet(value);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityLength other = (QuantityLength) obj;

        return Double.compare(this.valueInFeet(), other.valueInFeet()) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(valueInFeet());
    }
}
