package uc3_genericquantityclassfordryprinciple;

import com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.LengthUnit;
import com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.QuantityLength;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuantityMeasurementAppTest3 {
    @Test
    public void testEquality_SameFeetValues(){
        QuantityLength q1=new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2=new QuantityLength(1.0, LengthUnit.FEET);

        assertTrue(q1.equals(q2),"1.0 ft should be equal to 1.0 ft");
    }

    @Test
    public  void testEquality_SameInchValues(){
        QuantityLength q1=new QuantityLength(1.0,LengthUnit.INCHES);
        QuantityLength q2=new QuantityLength(1.0,LengthUnit.INCHES);

        assertTrue(q1.equals(q2),"1.0 inch should be equal to 1.0 inch");
    }

    @Test
    public  void testEquality_FeetAndInches(){
        QuantityLength q1=new QuantityLength(1.0,LengthUnit.FEET);
        QuantityLength q2=new QuantityLength(12.0,LengthUnit.INCHES);
        assertTrue(q1.equals(q2),"1.0 ft should be equal to 12.0 inches");
    }

    @Test
    public void testEquality_DifferentValues(){
        QuantityLength q1=new QuantityLength(1.0,LengthUnit.FEET);
        QuantityLength q2=new QuantityLength(2.0,LengthUnit.FEET);

        assertFalse(q1.equals(q2),"1.0 ft should not be equal to 2.0 ft");
    }

    @Test
    public  void testEquality_DifferentConvertedValues(){
        QuantityLength q1=new QuantityLength(1.0,LengthUnit.FEET);
        QuantityLength q2=new QuantityLength(10.0,LengthUnit.INCHES);
        assertFalse(q1.equals(q2),"1.0 ft should not be equal to 10.0 inches");
    }

    @Test
    public void testEquality_SameReference(){
        QuantityLength q1=new QuantityLength(1.0,LengthUnit.FEET);
        assertTrue(q1.equals(q1),"q1 should be equal to itself");
    }
    @Test
    public void testEquality_NullComparision(){
        QuantityLength q1=new QuantityLength(1.0,LengthUnit.FEET);
        assertFalse(q1.equals(null),"q1 should not be equal to null");
    }
    @Test
    public void testEquality_TypeSafety(){
        QuantityLength q1=new QuantityLength(1.0,LengthUnit.FEET);
        assertFalse(q1.equals("1.0"),"q1 should not be equal to different type");
    }
    @Test
    void testMainOutput_FeetAndInches() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try {
            com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.QuantityMeasurementApp2.main(new String[]{});
        } finally {
            System.setOut(originalOut);
        }
        String output = outContent.toString().replace("\r", "");
        String expected = "Input: Quantity (1.0, \"feet\") and Quantity (12.0, \"inches\")\n" +
                "Output: Equal (true)\n" +
                "Input: Quantity (1.0, \"inch\") and Quantity (1.0, \"inch\")\n" +
                "Output: Equal (true)\n";
        assertTrue(output.endsWith(expected), "Main should print equality results for feet/inches and inch/inch cases");
    }

}
