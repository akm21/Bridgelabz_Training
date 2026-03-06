package uc9_weightmeasurement;

import com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.Length;
import com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone.LengthUnit;
import com.bridgelabz.wipro.uc9_weightmeasurement.QuantityMeasurementApp;
import com.bridgelabz.wipro.uc9_weightmeasurement.Weight;
import com.bridgelabz.wipro.uc9_weightmeasurement.WeightUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest9 {
    @Test
    public void testEquality_KilogramToKilogram_SameValue(){
        assertEquals(true, QuantityMeasurementApp.demonstrateWeightEquality(new Weight(1.0, WeightUnit.KILOGRAM),new Weight(1.0,WeightUnit.KILOGRAM)));
    }
    @Test
    public void testEquality_KilogramToKilogram_DifferentValue(){
        assertEquals(false,QuantityMeasurementApp.demonstrateWeightEquality(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(2.0,WeightUnit.KILOGRAM)));
    }
    @Test
    public void testEquality_KilogramToGram_EquivalentValue(){
        assertEquals(true,QuantityMeasurementApp.demonstrateWeightEquality(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(1000.0,WeightUnit.GRAM)));
    }
    @Test
    public void testEquality_GramToKilogram_EquivalentValue(){
        assertEquals(true,QuantityMeasurementApp.demonstrateWeightEquality(new Weight(1000.0,WeightUnit.GRAM),new Weight(1.0,WeightUnit.KILOGRAM)));
    }
    @Test
    public void testEquality_WeightVsLength_Incompatible(){
        Weight weight=new Weight(1.0,WeightUnit.KILOGRAM);
        Length length=new Length(1.0, LengthUnit.FEET);
        assertFalse(weight.equals(length));
    }
    @Test
    public void testEquality_NullComparison(){
        assertEquals(false,QuantityMeasurementApp.demonstrateWeightEquality(new Weight(1.0,WeightUnit.KILOGRAM),null));
    }
    @Test
    public void testEquality_SameReference(){
        Weight weight=new Weight(1.0,WeightUnit.KILOGRAM);
        assertTrue(weight.equals(weight),"Weight Object should be equal to itself");
    }
    @Test
    public void testEquality_NullUnit(){
       try{
           Weight weight=new Weight(1.0,null);
       }catch (IllegalArgumentException e){
           assertTrue(true);
           return;
       }
       assertTrue(false);
    }
    @Test
    public void testEquality_TransitiveProperty(){
        Weight weight1=new Weight(1.0,WeightUnit.KILOGRAM);
        Weight weight2=new Weight(1000.0,WeightUnit.GRAM);
        Weight weight3=new Weight(1.0,WeightUnit.KILOGRAM);
        assertTrue(weight1.equals(weight2));
        assertTrue(weight2.equals(weight3));
        assertTrue(weight1.equals(weight3));
    }
    @Test
    public void testEquality_ZeroValue(){
        assertEquals(true,QuantityMeasurementApp.demonstrateWeightEquality(new Weight(0.0,WeightUnit.KILOGRAM),new Weight(0.0,WeightUnit.GRAM)));
    }
    @Test
    public void testEquality_NegativeWeight(){
        assertEquals(true,QuantityMeasurementApp.demonstrateWeightEquality(new Weight(-1.0,WeightUnit.KILOGRAM),new Weight(-1000.0,WeightUnit.GRAM)));
    }
    @Test
    public void testEquality_LargeWeightValue(){
        assertEquals(true,QuantityMeasurementApp.demonstrateWeightEquality(new Weight(1000000.0,WeightUnit.GRAM),new Weight(1000.0,WeightUnit.KILOGRAM)));
    }
    @Test
    public void testEquality_SmallWeightValue(){
        assertEquals(true,QuantityMeasurementApp.demonstrateWeightEquality(new Weight(0.001,WeightUnit.KILOGRAM),new Weight(1.0,WeightUnit.GRAM)));
    }
    @Test
    public void testEquality_PoundToKilogram(){
        Weight weight=QuantityMeasurementApp.demonstrateWeightConversion(new Weight(1000.0/453.592,WeightUnit.POUND),WeightUnit.KILOGRAM);
        assertTrue(weight.equals(new Weight(1.0,WeightUnit.KILOGRAM)));
    }
    @Test
    public void testEquality_KilogramToPound(){
        Weight weight=QuantityMeasurementApp.demonstrateWeightConversion(new Weight(1.0,WeightUnit.KILOGRAM),WeightUnit.POUND);
        assertTrue(weight.equals(new Weight(1000.0/453.592,WeightUnit.POUND)));
    }
    @Test
    public void testConversion_SameUnit(){
        Weight weight=QuantityMeasurementApp.demonstrateWeightConversion(new Weight(5.0,WeightUnit.KILOGRAM),WeightUnit.KILOGRAM);
        assertTrue(weight.equals(new Weight(5.0,WeightUnit.KILOGRAM)));
    }
    @Test
    public void testConversion_ZeroValue(){
        Weight weigth=QuantityMeasurementApp.demonstrateWeightConversion(new Weight(0.0,WeightUnit.KILOGRAM),WeightUnit.GRAM);
        assertTrue(weigth.equals(new Weight(0.0,WeightUnit.GRAM)));
    }
    @Test
    public void testConversion_NegativeValue(){
        Weight weight=QuantityMeasurementApp.demonstrateWeightConversion(new Weight(-1.0,WeightUnit.KILOGRAM),WeightUnit.GRAM);
        assertTrue(weight.equals(new Weight(-1000.0,WeightUnit.GRAM)));
    }
    @Test
    public void testConversion_RoundTrip(){
        Weight weight1=QuantityMeasurementApp.demonstrateWeightConversion(new Weight(1.5,WeightUnit.KILOGRAM),WeightUnit.GRAM);
        Weight weight2=QuantityMeasurementApp.demonstrateWeightConversion(weight1,WeightUnit.KILOGRAM);
        assertTrue(weight2.equals(new Weight(1.5,WeightUnit.KILOGRAM)));
    }
    @Test
    public void testAddition_SameUnit_KilogramPlusKilogram(){
        Weight weight=QuantityMeasurementApp.demonstrateWeightAddition(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(2.0,WeightUnit.KILOGRAM));
        assertTrue(weight.equals(new Weight(3.0,WeightUnit.KILOGRAM)));
    }
    @Test
    public void testEquality_SameUnit(){
        assertEquals(true,QuantityMeasurementApp.demonstrateWeightEquality(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(1.0,WeightUnit.KILOGRAM)));
    }
    @Test
    public void testAddition_CrossUnit_KilogramPlusGram(){
        Weight weight=QuantityMeasurementApp.demonstrateWeightAddition(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(1000.0,WeightUnit.GRAM));
        assertTrue(weight.equals(new Weight(2.0,WeightUnit.KILOGRAM)));
    }
    @Test
    public void testAddition_CrossUnit_PoundPlusKilogram(){
        Weight weight=QuantityMeasurementApp.demonstrateWeightAddition(new Weight(1000.0/453.592,WeightUnit.POUND),new Weight(1.0,WeightUnit.KILOGRAM));
        assertTrue(weight.equals(new Weight(2000.0/453.592,WeightUnit.POUND)));
    }
    @Test
    public void testAddition_ExplicitTargetUnit_Kilogram(){
        Weight weight=QuantityMeasurementApp.demonstrateWeightAddition(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(1000.0,WeightUnit.GRAM),WeightUnit.GRAM);
        assertTrue(weight.equals(new Weight(2000.0,WeightUnit.GRAM)));
    }
    @Test
    public void testAddition_Commutativity(){
        Weight weight1=QuantityMeasurementApp.demonstrateWeightAddition(new Weight(1.0,WeightUnit.KILOGRAM),new Weight(1000.0,WeightUnit.GRAM));
        Weight weight2=QuantityMeasurementApp.demonstrateWeightAddition(new Weight(1000.0,WeightUnit.GRAM),new Weight(1.0,WeightUnit.KILOGRAM));
        assertEquals(true,QuantityMeasurementApp.demonstrateWeightEquality(weight1,weight2));
    }
    @Test
    public void testAddition_WithZero(){
        Weight weight=QuantityMeasurementApp.demonstrateWeightAddition(new Weight(5.0,WeightUnit.KILOGRAM),new Weight(0.0,WeightUnit.GRAM));
        assertTrue(weight.equals(new Weight(5.0,WeightUnit.KILOGRAM)));
    }
    @Test
    public void testAddition_NegativeValues(){
        Weight weight=QuantityMeasurementApp.demonstrateWeightAddition(new Weight(5.0,WeightUnit.KILOGRAM),new Weight(-2000.0,WeightUnit.GRAM));
        assertTrue(weight.equals(new Weight(3.0,WeightUnit.KILOGRAM)));
    }
    @Test
    public void testAddition_LargeValues(){
        Weight weight=QuantityMeasurementApp.demonstrateWeightAddition(new Weight(1e6,WeightUnit.KILOGRAM),new Weight(1e6,WeightUnit.KILOGRAM));
        assertTrue(weight.equals(new Weight(2e6,WeightUnit.KILOGRAM)));
    }
}
