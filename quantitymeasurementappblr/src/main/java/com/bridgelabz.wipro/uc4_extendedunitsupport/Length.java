package com.bridgelabz.wipro.uc4_extendedunitsupport;

public class Length{

    private static final double ABS_EPS_IN = 5e-5;
    private static final double REL_EPS    = 1e-9;

    private final double value;
    private final LengthUnit unit;

    public Length(double value, LengthUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public enum LengthUnit {
    FEET(12.0),
    INCHES(1.0),
    YARDS(36.0),
    CENTIMETERS(1.0/2.54);

        private final double toInchesFactor;

        LengthUnit(double toInchesFactor) {
            this.toInchesFactor = toInchesFactor;
        }

        public double toInches(double v) {
            return v * toInchesFactor;
        }
    }

    private double toBaseInches() {
        return unit.toInches(value);


    }

    private static boolean almostEqual(double a, double b) {
        double diff  = Math.abs(a - b);
        double scale = Math.max(1.0, Math.max(Math.abs(a), Math.abs(b)));
        double tol   = Math.max(ABS_EPS_IN, REL_EPS * scale);
        return diff <= tol;
    }

    public boolean compare(Length that) {
        return almostEqual(this.toBaseInches(), that.toBaseInches());
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Length that = (Length) o;
        return compare(that);
    }

    @Override
    public int hashCode() {
        // Quantize to absolute tolerance so equal objects hash the same
        long bucket = Math.round(toBaseInches() / ABS_EPS_IN);
        return Long.hashCode(bucket);
    }

    // Optional, for nicer prints while debugging:
    @Override
    public String toString() {
        return value + " " + unit.name().toLowerCase();
    }

    public static void main(String[] args){
        Length length1= new Length(1.0,LengthUnit.FEET);
        Length length2=new Length(12.0,LengthUnit.INCHES);

        System.out.println("Are lengths equal? "+length1.equals(length2));

        Length length3= new Length(1.0,LengthUnit.YARDS);
        Length length4=new Length(36.0,LengthUnit.INCHES);

        System.out.println("Are lengths equal? "+length3.equals(length4));

        Length length5= new Length(100.0,LengthUnit.CENTIMETERS);
        Length length6=new Length(39.3701,LengthUnit.INCHES);

        System.out.println("Are lengths equal? "+length5.equals(length6));
    }

}


