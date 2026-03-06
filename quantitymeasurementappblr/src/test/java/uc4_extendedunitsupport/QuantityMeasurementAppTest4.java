package uc4_extendedunitsupport;

import com.bridgelabz.wipro.uc4_extendedunitsupport.Length;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest4 {
    @Test
    public void testEquality_YardToYard_SameValue(){
        Length length1=new Length(1.0, Length.LengthUnit.YARDS);
        Length length2=new Length(1.0, Length.LengthUnit.YARDS);
        assertTrue(length1.equals(length2),"1.0 Yard should be equal to 1.0 Yard");
    }
    @Test
    public void testEquality_YardToYard_DifferentValue(){
         Length length1=new Length(1.0, Length.LengthUnit.YARDS);
         Length length2=new Length(2.0, Length.LengthUnit.YARDS);
         assertFalse(length1.equals(length2),"1.0 Yard should not be equal to 2.0 Yard");
    }
    @Test
    public void testEquality_YardToFeet_EquivalentValue(){
        Length length1=new Length(1.0, Length.LengthUnit.YARDS);
        Length length2=new Length(3.0, Length.LengthUnit.FEET);
        assertTrue(length1.equals(length2),"1.0 Yard should be equal to 3 Feet");
    }
    @Test
    public void testEquality_FeetToYard_EquivalentValue(){
        Length length1=new Length(3.0, Length.LengthUnit.FEET);
        Length length2=new Length(1.0, Length.LengthUnit.YARDS);
        assertTrue(length1.equals(length2),"3.0 ft should be equal to 1.0 Yard");
    }
    @Test
    public void testEquality_YardToInches_EquivalentValue() {
        Length length1=new Length(1.0, Length.LengthUnit.YARDS);
        Length length2=new Length(36.0, Length.LengthUnit.INCHES);
        assertTrue(length1.equals(length2),"1.0 Yard should be equal to 36 Inches");
    }
    @Test
    public void testEquality_InchesToYard_EquivalentValue() {
        Length length1 = new Length(36.0, Length.LengthUnit.INCHES);
        Length length2 = new Length(1.0, Length.LengthUnit.YARDS);
        assertTrue(length1.equals(length2),"36.0 Inches should be equal to 1.0 Yard");
    }
    @Test
    public void testEquality_YardToFeet_NonEquivalentValue(){
        Length length1=new Length(1.0, Length.LengthUnit.YARDS);
        Length length2=new Length(2.0, Length.LengthUnit.FEET);
        assertFalse(length1.equals(length2),"1.0 Yard should not be equal to 2.0 ft");
    }
    @Test
    public void testEquality_centimetersToInches_EquivalentValue(){
        Length length1=new Length(1.0, Length.LengthUnit.CENTIMETERS);
        Length length2=new Length(0.393701, Length.LengthUnit.INCHES);
        assertTrue(length1.equals(length2),"1.0 Centimeters should be equal to 0.393701");
    }
    @Test
    public void testEquality_centimetersToFeet_NonEquivalentValue(){
        Length length1=new Length(1.0, Length.LengthUnit.CENTIMETERS);
        Length length2=new Length(1.0, Length.LengthUnit.FEET);
        assertFalse(length1.equals(length2),"1.0 CM should not be equal to 1.0 ft");
    }
    @Test
    public void testEquality_MultiUnit_TransitiveProperty(){
        Length length1=new Length(1.0, Length.LengthUnit.YARDS);
        Length length2=new Length(3.0, Length.LengthUnit.FEET);
        Length length3=new Length(36.0, Length.LengthUnit.INCHES);
        assertTrue(length1.equals(length2),"1.0 Yard should be equal to 3.0 ft");
        assertTrue(length2.equals(length3),"3.0 ft should be equal to 36.0 inches");
        assertTrue(length1.equals(length3),"1.0 Yard should be equal to 36.0 inches(Transitive property");
    }

    @Test
    void testEquality_YardWithNullUnit() {
        // Arrange
        Length length1 = new Length(1.0, Length.LengthUnit.YARDS);
        Length length2 = new Length(1.0, null); // allowed in this variant

        // Act + Assert: calling equals should throw (IllegalStateException or IllegalArgumentException)
        RuntimeException ex = assertThrows(
                RuntimeException.class,         // or pick the exact type you throw
                () -> length1.equals(length2),
                "equals() should throw when other unit is null"
        );
    }
        @Test
    public void testEquality_YardSameReference(){
         Length length=new Length(1.0, Length.LengthUnit.YARDS);
         assertTrue(length.equals(length),"length should be equal to itself");
    }
    @Test
    public void testEquality_YardNullComparison(){
        Length length=new Length(1.0, Length.LengthUnit.YARDS);
        assertFalse(length.equals(null),"length should not be equal to null");
    }
    @Test
    public void testEquality_CentimetersWithNullUnit(){
        Length length1 = new Length(1.0, Length.LengthUnit.CENTIMETERS);
        Length length2 = new Length(1.0, null); // allowed in this variant

        // Act + Assert: calling equals should throw (IllegalStateException or IllegalArgumentException)
        RuntimeException ex = assertThrows(
                RuntimeException.class,         // or pick the exact type you throw
                () -> length1.equals(length2),
                "equals() should throw when other unit is null"
        );
    }
    @Test
    public void testEquality_CentimetersSameReference(){
       Length length=new Length(1.0, Length.LengthUnit.CENTIMETERS);
       assertTrue(length.equals(length),"length should be equal to itself");
    }
    @Test
    public void testEquality_CentimetersNullComparison(){
        Length length=new Length(1.0, Length.LengthUnit.CENTIMETERS);
        assertFalse(length.equals(null),"length should not be equal to null");
    }
    @Test
    public void testEquality_AllUnits_ComplexScenario(){
        Length length1=new Length(2.0, Length.LengthUnit.YARDS);
        Length length2=new Length(6.0, Length.LengthUnit.FEET);
        Length length3=new Length(72.0, Length.LengthUnit.INCHES);
        assertTrue(length1.equals(length2),"2.0 yards should be equal to 6.0 ft");
        assertTrue(length2.equals(length3),"6.0 ft should be equal to 72.0 inches");
        assertTrue(length1.equals(length3),"2.0 yards should be equal to 72.0 inches");
    }
}
