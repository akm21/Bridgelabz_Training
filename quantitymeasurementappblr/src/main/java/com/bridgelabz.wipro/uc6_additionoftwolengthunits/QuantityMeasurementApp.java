package com.bridgelabz.wipro.uc6_additionoftwolengthunits;

public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality(Length length1,Length length2){
        return length1.equals(length2);
    }
    public static boolean demonstrateLengthComparison(Length length1,Length length2){
        return length1.compare(length2);
    }
    public static Length demonstrateLengthConversion(Length length,Length.LengthUnit targetUnit){
        return length.convertTo(targetUnit);
    }
    public static Length demonstrateLengthAddition(Length length1,Length length2){
        return length1.add(length2);
    }

    public static void main(String[] args){
        System.out.println("add(Length(1.0,FEET),Length(2.0,FEET)) "+demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.FEET),new Length(2.0, Length.LengthUnit.FEET)));
        System.out.println("add(Length(1.0,FEET),Length(12.0,INCHES)) "+demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.FEET),new Length(12.0, Length.LengthUnit.INCHES)));
        System.out.println("add(Length(12.0,INCHES),Length(1.0,FEET)) "+demonstrateLengthAddition(new Length(12.0, Length.LengthUnit.INCHES),new Length(1.0, Length.LengthUnit.FEET)));
        System.out.println("add(Length(1.0,YARDS),Length(3.0,FEET)) "+demonstrateLengthAddition(new Length(1.0, Length.LengthUnit.YARDS),new Length(3.0, Length.LengthUnit.FEET)));
        System.out.println("add(Length(36.0,INCHES),Length(1.0,YARDS)) "+demonstrateLengthAddition(new Length(36.0, Length.LengthUnit.INCHES),new Length(1.0, Length.LengthUnit.YARDS)));
        System.out.println("add(Length(2.54,CENTIMETERS),Length(1.0,INCHES)) "+demonstrateLengthAddition(new Length(2.54, Length.LengthUnit.CENTIMETERS),new Length(1.0, Length.LengthUnit.INCHES)));
        System.out.println("add(Length(5.0,FEET),Length(0.0,INCHES)) "+demonstrateLengthAddition(new Length(5.0, Length.LengthUnit.FEET),new Length(0.0, Length.LengthUnit.INCHES)));
        System.out.println("add(Length(5.0,FEET),Length(-2,FEET)) "+demonstrateLengthAddition(new Length(5.0, Length.LengthUnit.FEET),new Length(-2.0, Length.LengthUnit.FEET)));

    }
}
