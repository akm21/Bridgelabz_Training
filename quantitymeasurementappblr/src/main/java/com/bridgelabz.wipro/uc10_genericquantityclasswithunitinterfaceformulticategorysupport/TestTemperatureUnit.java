package com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport;

public enum TestTemperatureUnit implements IMeasurable{
    CELSIUS(1.0);

    private final double conversionFactor;

    TestTemperatureUnit(double conversionFactor) {
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
