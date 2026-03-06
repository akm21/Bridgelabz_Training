package com.bridgelabz.wipro.uc14_temperaturemeasurementwithselectivearithmeticsupportandimeasurablerefactoring;

import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.LengthUnit;
import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.VolumeUnit;
import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.WeigthUnit;
public interface IMeasurable {

    SupportsArithmetic supportsArithmetic=()-> true;

    static IMeasurable getUnitInstance(String unitName) {

        if (unitName == null || unitName.isBlank()) {
            throw new IllegalArgumentException("Unit name cannot be null or empty");
        }

        String normalized = unitName.trim().toUpperCase();

        // Try LengthUnit
        try {
            return LengthUnit.valueOf(normalized);
        } catch (IllegalArgumentException ignored) {}

        // Try WeightUnit
        try {
            return WeigthUnit.valueOf(normalized);
        } catch (IllegalArgumentException ignored) {}

        //Try VolumeUnit
        try {
            return VolumeUnit.valueOf(normalized);
        } catch (IllegalArgumentException ignored) {}
        // Try TemperatureUnit
        try {
            return TemperatureUnit.valueOf(normalized);
        } catch (IllegalArgumentException ignored) {}

        throw new IllegalArgumentException("Invalid unit: " + unitName);
    }

    public String getUnitName();

    public double convertToBaseUnit(double value);

    public double convertFromBaseUnit(double baseValue);

    default boolean supportsArithmetic(){
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation){

    }
}
