package com.bridgelabz.wipro.java8_scenariobased.payrollprocessingsystem;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    private static List<Payroll> payrolls = List.of(
            new Payroll("E1", 50000, 5000, "IT"),
            new Payroll("E2", 70000, 10000, "IT"),
            new Payroll("E3", 40000, 3000, "HR"),
            new Payroll("E4", 45000, 5000, "HR")
    );

    public static void main(String[] args) {
        Map<String, Double> totalPayoutPerDept = payrolls.stream().
                collect(Collectors.groupingBy(Payroll::getDepartment, Collectors.summingDouble(p -> p.getBaseSalary() + p.getBonus())));
        totalPayoutPerDept.forEach((dept, totalPayout) -> System.out.println(dept + ": " + totalPayout));

        Optional<Payroll> highestPaid = payrolls.stream().max(Comparator.comparing(p -> p.getBaseSalary() + p.getBonus()));
        highestPaid.ifPresent(p -> System.out.println("Highest Paid Employee: " + p.getEmpId() + " with total compensation: " + (p.getBaseSalary() + p.getBonus())));

        List<String> highAvgDepartments = payrolls.stream().collect(Collectors.groupingBy(Payroll::getDepartment, Collectors.averagingDouble(p -> p.getBaseSalary() + p.getBonus())))
                .entrySet().stream().filter(e -> e.getValue() > 55000).map(Map.Entry::getKey).toList();
        System.out.println("Departments with average compensation > 55000: " + highAvgDepartments);

        Map<String, Double> empCompMap = payrolls.stream().collect(Collectors.toMap(Payroll::getEmpId, p -> p.getBaseSalary() + p.getBonus()));
        empCompMap.forEach((empId, totalComp) -> System.out.println(empId + "->" + totalComp));
        System.out.println(empCompMap);
    }
}
