package com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple;

public class QuantityMeasurementApp2 {

        public static void main(String[] args) {

            com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.QuantityLength q1 = new com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.QuantityLength(1.0, com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.LengthUnit.FEET);
            com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.QuantityLength q2 = new com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.QuantityLength(12.0, com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.LengthUnit.INCHES);
            System.out.println("Input: Quantity (1.0, \"feet\") and Quantity (12.0, \"inches\")");
            System.out.println("Output: Equal (" + q1.equals(q2) + ")");

            com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.QuantityLength q3 = new com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.QuantityLength(1.0, com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.LengthUnit.INCHES);
            com.bridgelabz.wipro.uc3_genericquantityclassfordryprinciple.QuantityLength q4 = new QuantityLength(1.0, LengthUnit.INCHES);

            System.out.println("Input: Quantity (1.0, \"inch\") and Quantity (1.0, \"inch\")");
            System.out.println("Output: Equal (" + q3.equals(q4) + ")");
        }
    }
