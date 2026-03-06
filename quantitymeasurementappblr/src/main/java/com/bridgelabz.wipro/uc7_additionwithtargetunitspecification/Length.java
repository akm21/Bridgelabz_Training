package com.bridgelabz.wipro.uc7_additionwithtargetunitspecification;

public class Length {
    private static final double ABS_EPS_IN = 5e-5;
    private static final double REL_EPS = 1e-9;

    private double value;
    private LengthUnit unit;

    public Length(double value, LengthUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public enum LengthUnit {
        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(1.0 / 2.54);

        private final double toInchesFactor;

        LengthUnit(double toInchesFactor) {
            this.toInchesFactor = toInchesFactor;
        }

        public double toInches(double v){
             return v*toInchesFactor;
        }
    }
    public double toBaseInches(){
        return unit.toInches(value);
    }

    private static boolean almostEqual(double a,double b){
        double diff=Math.abs(a-b);
        double scale=Math.max(1.0,Math.max(Math.abs(a),Math.abs(b)));
        double tol=Math.max(ABS_EPS_IN,REL_EPS*scale);
        return diff<=tol;
    }
    public boolean compare(Length that){
        return almostEqual(this.toBaseInches(), that.toBaseInches());
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(obj==null||getClass()!=obj.getClass())
            return false;
        Length that= (Length) obj;
        return compare(that);
    }

    @Override
    public int hashCode() {
        double quantized=Math.round(this.toBaseInches()/ABS_EPS_IN)*ABS_EPS_IN;
        return Double.hashCode(quantized);
    }
    public Length convertTo(LengthUnit targetUnit){
        if(targetUnit==null)
            throw new IllegalArgumentException("Target unit cannot be null");
        double valueInInches=this.toBaseInches();
        double convertedValue=targetUnit.toInches(1.0)==0 ? 0: valueInInches/targetUnit.toInches(1.0);
        return new Length(convertedValue, targetUnit);
    }



    public Length Add(Length thatLength){
        if(thatLength==null)
            throw new IllegalArgumentException("Length cannot be null");
        double length1=thatLength.toBaseInches();
        double length2=this.toBaseInches();
        double sumlength=length1+length2;
        double sumlengthbaseunit=sumlength/this.unit.toInchesFactor;
        return new Length(sumlengthbaseunit,this.unit);
    }
    private static double convertFromBaseToTargetUnit(double lengthInInches, LengthUnit targetUnit){
        return lengthInInches/targetUnit.toInchesFactor;
    }
    public static Length Add(Length length1,Length length2,Length.LengthUnit targetUnit){
        if(length1==null || length2==null)
            throw new IllegalArgumentException("length1 and length2 cannot be null");
        if(targetUnit==null)
            throw new IllegalArgumentException("targetUnit cannot be null");
        double newlength1=length1.toBaseInches();
        double newlength2=length2.toBaseInches();
        double sumlength=newlength1+newlength2;
        double sumlengthbaseunit=convertFromBaseToTargetUnit(sumlength,targetUnit);
        return new Length(sumlengthbaseunit,targetUnit);
    }
    @Override
    public String toString() {
        return "Length{ " + "value: " + value + ", unit: " + unit + "}";
    }

}

