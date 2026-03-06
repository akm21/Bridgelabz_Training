package com.bridgelabz.wipro.uc11_volumemeasurementequalityconversionandaddition;

import com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.LengthUnit;
import com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.WeightUnit;

public class QuantityMeasurementApp {
    public static <U extends IMeasurable>boolean demonstrateEquality(Quantity<U> quantity1,Quantity<U> quantity2){
        return quantity1.equals(quantity2);
    }
    public static <U extends IMeasurable>Quantity<U> demonstrateConversion(Quantity<U> quantity,U targetUnit){
        return quantity.convertTo(targetUnit);
    }
    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1,Quantity<U> quantity2){
        return quantity1.add(quantity2);
    }
    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1,Quantity<U> quantity2,U targetUnit){
        return quantity1.add(quantity2,targetUnit);
    }
    public static void main(String[] args){
        //Equality Comparisons
        System.out.println("Input: new Quantity<>(1.0,LITRE).equals(new Quantity<>(1.0,LITRE)) "+demonstrateEquality(new Quantity<>(1.0,VolumeUnit.LITRE),new Quantity<>(1.0,VolumeUnit.LITRE)));
        System.out.println("Input: new Quantity<>(1.0,LITRE).equals(new Quantity<>(1000.0,MILLILITRE)) "+demonstrateEquality(new Quantity<>(1.0,VolumeUnit.LITRE),new Quantity<>(1000.0,VolumeUnit.MILLILITRE)));
        System.out.println("Input: new Quantity<>(1.0,GALLON).equals(new Quantity<>(1.0,GALLON)) "+demonstrateEquality(new Quantity<>(1.0,VolumeUnit.GALLON),new Quantity<>(1.0,VolumeUnit.GALLON)));
        System.out.println("Input: new Quantity<>(1.0,LITRE).equals(new Quantity<>(~0.264172,GALLON)) "+demonstrateEquality(new Quantity<>(1.0,VolumeUnit.LITRE),new Quantity<>(0.264172,VolumeUnit.GALLON)));
        System.out.println("Input: new Quantity<>(500.0,MILLILITRE).equals(new Quantity<>(0.5,LITRE)) "+demonstrateEquality(new Quantity<>(500.0,VolumeUnit.MILLILITRE),new Quantity<>(0.5,VolumeUnit.LITRE)));
        System.out.println("Input: new Quantity<>(3.78541,LITRE).equals(new Quantity<>(1.0,GALLON)) "+demonstrateEquality(new Quantity<>(3.78541,VolumeUnit.LITRE),new Quantity<>(1.0,VolumeUnit.GALLON)));

        //Unit Conversion

        System.out.println("Input: new Quantity<>(1.0,LITRE).convertTo(MILLILITRE) "+demonstrateConversion(new Quantity<>(1.0,VolumeUnit.LITRE),VolumeUnit.MILLILITRE));
        System.out.println("Input: new Quantity<>(2.0,GALLON).convertTo(LITRE) "+demonstrateConversion(new Quantity<>(2.0,VolumeUnit.GALLON),VolumeUnit.LITRE));
        System.out.println("Input: new Quantity<>(500.0,MILLILITRE).convertTo(GALLON) "+demonstrateConversion(new Quantity<>(500.0,VolumeUnit.MILLILITRE),VolumeUnit.GALLON));
        System.out.println("Input: new Quantity<>(0.0,LITRE).convertTo(MILLILITRE) "+demonstrateConversion(new Quantity<>(0.0,VolumeUnit.LITRE),VolumeUnit.MILLILITRE));
        System.out.println("Input: new Quantity<>(1.0,LITRE).convertTo(LITRE) "+demonstrateConversion(new Quantity<>(1.0,VolumeUnit.LITRE),VolumeUnit.LITRE));

        //Addition Operations (Implicit Target Unit)

        System.out.println("Input: new Quantity<>(1.0,LITRE).add(new Quantity<>(2.0,LITRE)) "+demonstrateAddition(new Quantity<>(1.0,VolumeUnit.LITRE),new Quantity<>(2.0,VolumeUnit.LITRE)));
        System.out.println("Input: new Quantity<>(1.0,LITRE).add(new Quantity<>(1000.0,MILLILITRE)) "+demonstrateAddition(new Quantity<>(1.0,VolumeUnit.LITRE),new Quantity<>(1000.0,VolumeUnit.MILLILITRE)));
        System.out.println("Input: new Quantity<>(500.0,MILLILITRE).add(new Quantity<>(0.5,LITRE)) "+demonstrateAddition(new Quantity<>(500.0,VolumeUnit.MILLILITRE),new Quantity<>(0.5,VolumeUnit.LITRE)));
        System.out.println("Input: new Quantity<>(2.0,GALLON).add(new Quantity<>(3.78541,LITRE)) "+demonstrateAddition(new Quantity<>(2.0,VolumeUnit.GALLON),new Quantity<>(3.78541,VolumeUnit.LITRE)));

        //Addition Operations(Explicit Target Unit)

        System.out.println("Input: new Quantity<>(1.0,LITRE).add(new Quantity<>(1000.0,MILLILITRE),MILLILITRE) "+demonstrateAddition(new Quantity<>(1.0,VolumeUnit.LITRE),new Quantity<>(1000.0,VolumeUnit.MILLILITRE),VolumeUnit.MILLILITRE));
        System.out.println("Input: new Quantity<>(1.0,GALLON).add(new Quantity<>(3.78541,LITRE),GALLON) "+demonstrateAddition(new Quantity<>(1.0,VolumeUnit.GALLON),new Quantity<>(3.78541,VolumeUnit.LITRE),VolumeUnit.GALLON));
        System.out.println("Input: new Quantity<>(500.0,MILLILITRE).add(new Quantity<>(1.0,LITRE),GALLON) "+demonstrateAddition(new Quantity<>(500.0,VolumeUnit.MILLILITRE),new Quantity<>(1.0,VolumeUnit.LITRE),VolumeUnit.GALLON));
        System.out.println("Input: new Quantity<>(2.0,LITRE).add(new Quantity<>(4.0,GALLON),LITRE) "+demonstrateAddition(new Quantity<>(2.0,VolumeUnit.LITRE),new Quantity<>(4.0,VolumeUnit.GALLON),VolumeUnit.LITRE));

        //Category Incompatibility

        Quantity litre=new Quantity<>(1.0,VolumeUnit.LITRE);
        com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity length=new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(1.0, LengthUnit.FEET);
        System.out.println("Input: new Quantity<>(1.0,LITRE).equals(new Quantity<>(1.0,FOOT)) "+litre.equals(length));

        com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity weight=new com.bridgelabz.wipro.uc10_genericquantityclasswithunitinterfaceformulticategorysupport.Quantity<>(1.0, WeightUnit.KILOGRAM);
        System.out.println("Input: new Quantity<>(1.0,LITRE).equals(new Quantity<>(1.0,KILOGRAM)) "+litre.equals(weight));
    }
}
