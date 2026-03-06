package com.bridgelabz.wipro.uc14_temperaturemeasurementwithselectivearithmeticsupportandimeasurablerefactoring;

import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.LengthUnit;
import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.WeigthUnit;

public class QuantityMeasurementApp {
    public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> quantity1, Quantity<U> quantity2) {
        return quantity1.equals(quantity2);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit) {
        return quantity.convertTo(targetUnit);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2) {
        return quantity1.Add(quantity2);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2, U targetUnit) {
        return quantity1.Add(quantity2, targetUnit);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> quantity1, Quantity<U> quantity2) {
        return quantity1.Subtract(quantity2);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> quantity1, Quantity<U> quantity2, U targetUnit) {
        return quantity1.Subtract(quantity2, targetUnit);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateDivision(Quantity<U> quantity1, Quantity<U> quantity2) {
        return quantity1.Divide(quantity2);
    }

    public static void main(String[] args) {
        //Temperature Equality Comparisons:
        System.out.println("Input: new Quantity<>(0.0,CELSIUS).equals(new Quantity<>(32.0,FAHRENHEIT)) " + demonstrateEquality(new Quantity<>(0.0, TemperatureUnit.CELSIUS), new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)));
        System.out.println("Input: new Quantity<>(273.15,KELVIN).equals(new Quantity<>(0.0,CELSIUS)) " + demonstrateEquality(new Quantity<>(273.15, TemperatureUnit.KELVIN), new Quantity<>(0.0, TemperatureUnit.CELSIUS)));
        System.out.println("Input: new Quantity<>(212.0,FAHRENHEIT).equals(new Quantity<>(100.0,CELSIUS)) " + demonstrateEquality(new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT), new Quantity<>(100.0, TemperatureUnit.CELSIUS)));
        System.out.println("Input: new Quantity<>(100.0,CELSIUS).equals(new Quantity<>(373.15,KELVIN)) " + demonstrateEquality(new Quantity<>(100.0, TemperatureUnit.CELSIUS), new Quantity<>(373.15, TemperatureUnit.KELVIN)));
        System.out.println("Input: new Quantity<>(50.0,CELSIUS).equals(new Quantity<>(122.0,FAHRENHEIT)) " + demonstrateEquality(new Quantity<>(50.0, TemperatureUnit.CELSIUS), new Quantity<>(122.0, TemperatureUnit.FAHRENHEIT)));

        //Temperature Conversions:
        System.out.println("Input: new Quantity<>(100.0,CELSIUS).convertTo(FAHRENHEIT) " + demonstrateConversion(new Quantity<>(100.0, TemperatureUnit.CELSIUS), TemperatureUnit.FAHRENHEIT));
        System.out.println("Input: new Quantity<>(32.0,FAHRENHEIT).convertTo(CELSIUS) " + demonstrateConversion(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT), TemperatureUnit.CELSIUS));
        System.out.println("Input: new Quantity<>(273.15,KELVIN).convertTo(CELSIUS) " + demonstrateConversion(new Quantity<>(273.15, TemperatureUnit.KELVIN), TemperatureUnit.CELSIUS));
        System.out.println("Input: new Quantity<>(0.0,CELSIUS).convertTo(KELVIN) " + demonstrateConversion(new Quantity<>(0.0, TemperatureUnit.CELSIUS), TemperatureUnit.KELVIN));
        System.out.println("Input: new Quantity<>(-40.0,CELSIUS).convertTo(FAHRENHEIT) " + demonstrateConversion(new Quantity<>(-40.0, TemperatureUnit.CELSIUS), TemperatureUnit.FAHRENHEIT));

        //Unsupported Operations(Error Handling):
        try {
            System.out.println("Input: new Quantity<>(100.0,CELSIUS).add(new Quantity<>(50.0,CELSIUS)) " + demonstrateAddition(new Quantity<>(100.0, TemperatureUnit.CELSIUS), new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        }
        try {
            System.out.println("Input: new Quantity<>(100.0,CELSIUS).subtract(new Quantity<>(50.0,CELSIUS)) " + demonstrateSubtraction(new Quantity<>(100.0, TemperatureUnit.CELSIUS), new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        }
        try {
            System.out.println("Input: new Quantity<>(100.0,CELSIUS).divide(new Quantity<>(50.0,CELSIUS)) " + demonstrateDivision(new Quantity<>(100.0, TemperatureUnit.CELSIUS), new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        }
        //Cross-Category Comparisons:
        System.out.println("Input: new Quantity<>(100.0,CELSIUS).equals(new Quantity<>(100.0,LengthUnit.FEET)) " + demonstrateEquality(new Quantity<>(100.0, TemperatureUnit.CELSIUS), new Quantity<>(100.0, LengthUnit.FEET)));
        //Temperature Comparisons with Other Categories:
        System.out.println("Input: new Quantity<>(50.0,CELSIUS).equals(new Quantity<>(50.0,WeightUnit.KILOGRAM)) " + demonstrateEquality(new Quantity<>(50.0, TemperatureUnit.CELSIUS), new Quantity<>(50.0, WeigthUnit.KILOGRAM)));
    }
}