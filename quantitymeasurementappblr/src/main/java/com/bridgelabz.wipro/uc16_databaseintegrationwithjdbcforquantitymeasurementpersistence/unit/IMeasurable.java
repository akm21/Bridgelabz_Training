package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.unit;

public interface IMeasurable extends IMeasurableUnit {

    SupportsArithmetic supportsArithmetic=()-> true;

    public String getUnitName();

    double getConversionFactor();

    public double convertToBaseUnit(double value);

    public double convertFromBaseUnit(double baseValue);

    default boolean supportsArithmetic(){
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation){

    }
}
