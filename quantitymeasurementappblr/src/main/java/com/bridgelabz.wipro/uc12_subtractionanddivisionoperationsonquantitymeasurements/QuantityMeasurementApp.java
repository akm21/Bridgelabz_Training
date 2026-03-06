package com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements;

public class QuantityMeasurementApp {
    public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> quantity1,Quantity<U> quantity2){
        return quantity1.equals(quantity2);
    }
    public static <U extends IMeasurable> Quantity<U>demonstrateConversion(Quantity<U> quantity,U targetUnit){
        return quantity.convertTo(targetUnit);
    }
    public static <U extends IMeasurable> Quantity<U>demonstrateAddition(Quantity<U> quantity1,Quantity<U> quantity2){
        return quantity1.add(quantity2);
    }
    public static <U extends IMeasurable> Quantity<U>demonstrateAddition(Quantity<U> quantity1,Quantity<U> quantity2,U targetUnit){
        return quantity1.add(quantity2,targetUnit);
    }
    public static <U extends IMeasurable> Quantity<U>demonstrateSubtraction(Quantity<U> quantity1,Quantity<U> quantity2){
        return quantity1.subtract(quantity2);
    }
    public static <U extends IMeasurable> Quantity<U>demonstrateSubtraction(Quantity<U> quantity1,Quantity<U> quantity2,U targetUnit){
        return quantity1.subtract(quantity2,targetUnit);
    }
    public static <U extends IMeasurable> Quantity<U>demonstrateDivision(Quantity<U> quantity1,Quantity<U> quantity2){
        return quantity1.divide(quantity2);
    }
    public static void main(String[] args){
        //Subtraction with Implicit Target Unit

        System.out.println("Input: new Quantity<>(10.0,FEET).subtract(new Quantity<>(6.0,INCHES)) "+demonstrateSubtraction(new Quantity<>(10.0,LengthUnit.FEET),new Quantity<>(6.0,LengthUnit.INCHES)));
        System.out.println("Input: new Quantity<>(10.0,KILOGRAM).subtract(new Quantity<>(5000.0,GRAM)) "+demonstrateSubtraction(new Quantity<>(10.0,WeigthUnit.KILOGRAM),new Quantity<>(5000.0,WeigthUnit.GRAM)));
        System.out.println("Input: new Quantity<>(5.0,LITRE).subtract(new Quantity<>(500.0,MILLILITRE)) "+demonstrateSubtraction(new Quantity<>(5.0,VolumeUnit.LITRE),new Quantity<>(500.0,VolumeUnit.MILLILITRE)));
        //Subtraction with Explicit Target Unit
        System.out.println("Input: new Quantity<>(10.0,FEET).subtract(new Quantity<>(6.0,INCHES),INCHES) "+demonstrateSubtraction(new Quantity<>(10.0,LengthUnit.FEET),new Quantity<>(6.0,LengthUnit.INCHES),LengthUnit.INCHES));
        System.out.println("Input: new Quantity<>(10.0,KILOGRAM).subtract(new Quantity<>(5000.0,GRAM),GRAM) "+demonstrateSubtraction(new Quantity<>(10.0,WeigthUnit.KILOGRAM),new Quantity<>(5000.0,WeigthUnit.GRAM),WeigthUnit.GRAM));
        System.out.println("Input: new Quantity<>(5.0,LITRE).subtract(new Quantity<>(2.0,LITRE),MILLILITRE) "+demonstrateSubtraction(new Quantity<>(5.0,VolumeUnit.LITRE),new Quantity<>(2.0,VolumeUnit.LITRE),VolumeUnit.MILLILITRE));
        //Subtraction Resulting in Negative Values
        System.out.println("Input: new Quantity<>(5.0,FEET).subtract(new Quantity<>(10.0,FEET)) "+demonstrateSubtraction(new Quantity<>(5.0,LengthUnit.FEET),new Quantity<>(10.0,LengthUnit.FEET)));
        System.out.println("Input: new Quantity<>(2.0,KILOGRAM).subtract(new Quantity<>(5.0,KILOGRAM)) "+demonstrateSubtraction(new Quantity<>(2.0,WeigthUnit.KILOGRAM),new Quantity<>(5.0,WeigthUnit.KILOGRAM)));
        //Subtraction Resulting in Zero
        System.out.println("Input: new Quantity<>(10.0,FEET).subtract(new Quantity<>(120.0,INCHES)) "+demonstrateSubtraction(new Quantity<>(10.0,LengthUnit.FEET),new Quantity<>(120.0,LengthUnit.INCHES)));
        System.out.println("Input: new Quantity<>(1.0,LITRE).subtract(new Quantity<>(1000.0,MILLILITRE)) "+demonstrateSubtraction(new Quantity<>(1.0,VolumeUnit.LITRE),new Quantity<>(1000.0,VolumeUnit.MILLILITRE)));
        //Division Operations
        System.out.println("Input: new Quantity<>(10.0,FEET).divide(new Quantity<>(2.0,FEET)) "+demonstrateDivision(new Quantity<>(10.0,LengthUnit.FEET),new Quantity<>(2.0,LengthUnit.FEET)).getValue());
        System.out.println("Input: new Quantity<>(10.0,FEET).divide(new Quantity<>(5.0,FEET)) "+demonstrateDivision(new Quantity<>(10.0,LengthUnit.FEET),new Quantity<>(5.0,LengthUnit.FEET)));
        System.out.println("Input: new Quantity<>(24.0,INCHES).divide(new Quantity<>(2.0,FEET)) "+demonstrateDivision(new Quantity<>(24.0,LengthUnit.INCHES),new Quantity<>(2.0,LengthUnit.FEET)));
        System.out.println("Input: new Quantity<>(10.0,KILOGRAM).divide(new Quantity<>(5.0,KILOGRAM)) "+demonstrateDivision(new Quantity<>(10.0,WeigthUnit.KILOGRAM),new Quantity<>(5.0,WeigthUnit.KILOGRAM)));
        System.out.println("Input: new Quantity<>(5.0,LITRE).divide(new Quantity<>(10.0,LITRE)) "+demonstrateDivision(new Quantity<>(5.0,VolumeUnit.LITRE),new Quantity<>(10.0,VolumeUnit.LITRE)));
        //Division with Different Units(Same Category):
        System.out.println("Input: new Quantity<>(12.0,INCHES).divide(new Quantity<>(1.0,FEET)) "+demonstrateDivision(new Quantity<>(12.0,LengthUnit.INCHES),new Quantity<>(1.0,LengthUnit.FEET)).getValue());
        System.out.println("Input: new Quantity<>(2000.0,GRAM).divide(new Quantity<>(1.0,KILOGRAM)) "+demonstrateDivision(new Quantity<>(2000.0,WeigthUnit.GRAM),new Quantity<>(1.0,WeigthUnit.KILOGRAM)));
        System.out.println("Input: new Quantity<>(1000.0,MILLILITRE).divide(new Quantity<>(1.0,LITRE)) "+demonstrateDivision(new Quantity<>(1000.0,VolumeUnit.MILLILITRE),new Quantity<>(1.0,VolumeUnit.LITRE)));
        //Error Cases:
        /*try {
            Quantity length = new Quantity<>(10.0, LengthUnit.FEET);
            System.out.println(length.subtract(null));
        } catch (Exception e) {
            //throw new IllegalArgumentException(e+"Other Quantity cannot be null");
        }*/
        try {
            System.out.println("Input: new Quantity<>(10.0,FEET).subtract(null) " + demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), null));
           // System.out.println("Input: new Quantity<>(10.0,FEET).divide(new Quantity<>(0.0,FEET)) " + demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(0.0, LengthUnit.FEET)));
           // System.out.println("Input: new Quantity<>(10.0,FEET).subtract(new Quantity<>(5.0,KILOGRAM)) " + demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(5.0, WeightUnit.KILOGRAM)));
        }catch(Exception e ){
            System.out.println("Error messages: "+e);
        }
        try {
            //System.out.println("Input: new Quantity<>(10.0,FEET).subtract(null) " + demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), null));
            System.out.println("Input: new Quantity<>(10.0,FEET).divide(new Quantity<>(0.0,FEET)) " + demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(0.0, LengthUnit.FEET)));
            //System.out.println("Input: new Quantity<>(10.0,FEET).subtract(new Quantity<>(5.0,KILOGRAM)) " + demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(5.0, WeightUnit.KILOGRAM)));
        }catch(Exception e ){
            System.out.println("Error messages: "+e);
        }
        try {
            //System.out.println("Input: new Quantity<>(10.0,FEET).subtract(null) " + demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), null));
           // System.out.println("Input: new Quantity<>(10.0,FEET).divide(new Quantity<>(0.0,FEET)) " + demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(0.0, LengthUnit.FEET)));
            System.out.println("Input: new Quantity<>(10.0,FEET).subtract(new Quantity<>(5.0,KILOGRAM)) " + demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(5.0, WeigthUnit.KILOGRAM)));
        }catch(Exception e ){
            System.out.println("Error messages: "+e);
        }
    }
}
