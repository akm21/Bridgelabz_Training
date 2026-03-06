package com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport;

public interface IMeasurable {
    double getConversionFactor();

    double convertToBaseUnit(double value);

    public double convertFromBaseUnit(double baseValue);

    String getUnitName();

}
