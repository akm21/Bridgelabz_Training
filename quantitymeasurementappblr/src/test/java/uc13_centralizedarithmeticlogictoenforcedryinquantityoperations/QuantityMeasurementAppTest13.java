package uc13_centralizedarithmeticlogictoenforcedryinquantityoperations;

import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.LengthUnit;
import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.VolumeUnit;
import com.bridgelabz.wipro.uc12_subtractionanddivisionoperationsonquantitymeasurements.WeigthUnit;
import com.bridgelabz.wipro.uc13_centralizedarithmeticlogictoenforcedryinquantityoperations.ArithmeticOperation;
import com.bridgelabz.wipro.uc13_centralizedarithmeticlogictoenforcedryinquantityoperations.Quantity;
import com.bridgelabz.wipro.uc13_centralizedarithmeticlogictoenforcedryinquantityoperations.QuantityMeasurementApp;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class QuantityMeasurementAppTest13 {

    //Quantity quantity;
    @Test
    public void testRefactoring_Add_DelegatesViaHelper() {


        Quantity<LengthUnit> spyQuantity = Mockito.spy(new Quantity<>(10.0, LengthUnit.FEET));
        Quantity<LengthUnit> other = new Quantity<>(5.0, LengthUnit.FEET);

        spyQuantity.add(other);//times().....
        verify(spyQuantity, times(1)).performBaseArithmetic(other, ArithmeticOperation.ADD);
    }

    @Test
    public void testRefactoring_Subtract_DelegatesViaHelper() {
        Quantity<LengthUnit> spyQuantity = Mockito.spy(new Quantity<>(10.0, LengthUnit.FEET));
        Quantity<LengthUnit> other = new Quantity<>(5.0, LengthUnit.FEET);

        spyQuantity.subtract(other);
        verify(spyQuantity, times(1)).performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
    }

    @Test
    public void testRefactoring_Divide_DelegatesViaHelper() {
        Quantity<LengthUnit> spyQuantity = Mockito.spy(new Quantity<>(10.0, LengthUnit.FEET));
        Quantity<LengthUnit> other = new Quantity<>(5.0, LengthUnit.FEET);

        spyQuantity.divide(other);
        verify(spyQuantity, times(1)).performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    @Test
    public void testValidation_NullOperand_ConsistentAcrossOperations() {
        Quantity<LengthUnit> quantity = new Quantity<>(10.0, LengthUnit.FEET);
        IllegalArgumentException addEx = assertThrows(IllegalArgumentException.class, () -> quantity.add(null));
        IllegalArgumentException subEx = assertThrows(IllegalArgumentException.class, () -> quantity.subtract(null));
        IllegalArgumentException divEx = assertThrows(IllegalArgumentException.class, () -> quantity.divide(null));

        assertEquals(addEx.getMessage(), subEx.getMessage());
        assertEquals(addEx.getMessage(), divEx.getMessage());
    }

    @Test
    public void testValidation_CrossCategory_ConsistentAcrossOperations() {
        Quantity<LengthUnit> length = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<WeigthUnit> weight = new Quantity<>(5.0, WeigthUnit.KILOGRAM);

        IllegalArgumentException addEx = assertThrows(IllegalArgumentException.class, () -> length.add((Quantity) weight));
        IllegalArgumentException subEx = assertThrows(IllegalArgumentException.class, () -> length.subtract((Quantity) weight));
        IllegalArgumentException divEx = assertThrows(IllegalArgumentException.class, () -> length.divide((Quantity) weight));

        assertEquals(addEx.getMessage(), subEx.getMessage());
        assertEquals(addEx.getMessage(), divEx.getMessage());
    }

    @Test
    public void testValidation_FiniteValue_ConsistentAcrossOperations() {
        Quantity<LengthUnit> quantity1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> quantity2 = new Quantity<>(Double.NaN, LengthUnit.FEET);
        Quantity<LengthUnit> quantity3 = new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET);

        IllegalArgumentException addEx = assertThrows(IllegalArgumentException.class, () -> quantity1.add(quantity2));
        IllegalArgumentException subEx = assertThrows(IllegalArgumentException.class, () -> quantity1.subtract(quantity2));
        IllegalArgumentException divEx = assertThrows(IllegalArgumentException.class, () -> quantity1.divide(quantity2));

        assertEquals(addEx.getClass(), subEx.getClass());
        assertEquals(addEx.getClass(), divEx.getClass());

        IllegalArgumentException addEx1 = assertThrows(IllegalArgumentException.class, () -> quantity1.add(quantity3));
        IllegalArgumentException subEx1 = assertThrows(IllegalArgumentException.class, () -> quantity1.subtract(quantity3));
        IllegalArgumentException divEx1 = assertThrows(IllegalArgumentException.class, () -> quantity1.divide(quantity3));

        assertEquals(addEx1.getClass(), subEx1.getClass());
        assertEquals(addEx1.getClass(), divEx1.getClass());
    }

    @Test
    public void testValidation_NullTargetUnit_AddSubtractReject() {
        try {
            Quantity quantity = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(5.0, LengthUnit.FEET), null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
        try {
            Quantity quantity = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(5.0, LengthUnit.FEET), null);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testArithmeticOperation_Add_EnumComputation() {
        double result = ArithmeticOperation.ADD.compute(10.0, 5.0);
        assertEquals(15.0, result);
    }

    @Test
    public void testArithmeticOperation_Subtract_EnumComputation() {
        double result = ArithmeticOperation.SUBTRACT.compute(10.0, 5.0);
        assertEquals(5.0, result);
    }

    @Test
    public void testArithmeticOperation_Divide_EnumComputation() {
        double result = ArithmeticOperation.DIVIDE.compute(10.0, 5.0);
        assertEquals(2.0, result);
    }

    @Test
    public void testArithmeticOperation_DivideByZero_EnumThrows() {
        try {
            double result = ArithmeticOperation.DIVIDE.compute(10.0, 0.0);
        } catch (ArithmeticException e) {
            assertTrue(true);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testPerformBaseArithmetic_ConversionAndOperation() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);

        double resultBase = q1.performBaseArithmetic(q2, ArithmeticOperation.ADD);
        assertEquals(2.0, resultBase);
    }

    @Test
    public void testAdd_UC12_BehaviorPreserved() {
        Quantity<LengthUnit> length = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES));
        assertEquals(2.0, length.getValue());

        Quantity<WeigthUnit> weight = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(10.0, WeigthUnit.KILOGRAM), new Quantity<>(5000.0, WeigthUnit.GRAM));
        assertEquals(15.0, weight.getValue());

        Quantity<VolumeUnit> volume = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(500.0, VolumeUnit.MILLILITRE));
        assertEquals(1.5, volume.getValue());
    }

    @Test
    public void testSubtract_UC12_BehaviorPreserved() {
        Quantity<LengthUnit> length = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES));
        assertEquals(9.0, length.getValue());

        Quantity<WeigthUnit> weight = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, WeigthUnit.KILOGRAM), new Quantity<>(5000.0, WeigthUnit.GRAM));
        assertEquals(5.0, weight.getValue());

        Quantity<VolumeUnit> volume = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
        assertEquals(3000.0, volume.getValue());
        assertEquals(VolumeUnit.MILLILITRE, volume.getUnit());
    }

    @Test
    public void testDivide_UC12_BehaviorPreserved() {
        Quantity<LengthUnit> length = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(5.0, LengthUnit.FEET));
        assertEquals(2.0, length.getValue());

        Quantity<LengthUnit> length1 = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(24.0, LengthUnit.INCHES), new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(1.0, length1.getValue());
    }

    @Test
    public void testRounding_AddSubtract_TwoDecimalPlaces() {
        Quantity<LengthUnit> addresult = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(1.33, LengthUnit.FEET));
        assertEquals(11.33, addresult.getValue());

        Quantity<LengthUnit> subresult = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(1.33, LengthUnit.FEET));
        assertEquals(8.67, subresult.getValue());
    }

    @Test
    public void testRounding_Divide_NoRounding() {
        Quantity<LengthUnit> result = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(3.0, LengthUnit.FEET));
        assertEquals(3.3333333333333335, result.getValue());
    }

    @Test
    public void testImplicitTargetUnit_AddSubtract() {
        Quantity<LengthUnit> addresult = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES));
        assertEquals(LengthUnit.FEET, addresult.getUnit());

        Quantity<LengthUnit> subresult = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES));
        assertEquals(LengthUnit.FEET, subresult.getUnit());
    }

    @Test
    public void testExplicitTargetUnit_AddSubtract_Overrides() {
        Quantity<LengthUnit> addresult = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.YARDS);
        assertEquals(LengthUnit.YARDS, addresult.getUnit());

        Quantity<LengthUnit> subresult = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.INCHES), new Quantity<>(12.0, LengthUnit.FEET), LengthUnit.YARDS);
        assertEquals(LengthUnit.YARDS, subresult.getUnit());
    }

    @Test
    public void testImmutability_AfterAdd_ViaCentralizedHelper() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> other = new Quantity<>(5.0, LengthUnit.FEET);

        Quantity<LengthUnit> result = original.add(other);
        assertEquals(10.0, original.getValue());
        assertEquals(LengthUnit.FEET, original.getUnit());

        assertEquals(15.0, result.getValue());
    }

    @Test
    public void testImmutability_AfterSubtract_ViaCentralizedHelper() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.INCHES);
        Quantity<LengthUnit> other = new Quantity<>(5.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result = original.subtract(other);
        assertEquals(10.0, original.getValue());
        assertEquals(LengthUnit.INCHES, original.getUnit());

        assertEquals(5.0, result.getValue());
    }

    @Test
    public void testImmutability_AfterDivide_ViaCentralizedHelper() {
        Quantity<VolumeUnit> original = new Quantity<>(10.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> other = new Quantity<>(5.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result = original.divide(other);
        assertEquals(10.0, original.getValue());
        assertEquals(VolumeUnit.LITRE, original.getUnit());

        assertEquals(2.0, result.getValue());
    }

    @Test
    public void testAllOperations_AcrossAllCategories() {
        Quantity<LengthUnit> lengthadd = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES));
        assertEquals(2.0, lengthadd.getValue());

        Quantity<LengthUnit> lengthsub = QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(2.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES));
        assertEquals(1.0, lengthsub.getValue());

        Quantity<WeigthUnit> weightadd = QuantityMeasurementApp.demonstrateAddition(new Quantity<>(10.0, WeigthUnit.KILOGRAM), new Quantity<>(1000.0, WeigthUnit.GRAM));
        assertEquals(11.0, weightadd.getValue());

        Quantity<WeigthUnit> weightdiv = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(10.0, WeigthUnit.KILOGRAM), new Quantity<>(2.0, WeigthUnit.KILOGRAM));
        assertEquals(5.0, weightdiv.getValue());

        Quantity<VolumeUnit> volumediv = QuantityMeasurementApp.demonstrateDivision(new Quantity<>(10.0, VolumeUnit.LITRE), new Quantity<>(2000.0, VolumeUnit.MILLILITRE));
        assertEquals(5.0, volumediv.getValue());
    }

    @Test
    public void testCodeDuplication_ValidationLogic_Eliminated() {
        Method[] methods = Quantity.class.getDeclaredMethods();

        int validationMethodCount = 0;
        for (Method method : methods) {
            if (method.getName().equals("validateArithmeticOperands") && Modifier.isPrivate(method.getModifiers())) {
                validationMethodCount++;
            }
        }
        assertEquals(1, validationMethodCount, "Validation logic should exist only once in helper method.");
    }

    @Test
    public void testCodeDuplication_ConversionLogic_Eliminated() {
        Method[] methods = Quantity.class.getDeclaredMethods();

        int conversionHelperCount = 0;
        for (Method method : methods) {
            if (method.getName().equals("performBaseArithmetic") && Modifier.isPublic(method.getModifiers())) {
                conversionHelperCount++;
            }
        }
        assertEquals(1, conversionHelperCount, "Conversion + arithmetic logic should exist only in helper.");
    }

    @Test
    public void testEnumDispatch_AllOperations_CorrectlyDispatched() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);

        assertEquals(15.0, q1.add(q2).getValue());
        assertEquals(5.0, q1.subtract(q2).getValue());
        assertEquals(2.0, q1.divide(q2).getValue());
    }

    @Test
    public void testFutureOperation_MultiplicationPattern() {
        double result = ArithmeticOperation.ADD.compute(3, 4);
        /*Similar to above Replace ADD with MULTIPLY Once implemented
                Expected pattern: MULTIPLY.compute(3,4)-> 12*/
        assertEquals(7.0, result);
    }

    @Test
    public void testErrorMessage_Consistency_Across_Operations() {
        Quantity<LengthUnit> quantity = new Quantity<>(10.0, LengthUnit.FEET);
        IllegalArgumentException addEx = assertThrows(IllegalArgumentException.class, () -> quantity.add(null));
        IllegalArgumentException subEx = assertThrows(IllegalArgumentException.class, () -> quantity.subtract(null));
        IllegalArgumentException divEx = assertThrows(IllegalArgumentException.class, () -> quantity.divide(null));
        assertEquals(addEx.getMessage(), subEx.getMessage());
        assertEquals(addEx.getMessage(), divEx.getMessage());
    }

    @Test
    public void testHelper_PublicVisibility() {
        Method[] methods = Quantity.class.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().equals("performBaseArithmetic") && Modifier.isPublic(method.getModifiers())) {
                assertTrue(true);
                return;
            }
        }
        assertTrue(false);
    }
    @Test
    public void testValidation_Helper_PrivateVisibility(){
        Method[] methods = Quantity.class.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().equals("validateArithmeticOperands") && Modifier.isPrivate(method.getModifiers())) {
                assertTrue(true);
                return;
            }
        }
        assertTrue(false);
    }
    @Test
    public void testRounding_Helper_Accuracy() throws Exception{
        Quantity<LengthUnit> quantity=new Quantity<>(1.0,LengthUnit.FEET);

        Method roundMethod=Quantity.class.getDeclaredMethod("round",double.class);
        roundMethod.setAccessible(true);
        java.lang.Object result =roundMethod.invoke(quantity,1.234567);
        double rounded= (double) result;

        assertEquals(1.23,rounded);
    }
    @Test
    public void testArithmetic_chain_Operations(){
        Quantity<LengthUnit> q1=new Quantity<>(10.0,LengthUnit.FEET);
        Quantity<LengthUnit> q2=new Quantity<>(2.0,LengthUnit.FEET);
        Quantity<LengthUnit> q3=new Quantity<>(1.0,LengthUnit.FEET);
        Quantity<LengthUnit> q4=new Quantity<>(3.0,LengthUnit.FEET);

        Quantity<LengthUnit> result=q1.add(q2).subtract(q3).divide(q4);

        assertEquals(11.0/3.0,result.getValue());
    }
    @Test
    public void testRefactoring_NoBehaviorChange_LargeDataset(){
        Quantity<LengthUnit> base=new Quantity<>(1000.0,LengthUnit.FEET);
        Quantity<LengthUnit> increment=new Quantity<>(1.0,LengthUnit.FEET);

        Quantity<LengthUnit> result=base;

        for(int i=0; i<1000; i++){
            result =result.add(increment);
        }
        assertEquals(2000.0,result.getValue());
    }
    @Test
    public void testRefactoring_Performance_ComparableToUC12(){
        Quantity<LengthUnit> q1=new Quantity<>(1000.0,LengthUnit.FEET);
        Quantity<LengthUnit> q2=new Quantity<>(2.0,LengthUnit.FEET);

        long start=System.nanoTime();
        for(int i=0;i<100000;i++){
            q1.add(q2);
            q1.subtract(q2);
            q1.divide(q2);
        }
        long end=System.nanoTime();
        long duration=end-start;

        assertTrue(duration<500000000L,"Performance degraded unexpectedly");
    }
    @Test
    public void testEnumConstant_ADD_CorrectlyAdds(){
        double result=ArithmeticOperation.ADD.compute(7,3);
        assertEquals(10.0,result);
    }
    @Test
    public void testEnumConstant_SUBTRACT_CorrectlySubtracts(){
        double result=ArithmeticOperation.SUBTRACT.compute(7,3);
        assertEquals(4.0,result);
    }
    @Test
    public void testEnumConstant_DIVIDE_CorrectlyDivides(){
        double result=ArithmeticOperation.DIVIDE.compute(7,2);
        assertEquals(7.0/2.0,result);
    }
    @Test
    public void testHelper_BaseUnitConversion_Correct(){
        Quantity<LengthUnit> q1=new Quantity<>(1.0,LengthUnit.FEET);
        Quantity<LengthUnit> q2=new Quantity<>(12.0,LengthUnit.INCHES);

        double baseresult=q1.performBaseArithmetic(q2,ArithmeticOperation.ADD);
        assertEquals(2.0,baseresult);
    }
    @Test
    public void testHelper_ResultConversion_Correct(){
        Quantity<LengthUnit> result=QuantityMeasurementApp.demonstrateAddition(new Quantity<>(2.0,LengthUnit.FEET),new Quantity<>(12.0,LengthUnit.INCHES),LengthUnit.YARDS);
        assertEquals(LengthUnit.YARDS,result.getUnit());

        Quantity<VolumeUnit> result1=QuantityMeasurementApp.demonstrateSubtraction(new Quantity<>(10.0,VolumeUnit.LITRE),new Quantity<>(1000.0,VolumeUnit.MILLILITRE),VolumeUnit.GALLON);
        assertEquals(VolumeUnit.GALLON,result1.getUnit());
    }
    @Test
    public void testRefactoring_Validation_UnifiedBehavior(){
        Quantity<LengthUnit> valid=new Quantity<>(10.0,LengthUnit.FEET);
        Quantity<WeigthUnit> invalidCategory=new Quantity<>(5.0,WeigthUnit.KILOGRAM);

        IllegalArgumentException addException=assertThrows(IllegalArgumentException.class,()->valid.add((Quantity)invalidCategory));
        IllegalArgumentException subException=assertThrows(IllegalArgumentException.class,()->valid.subtract((Quantity)invalidCategory));
        IllegalArgumentException divException=assertThrows(IllegalArgumentException.class,()->valid.divide((Quantity)invalidCategory));

        String expectedMessage=addException.getMessage();

        assertEquals(expectedMessage,subException.getMessage());
        assertEquals(expectedMessage,divException.getMessage());
    }
}
