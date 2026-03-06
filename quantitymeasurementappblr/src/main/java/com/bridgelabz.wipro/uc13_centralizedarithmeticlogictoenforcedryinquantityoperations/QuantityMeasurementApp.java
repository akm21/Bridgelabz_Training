package com.bridgelabz.wipro.uc13_centralizedarithmeticlogictoenforcedryinquantityoperations;

import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.IMeasurable;
import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.LengthUnit;
import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.VolumeUnit;
import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.WeigthUnit;

public class QuantityMeasurementApp {
    public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> quantity1, Quantity<U> quantity2){
        return quantity1.equals(quantity2);
    }
    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit){
        return quantity.convertTo(targetUnit);
    }
    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2){
        return quantity1.add(quantity2);
    }
    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> quantity1, Quantity<U> quantity2, U targetUnit){
        return quantity1.add(quantity2,targetUnit);
    }
    public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> quantity1, Quantity<U> quantity2){
        return quantity1.subtract(quantity2);
    }
    public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> quantity1, Quantity<U> quantity2, U targetUnit){
        return quantity1.subtract(quantity2,targetUnit);
    }
    public static <U extends IMeasurable> Quantity<U> demonstrateDivision(Quantity<U> quantity1, Quantity<U> quantity2){
        return quantity1.divide(quantity2);
    }
    public static void main(String[] args){
        //Addition Operations(Behavior Unchanged from UC12):
        System.out.println("Input: new Quantity<>(1.0,FEET).add(new Quantity<>(12.0,INCHES)) "+demonstrateAddition(new Quantity<>(1.0, LengthUnit.FEET),new Quantity<>(12.0,LengthUnit.INCHES)));
        System.out.println("Input: new Quantity<>(10.0,KILOGRAM).add(new Quantity<>(5000.0,GRAM),GRAM) "+demonstrateAddition(new Quantity<>(10.0, WeigthUnit.KILOGRAM),new Quantity<>(5000.0,WeigthUnit.GRAM),WeigthUnit.GRAM));
        System.out.println("Internal: Calls performBaseArithmetic(other,ADD) after validation");
        //Subtraction Operations(Behavior Unchanged from UC12):
        System.out.println("Input: new Quantity<>(10.0,FEET).subtract(new Quantity<>(6.0,INCHES)) "+demonstrateSubtraction(new Quantity<>(10.0,LengthUnit.FEET),new Quantity<>(6.0,LengthUnit.INCHES)));
        System.out.println("Input: new Quantity<>(5.0,LITRE).subtract(new Quantity<>(2.0,LITRE),MILLILITRE) "+demonstrateSubtraction(new Quantity<>(5.0, VolumeUnit.LITRE),new Quantity<>(2.0,VolumeUnit.LITRE),VolumeUnit.MILLILITRE));
        System.out.println("Internal: Calls performBaseArithmetic(other,SUBTRACT) after validation");

        //Division Operations(Behavior Unchanged from UC12):
        System.out.println("Input: new Quantity<>(10.0,FEET).divide(new Quantity<>(2.0,FEET)) "+demonstrateDivision(new Quantity<>(10.0,LengthUnit.FEET),new Quantity<>(2.0,LengthUnit.FEET)));
        System.out.println("Input: new Quantity<>(24.0,INCHES).divide(new Quantity<>(2.0,FEET)) "+demonstrateDivision(new Quantity<>(24.0,LengthUnit.INCHES),new Quantity<>(2.0,LengthUnit.FEET)).getValue());
        System.out.println("Internal: Calls performBaseArithmetic(other,DIVISION) after validation");

        //Error Cases (Consistent Across All Operations):
        try{
            System.out.println("Input: new Quantity<>(10.0,FEET).add(null) "+demonstrateAddition(new Quantity<>(10.0,LengthUnit.FEET),null));
        }catch (Exception e){
            System.out.println("Exception  "+e);
        }
        try {
            System.out.println("Input: new Quantity<>(10.0,FEET).subtract(new Quantity<>(5.0,KILOGRAM)) "+demonstrateSubtraction(new Quantity<>(10.0,LengthUnit.FEET),new Quantity<>(5.0,WeigthUnit.KILOGRAM)));
        } catch (Exception e) {
            System.out.println("Exception  "+e);
        }
        try{
            System.out.println("Input: new Quantity<>(10.0,FEET).divide(new Quantity<>(0.0,FEET)) "+demonstrateDivision(new Quantity<>(10.0,LengthUnit.FEET),new Quantity<>(0.0,LengthUnit.FEET)));
        } catch (Exception e) {
            System.out.println("Exception "+e);
        }
    }
}
