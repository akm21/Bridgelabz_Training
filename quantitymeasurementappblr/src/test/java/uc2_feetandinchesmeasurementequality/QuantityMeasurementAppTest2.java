package uc2_feetandinchesmeasurementequality;

import com.bridgelabz.wipro.uc2_feetandinchesmeasurementequality.QuantityMeasurementApp1;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuantityMeasurementAppTest2 {
    @Test
     public void testFeetEquality_SameValue() {
        assertTrue(QuantityMeasurementApp1.compareFeet(1.0, 1.0), "Feet values 1.0 and 1.0 should be equal");
    }
    @Test
    public void testFeetEquality_DifferentValue() {
        assertFalse(QuantityMeasurementApp1.compareFeet(1.0, 2.0), "Feet values 1.0 and 2.0 should not be equal");
    }
    @Test
    public void testFeetEquality_NullComparison() {
        QuantityMeasurementApp1.Feet feet=new QuantityMeasurementApp1.Feet(1.0);
        assertFalse(feet.equals(null), "Feet object should not be equal to null");
    }
    @Test
    public void testFeetEquality_NonNumericInput() {
        QuantityMeasurementApp1.Feet feet = new QuantityMeasurementApp1.Feet(1.0);
        Object nonNumericObject = "1.0";
        assertFalse(feet.equals(nonNumericObject), "Feet object should not be equal to an object of different type");
    }
    @Test
    public void testFeetEquality_SameReference() {
        QuantityMeasurementApp1.Feet feet=new QuantityMeasurementApp1.Feet(1.0);
        assertTrue(feet.equals(feet), "Feet object should  be equal to itself");
    }

    @Test
    public void testInchesEquality_SameValue() {
        assertTrue(QuantityMeasurementApp1.compareInches(1.0, 1.0), "Inches values 1.0 and 1.0 should be equal");
    }
    @Test
    public void testInchesEquality_DifferentValue() {
        assertFalse(QuantityMeasurementApp1.compareInches(1.0, 2.0), "Inches values 1.0 and 2.0 should not be equal");
    }
    @Test
    public void testInchesEquality_NullComparison() {
        QuantityMeasurementApp1.Inches inche=new QuantityMeasurementApp1.Inches(1.0);
        assertFalse(inche.equals(null), "inche object should not be equal to null");
    }
    @Test
    public void testInchesEquality_NonNumericInput() {
        QuantityMeasurementApp1.Inches inche = new QuantityMeasurementApp1.Inches(1.0);
        Object nonNumericObject = "1.0";
        assertFalse(inche.equals(nonNumericObject), "inche object should not be equal to an object of different type");
    }
    @Test
    public void testInchesEquality_SameReference() {
        QuantityMeasurementApp1.Inches inche=new QuantityMeasurementApp1.Inches(1.0);
        assertTrue(inche.equals(inche), "inche object should  be equal to itself");
    }

    @Test
    void testFeetNotEqualToInches() {
        QuantityMeasurementApp1.Feet feet = new QuantityMeasurementApp1.Feet(1.0);
        QuantityMeasurementApp1.Inches inches = new QuantityMeasurementApp1.Inches(1.0);

        assertFalse(
                feet.equals(inches),
                "Feet should not be equal to Inches even if values match"
        );
    }

    @Test
    void testInchesNotEqualToOtherObject() {
        QuantityMeasurementApp1.Inches inches = new QuantityMeasurementApp1.Inches(1.0);

        assertFalse(
                inches.equals("1.0"),
                "Inches should not be equal to a non-numeric object"
        );
    }

    @Test
    void testMainOutput_InchesAndFeet() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try {
            QuantityMeasurementApp1.main(new String[]{});
        } finally {
            System.setOut(originalOut);
        }
        String output = outContent.toString().replace("\r", "");
        String expected = "Input 1.0 inch and 1.0 inch\n" +
                "Output: Equal (true)\n" +
                "Input 1.0 ft and 1.0 ft\n" +
                "Output: Equal (true)\n";
        assertTrue(output.endsWith(expected), "Main should print equality results for inches and feet");
    }

}
