package com.bridgelabz.wipro.uc8_refactoringunitenumtostandalone;

public class Length {

    private static final double ABS_EPS_IN=5e-5;
    private static final double REL_EPS=1e-9;

    private double value;
    private LengthUnit unit;

    public Length(double value, LengthUnit unit) {
        if(!Double.isFinite(value)){
            throw new IllegalArgumentException("Value must not be null");
        }
        if(unit==null){
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public double toBaseFeet(){
        return unit.convertToBaseUnit(value);
    }

    private static boolean almostEqual(double a,double b){
        double diff=Math.abs(a-b);
        double scale=Math.max(1.0,Math.max(Math.abs(a),Math.abs(b)));
        double toll=Math.max(ABS_EPS_IN,REL_EPS*scale);
        return diff<=toll;
    }
    public boolean compare(Length that){
        return almostEqual(this.toBaseFeet(), that.toBaseFeet());
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
        double quantized=Math.round(this.toBaseFeet()/ABS_EPS_IN)*ABS_EPS_IN;
        return Double.hashCode(quantized);
    }

    @Override
    public String toString() {
            return "Length{ " + "value: " + value + ", unit: " + unit + "}";
    }
    public Length convertTo(LengthUnit targetUnit){
        if(targetUnit==null)
            throw new IllegalArgumentException("Target unit cannot be null");
        double valueInInches=this.toBaseFeet();
        double convertedValue=targetUnit.convertToBaseUnit(1.0)==0 ? 0 : valueInInches/ targetUnit.convertToBaseUnit(1.0);
        return new Length(convertedValue,targetUnit);
    }
    public  Length convertTo(double value, LengthUnit fromUnit, LengthUnit toUnit){
        if(!Double.isFinite(value))
            throw new IllegalArgumentException("value must not be null");
        if(fromUnit==null||toUnit==null)
            throw new IllegalArgumentException("Units must not be null");
        double valueInInches=fromUnit.convertToBaseUnit(value);
        double convertedValue=valueInInches/toUnit.getToFeetFactor();
        return new Length(convertedValue,toUnit);
    }
    public Length Add(Length thatLength){
        if(thatLength==null)
            throw new IllegalArgumentException("Length cannot be null");
        double length1=thatLength.toBaseFeet();
        double length2=this.toBaseFeet();
        double sumlength=length1+length2;
        double sumlengthbaseunit=sumlength/this.unit.getToFeetFactor();
        return new Length(sumlengthbaseunit,this.unit);
    }
    private static double convertFromBaseToTargetUnit(double lengthInInches,LengthUnit targetUnit){
        return lengthInInches/targetUnit.getToFeetFactor();
    }
    public static Length Add(Length length1,Length length2,LengthUnit targetUnit){
            if(length1==null||length2==null)
                throw new IllegalArgumentException("length1 and length2 cannot be null");
            if(targetUnit==null)
                throw new IllegalArgumentException("Target unit cannot be null");
            double newlength1=length1.toBaseFeet();
            double newlength2=length2.toBaseFeet();
            double sumlength=newlength1+newlength2;
            double sumlengthbaseunit=convertFromBaseToTargetUnit(sumlength,targetUnit);
            return new Length(sumlengthbaseunit,targetUnit);
    }
}
