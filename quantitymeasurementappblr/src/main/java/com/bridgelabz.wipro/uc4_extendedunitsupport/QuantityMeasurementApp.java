package com.bridgelabz.wipro.uc4_extendedunitsupport;

public class QuantityMeasurementApp {

    public  static boolean  demonstrateLengthEquality(Length length1, Length length2){
         return length1.equals(length2);
    }
    public static boolean demonstrateLengthComparison(double value1, Length.LengthUnit value2, double value3, Length.LengthUnit value4){
        Length x=new Length(value1,value2);
        Length y=new Length(value3,value4);
        boolean result=demonstrateLengthEquality(x,y);
        System.out.println(result);
        return  result;
    }

    public static void main(String[] args){
        demonstrateLengthComparison(1.0,Length.LengthUnit.FEET, 12.0,Length.LengthUnit.INCHES);
        demonstrateLengthComparison(1.0,Length.LengthUnit.YARDS, 36.0,Length.LengthUnit.INCHES);
        demonstrateLengthComparison(100.0,Length.LengthUnit.CENTIMETERS, 39.3701,Length.LengthUnit.INCHES);
        demonstrateLengthComparison(3.0,Length.LengthUnit.FEET, 1.0,Length.LengthUnit.YARDS);
        demonstrateLengthComparison(30.48,Length.LengthUnit.CENTIMETERS, 1.0,Length.LengthUnit.FEET);

    }

}
