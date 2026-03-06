package com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport;

public enum TestTimeUnit implements IMeasurable{
    SECOND(1.0);

    public final double conversionFactor;

    TestTimeUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value*conversionFactor;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return baseValue/conversionFactor;
    }

    @Override
    public String getUnitName() {
        return name();
    }
}
