package com.bridgelabz.wipro.uc9_weightmeasurement;

public class Weight {
    private static final double ABS_EPS_IN=5e-5;
    private static final double REL_EPS=1e-9;

    private double value;
    private WeightUnit unit;

    public Weight(double value, WeightUnit unit) {
        if(!Double.isFinite(value))
            throw new IllegalArgumentException("Value must not be null");
        if(unit==null)
            throw new IllegalArgumentException("Unit cannot be null");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    public double toBaseGram(){
        return unit.convertToBaseUnit(value);
    }
    private static boolean almostEqual(double a,double b){
        double diff=Math.abs(a-b);
        double scale=Math.max(1.0,Math.max(Math.abs(a),Math.abs(b)));
        double toll=Math.max(ABS_EPS_IN,REL_EPS*scale);
        return diff<=toll;
    }
    public boolean compare(Weight that){
        return almostEqual(this.toBaseGram(),that.toBaseGram());
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
           return true;
        if(obj==null||getClass()!=obj.getClass())
            return false;
        Weight that= (Weight) obj;
        return compare(that);
    }

    @Override
    public int hashCode() {
        double quantized=Math.round(this.toBaseGram()/ABS_EPS_IN)*ABS_EPS_IN;
        return Double.hashCode(quantized);
    }

    @Override
    public String toString() {
        return "Weight{  value: " + value + ", unit: " + unit + "}";
    }
    public Weight convertTo(WeightUnit targetUnit){
        if(targetUnit==null)
            throw new IllegalArgumentException("Target unit cannot be null");
        double valueInGrams=this.toBaseGram();
        double convertedValue= targetUnit.convertToBaseUnit(1.0)==0 ? 0 : valueInGrams/ targetUnit.convertToBaseUnit(1.0);
        return new Weight(convertedValue,targetUnit);
    }
    public Weight convertTo(double value,WeightUnit fromUnit,WeightUnit toUnit){
        if(!Double.isFinite(value))
            throw new IllegalArgumentException("Value cannot be null");
        if(fromUnit==null||toUnit==null)
            throw new IllegalArgumentException("Units cannot be null");
        double valueInGrams= fromUnit.convertToBaseUnit(value);
        double convertedValue=valueInGrams/toUnit.getToGramFactor();
        return new Weight(convertedValue,toUnit);
    }
    public Weight Add(Weight thatWeight){
        if(thatWeight==null)
            throw new IllegalArgumentException("Weight cannot be null");
        double weight1=thatWeight.toBaseGram();
        double weight2=this.toBaseGram();
        double sumweight=weight1+weight2;
        double sumweightbaseunit=sumweight/this.unit.getToGramFactor();
        return new Weight(sumweightbaseunit,this.unit);
    }
    private static double convertFromBasetoTargetUnit(double weightInGrams,WeightUnit targetUnit){
        return weightInGrams/targetUnit.getToGramFactor();
    }
    public static Weight Add(Weight weight1, Weight weight2, WeightUnit targetUnit){
        if(weight1==null||weight2==null)
            throw new IllegalArgumentException("Weight cannot be null");
        if(targetUnit==null)
            throw new IllegalArgumentException("Target Unit cannot be null");
        double newweight1=weight1.toBaseGram();
        double newweight2=weight2.toBaseGram();
        double sumweight=newweight1+newweight2;
        double sumweightbaseunit=convertFromBasetoTargetUnit(sumweight,targetUnit);
        return new Weight(sumweightbaseunit,targetUnit);
    }
}
