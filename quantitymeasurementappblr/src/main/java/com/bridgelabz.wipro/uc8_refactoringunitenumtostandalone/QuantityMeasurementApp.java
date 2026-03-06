package com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone;

public class QuantityMeasurementApp {
    public static boolean demonstrateLengthEquality(Length length1,Length length2){
        return length1.equals(length2);
    }
    public static boolean demonstrateLengthComparison(double value1,LengthUnit unit1,double value2,LengthUnit unit2){
                  Length length1=new Length(value1,unit1);
                  Length length2=new Length(value2,unit2);
                  return length1.compare(length2);
    }
    public  Length demonstrateLengthConversion(double value,LengthUnit fromUnit,LengthUnit toUnit){
        Length l=new Length(value,fromUnit);
        return l.convertTo(value,fromUnit,toUnit);
    }
    public static Length demonstrateLengthConversion(Length length,LengthUnit toUnit){
        return length.convertTo(toUnit);
    }
    public static Length demonstrateLengthAddition(Length length1,Length length2){
        return length1.Add(length2);
    }
    public static Length demonstrateLengthAddition(Length length1,Length length2,LengthUnit targetUnit){
        return Length.Add(length1,length2,targetUnit);
    }
    public static void main(String[] args){
        System.out.println("Length(1.0,FEET).convertTo(INCHES) "+demonstrateLengthConversion(new Length(1.0,LengthUnit.FEET),LengthUnit.INCHES));
        System.out.println("Length(1.0,FEET).add(Length(12.0,INCHES),FEET) "+demonstrateLengthAddition(new Length(1.0,LengthUnit.FEET),new Length(12.0,LengthUnit.INCHES),LengthUnit.FEET));
        System.out.println("Length(36.0,INCHES).equals(Length(1.0,YARDS)) "+demonstrateLengthEquality(new Length(36.0,LengthUnit.INCHES),new Length(1.0,LengthUnit.YARDS)));
        System.out.println("Length(1.0,YARDS).add(Length(3.0,FEET),YARDS) "+demonstrateLengthAddition(new Length(1.0,LengthUnit.YARDS),new Length(3.0,LengthUnit.FEET),LengthUnit.YARDS));
        System.out.println("Length(2.54,CENTIMETERS).convertTo(INCHES) "+demonstrateLengthConversion(new Length(2.54,LengthUnit.CENTIMETERS),LengthUnit.INCHES));
        System.out.println("Length(5.0,FEET).add(Length(0.0,INCHES),FEET) "+demonstrateLengthAddition(new Length(5.0,LengthUnit.FEET),new Length(0.0,LengthUnit.INCHES),LengthUnit.FEET));
        System.out.println("LengthUnit.FEET.convertToBaseUnit(12.0) "+demonstrateLengthConversion(new Length(1.0,LengthUnit.FEET),LengthUnit.INCHES));
        System.out.println("LengthUnit.INCHES.convertToBaseUnit(12.0) "+demonstrateLengthConversion(new Length(12.0,LengthUnit.INCHES),LengthUnit.FEET));
        System.out.println(LengthUnit.FEET.convertToBaseUnit(12.0));
        System.out.println(LengthUnit.INCHES.convertToBaseUnit(12.0));
    }
}
