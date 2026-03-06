package com.bridgelabz.wipro.uc5_unittounitconversion;

public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality(Length length1, Length length2) {
        return length1.equals(length2);
    }

    public static boolean demonstrateLengthComparison(Length length1, Length length2) {
        return length1.compare(length2);
    }

    public static Length demonstrateLengthConversion(Length length, Length.LengthUnit targetUnit) {
        return length.convertTo(targetUnit);
    }

    public static void main(String[] args) {
        System.out.println("convert(1.0,Feet,Inches): " + demonstrateLengthConversion(new Length(1.0, Length.LengthUnit.FEET), Length.LengthUnit.INCHES));
        System.out.println("convert(3.0,Yards,Feet): " + demonstrateLengthConversion(new Length(3.0, Length.LengthUnit.YARDS), Length.LengthUnit.FEET));
        System.out.println("convert(36.0,Inches,Yards): " + demonstrateLengthConversion(new Length(36.0, Length.LengthUnit.INCHES), Length.LengthUnit.YARDS));
        System.out.println("convert(1.0,Centimeters,Inches): " + demonstrateLengthConversion(new Length(1.0, Length.LengthUnit.CENTIMETERS), Length.LengthUnit.INCHES));
        System.out.println("convert(0.0,Feet,Inches): " + demonstrateLengthConversion(new Length(0.0, Length.LengthUnit.FEET), Length.LengthUnit.INCHES));

        Length length1 = new Length(1.0, Length.LengthUnit.FEET);
        Length length2 = new Length(12.0, Length.LengthUnit.INCHES);
        System.out.println("Are lengths equal? " + demonstrateLengthEquality(length1, length2));
    }
}
