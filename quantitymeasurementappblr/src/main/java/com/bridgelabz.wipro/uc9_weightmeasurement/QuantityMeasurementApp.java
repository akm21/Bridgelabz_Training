package com.bridgelabz.wipro.uc9_weightmeasurement;

import com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.Length;
import com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.LengthUnit;

public class QuantityMeasurementApp {
    public static boolean demonstrateWeightEquality(Weight weight1,Weight weight2){
        return weight1.equals(weight2);
    }
    public static boolean demonstrateWeightComparison(double value1,WeightUnit unit1,double value2,WeightUnit unit2){
        Weight weight1=new Weight(value1,unit1);
        Weight weight2=new Weight(value2,unit2);
        return weight1.compare(weight2);
    }
    public Weight demonstrateWeightConversion(double value,WeightUnit fromUnit,WeightUnit toUnit){
        Weight weight=new Weight(value,fromUnit);
        return weight.convertTo(value,fromUnit,toUnit);
    }
    public static Weight demonstrateWeightConversion(Weight weight,WeightUnit toUnit){
        return weight.convertTo(toUnit);
    }
    public static Weight demonstrateWeightAddition(Weight weight1,Weight weight2){
        return weight1.Add(weight2);
    }
    public static Weight demonstrateWeightAddition(Weight weight1,Weight weight2,WeightUnit targetUnit){
        return Weight.Add(weight1,weight2,targetUnit);
    }
    public static void main(String[] args){
        //Equality Comparison      1000.0/453.592
        System.out.println("Weigth(1.0,KILOGRAM).equals(Weight(1.0,KILOGRAM)) "+demonstrateWeightEquality(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(1.0,WeightUnit.KILOGRAM)));
        System.out.println("Weight(1.0,KILOGRAM).equals(Weight(1000.0,GRAM)) "+demonstrateWeightEquality(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(1000,WeightUnit.GRAM)));
        System.out.println("Weight(2.0,POUND).equals(Weight(2.0,POUND)) "+demonstrateWeightEquality(new Weight(2.0,WeightUnit.POUND),new Weight(2.0,WeightUnit.POUND)));
        System.out.println("Weight(1.0,KILOGRAM).equals(Weight(~2.20462,POUND)) "+demonstrateWeightEquality(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(1000.0/453.592,WeightUnit.POUND)));
        System.out.println("Weight(500.0,GRAM).equals(Weight(0.5,KILOGRAM)) "+demonstrateWeightEquality(new Weight(500.0,WeightUnit.GRAM),new Weight(0.5,WeightUnit.KILOGRAM)));
        System.out.println("Weight(1.0,POUND).equals(Weight(453.592,GRAM)) "+demonstrateWeightEquality(new Weight(1.0,WeightUnit.POUND),new Weight(453.592,WeightUnit.GRAM)));

        //Unit Conversion
        System.out.println("Weight(1.0,KILOGRAM).convertTo(GRAM) "+demonstrateWeightConversion(new Weight(1.0,WeightUnit.KILOGRAM),WeightUnit.GRAM));
        System.out.println("Weight(2.0,POUND).convertTo(KILOGRAM) "+demonstrateWeightConversion(new Weight(2.0,WeightUnit.POUND),WeightUnit.KILOGRAM));
        System.out.println("Weight(500.0,GRAM).convertTo(POUND) "+demonstrateWeightConversion(new Weight(500.0,WeightUnit.GRAM),WeightUnit.POUND));
        System.out.println("Weight(0.0,KILOGRAM).convertTo(GRAM) "+demonstrateWeightConversion(new Weight(0.0,WeightUnit.KILOGRAM),WeightUnit.GRAM));

        //Addition Operation(Implicit Target Unit)
        System.out.println("Weight(1.0,KILOGRAM).add(Weight(2.0,KILOGRAM)) "+demonstrateWeightAddition(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(2.0,WeightUnit.KILOGRAM)));
        System.out.println("Weight(1.0,KILOGRAM).add(Weight(1000.0,GRAM)) "+demonstrateWeightAddition(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(1000.0,WeightUnit.GRAM)));
        System.out.println("Weight(500.0,GRAM).add(Weight(0.5,KILOGRAM)) "+demonstrateWeightAddition(new Weight(500.0,WeightUnit.GRAM),new Weight(0.5,WeightUnit.KILOGRAM)));

        //Addition Operation(Explicit Target Unit)
        System.out.println("Weight(1.0,KILOGRAM).add(Weight(1000.0,GRAM),GRAM) "+demonstrateWeightAddition(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(1000.0,WeightUnit.GRAM),WeightUnit.GRAM));
        System.out.println("Weight(1.0,POUND).add(Weight(453.592,GRAM),POUND) "+demonstrateWeightAddition(new Weight(1.0,WeightUnit.POUND),new Weight(453.592,WeightUnit.GRAM),WeightUnit.POUND));
        System.out.println("Weight(2.0,KILOGRAM).add(Weight(4.0,POUND),KILOGRAM) "+demonstrateWeightAddition(new Weight(2.0,WeightUnit.KILOGRAM),new Weight(4.0,WeightUnit.POUND),WeightUnit.KILOGRAM));

        //Category Incompatibility
        Weight weight=new Weight(1.0,WeightUnit.KILOGRAM);
        Length length=new Length(1.0,LengthUnit.FEET);
        System.out.println("Weight(1.0,KILOGRAM).equals(Weight(1.0,FOOT)) "+weight.equals(length));
    }

}
