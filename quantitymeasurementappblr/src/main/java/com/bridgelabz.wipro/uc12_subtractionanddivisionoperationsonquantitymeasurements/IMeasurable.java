package com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements;

public interface IMeasurable {
    public double getConversionFactor();

    public double convertToBaseUnit(double value);

    public double convertFromBaseUnit(double baseValue);

    String getUnitName();
}
