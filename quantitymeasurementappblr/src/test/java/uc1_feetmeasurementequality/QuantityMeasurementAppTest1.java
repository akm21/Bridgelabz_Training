package uc1_feetmeasurementequality;

import com.bridgelabz.wipro.uc1_feetmeasurementequality.QuantityMeasurementApp;
import  org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuantityMeasurementAppTest1 {

    @Test
    void testEquality_SameValue(){
        QuantityMeasurementApp.Feet feet1=new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet feet2=new QuantityMeasurementApp.Feet(1.0);
         assertTrue(feet1.equals(feet2), "Two Feet Objects with value 1.0 ft should be wqual");
    }

    @Test
    void testEquality_DifferentValue(){
        QuantityMeasurementApp.Feet feet1=new QuantityMeasurementApp.Feet(1.0);
        QuantityMeasurementApp.Feet feet2=new QuantityMeasurementApp.Feet(2.0);
        assertFalse(feet1.equals(feet2),"Feet values 1.0 ft and 2.0 ft should not be equal");
    }
    @Test
    void testEquality_NullComparison(){
        QuantityMeasurementApp.Feet feet1=new QuantityMeasurementApp.Feet(1.0);
        assertFalse(feet1.equals(null),"Feet Object should not be equal to null");
    }

    @Test
    void testEquality_NonNumericinput() {
        QuantityMeasurementApp.Feet feet = new QuantityMeasurementApp.Feet(1.0);
        Object nonNumericObject = "1.0";
        assertFalse(feet.equals(nonNumericObject), "Feet object should not be equal to an object of different type");
    }
    @Test
    void testEquality_SameReference() {
        QuantityMeasurementApp.Feet feet = new QuantityMeasurementApp.Feet(1.0);
        assertTrue(feet.equals(feet), "Feet object must be equal to itself type");

    }
    @Test
    void testMainOutput_EqualFeet() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try {
            QuantityMeasurementApp.main(new String[]{});
        } finally {
            System.setOut(originalOut);
        }
        String output = outContent.toString().replace("\r", "");
        String expected = "Input 1.0 ft and 1.0 ft\n" + "Output: Equal (true)\n";
        assertTrue(output.endsWith(expected), "Main should print the equality result for 1.0 ft comparison");
    }

}
