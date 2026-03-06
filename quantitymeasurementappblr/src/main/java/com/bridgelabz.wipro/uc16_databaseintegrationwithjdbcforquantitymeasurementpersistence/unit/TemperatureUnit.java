package com.bridgelabz.wipro.uc16_databaseintegrationwithjdbcforquantitymeasurementpersistence.unit;


import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable,IMeasurableUnit {

    CELSIUS(false, celsius -> celsius),
    FAHRENHEIT(true, fahrenheit -> (fahrenheit - 32) * 5 / 9),
    KELVIN(false, kelvin -> kelvin - 273.15);

    boolean isFahrenheit;
    Function<Double, Double> conversionValue;

    public Function<Double, Double> getConversionValue() {
        return conversionValue;
    }

    private final SupportsArithmetic supportsArithmetic = () -> false;

    TemperatureUnit(boolean isFahrenheit, Function<Double, Double> conversionValue) {
        this.isFahrenheit = isFahrenheit;
        this.conversionValue = conversionValue;
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public String getMeasurementType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public double getConversionFactor() {
        return 1; // Not used for temperature, as conversion is not linear
    }

    @Override
    public double convertToBaseUnit(double value) {
        return conversionValue.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        if (this == CELSIUS) {
            return baseValue;
        }
        if (this == FAHRENHEIT) {
            return (baseValue * 9 / 5) + 32;
        }
        return baseValue + 273.15;
    }

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        if (!supportsArithmetic.isSupported()) {
            throw new UnsupportedOperationException("Temperature does not support " + operation + " operation.");
        }
    }
}
