package com.bridgelabz.wipro.uc9_weightmeasurement;

public enum WeightUnit {
    MILLIGRAM(0.001),
    GRAM(1.0),
    KILOGRAM(1000.0),
    POUND(453.592),
    TONNE(1000000.0);

 private final double toGramFactor;

    WeightUnit(double toGramFactor) {
        this.toGramFactor = toGramFactor;
    }

    public double getToGramFactor() {
        return toGramFactor;
    }

    public double convertToBaseUnit(double value){
        return value*toGramFactor;
    }
    public double convertFromBaseUnit(double basevalue){
        return Math.round((basevalue/toGramFactor)*100.0)/100.0;
    }

    public static void main(String[] args){
        double kilograms=10.0;
        double grams=WeightUnit.KILOGRAM.convertToBaseUnit(kilograms);
        System.out.println(kilograms+" kilograms is "+grams+" grams.");

        double milligrams=WeightUnit.MILLIGRAM.convertFromBaseUnit(grams);
        System.out.println(grams+" gram is "+milligrams+" milligrams");
    }
}
