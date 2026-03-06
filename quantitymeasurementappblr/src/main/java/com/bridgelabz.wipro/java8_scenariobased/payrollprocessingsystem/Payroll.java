package com.bridgelabz.wipro.java8_scenariobased.payrollprocessingsystem;

public class Payroll {
    String empId;
    double baseSalary;
    double bonus;
    String department;

    public Payroll(String empId, double baseSalary, double bonus, String department) {
        this.empId = empId;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
        this.department = department;
    }

    public String getEmpId() {
        return empId;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public double getBonus() {
        return bonus;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Payroll{" +
                "empId='" + empId + '\'' +
                ", baseSalary=" + baseSalary +
                ", bonus=" + bonus +
                ", department='" + department + '\'' +
                '}';
    }
}
