package com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements;

public enum LengthUnit implements IMeasurable, com.bridgelabz.wipro.uc14_temperaturemeasurementwithselectivearithmeticsupportandimeasurablerefactoring.IMeasurable {
    FEET(1.0),
    INCHES(1.0/12.0),
    YARDS(3.0),
    CENTIMETERS(1.0/30.48);

    private final double conversionFactor;


    LengthUnit(double conversionFactor) {
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
