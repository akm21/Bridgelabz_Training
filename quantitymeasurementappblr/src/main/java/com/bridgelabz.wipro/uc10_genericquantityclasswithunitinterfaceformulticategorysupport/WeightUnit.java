package com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport;

public enum WeightUnit implements IMeasurable{
    MILLIGRAM(0.001),
    GRAM(1.0),
    KILOGRAM(1000.0),
    POUND(453.592),
    TONNE(1000000.0);

    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    public double convertToBaseUnit(double value){
        return value*conversionFactor;
    }
    public double convertFromBaseUnit(double baseValue){
        return baseValue/conversionFactor;
    }

    @Override
    public String getUnitName() {
        return name();
    }
}
