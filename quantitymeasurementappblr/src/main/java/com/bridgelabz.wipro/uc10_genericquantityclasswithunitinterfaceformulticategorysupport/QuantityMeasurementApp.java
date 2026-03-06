package com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport;

public class QuantityMeasurementApp {

    public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> quantity1, Quantity<U> quantity2) {
        return quantity1.equals(quantity2);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit) {
        return quantity.convertTo(targetUnit);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2) {
        return quantity1.add(quantity2);
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2, U targetUnit) {
        return quantity1.add(quantity2, targetUnit);
    }

    public static void main(String[] args) {
        Quantity<WeightUnit> weightInGrams = new Quantity<>(1000.0, WeightUnit.GRAM);
        Quantity<WeightUnit> weightInKilograms = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        boolean areEqual = demonstrateEquality(weightInGrams, weightInKilograms);
        System.out.println("Are weight equal? " + areEqual);

        Quantity<WeightUnit> convertedWeight = demonstrateConversion(weightInGrams, WeightUnit.KILOGRAM);
        System.out.println("Converted Weight: " + convertedWeight.getValue() + " " + convertedWeight.getUnit());

        Quantity<WeightUnit> weightInPounds = new Quantity<>(2.20462, WeightUnit.POUND);
        Quantity<WeightUnit> sumWeight = demonstrateAddition(weightInKilograms, weightInPounds);
        System.out.println("Sum Weight: " + sumWeight.getValue() + " " + sumWeight.getUnit());

        Quantity<WeightUnit> sumWeightInGrams = demonstrateAddition(weightInKilograms, weightInPounds, WeightUnit.GRAM);
        System.out.println("Sum Weight in Grams: " + sumWeightInGrams.getValue() + " " + sumWeightInGrams.getUnit());

        //Length Operations(UC1-UC8 functionality preserved):

        System.out.println("new Quantity<>(1.0,LengthUnit.FEET).equals(new Quantity<>(12.0,LengthUnit.INCHES)) " + QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES)));
        System.out.println("new Quantity<>(1.0,LengthUnit.FEET).convertTo(LengthUnit.INCHES) " + QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.INCHES));
        System.out.println("new Quantity<>(1.0,LengthUnit.FEET).add(new Quantity<>(12.0,LengthUnit.INCHES),LengthUnit.FEET) " + QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.FEET));

        //Weight Operations(UC9 functionality preserved):

        System.out.println("Input:new Quantity<>(1.0,WeightUnit.KILOGRAM).equals(new Quantity<>(1000.0,WeightUnit.GRAM)) " + QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1.0, WeightUnit.KILOGRAM), new Quantity<>(1000.0, WeightUnit.GRAM)));
        System.out.println("Input: new Quantity<>(1.0,WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM) " + QuantityMeasurementApp.demonstrateConversion(new Quantity<>(1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM));
        System.out.println("Input: new Quantity<>(1.0,WeightUnit.KILOGRAM).add(new Quantity<>(1000.0,WeightUnit.GRAM),WeightUnit.KILOGRAM) " + QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, WeightUnit.KILOGRAM), new Quantity<>(1000.0, WeightUnit.GRAM), WeightUnit.KILOGRAM));

        //Cross-Category Prevention

        System.out.println("Input: new Quantity<>(1.0,LengthUnit.FEET).equals(new Quantity<>(1.0,WeightUnit.KILOGRAM)) " + QuantityMeasurementApp.demonstrateEquality(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(1.0, WeightUnit.KILOGRAM)));
        System.out.println("Input: demonstrateEquality(Quantity<LengthUnit>,Quantity<WeightUnit>) ");
    }
}
