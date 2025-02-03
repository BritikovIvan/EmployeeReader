package org.britikov;

import org.britikov.entity.BaseEmployee;
import org.britikov.entity.Company;
import org.britikov.entity.Department;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public class EmployeeWriter {
    public static void writeToConsole(Company company) {
        Set<Department> departments = company.getDepartments();
        for (Department department : departments) {
            System.out.println(department.getName());
            System.out.println(department.getManager());
            department.getEmployees().forEach(System.out::println);
            int employeesAmount = department.getEmployees().size() + 1;
            BigDecimal totalSalary = department.getEmployees().stream()
                    .map(BaseEmployee::getSalary)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalSalary = totalSalary.add(department.getManager().getSalary());
            BigDecimal averageSalary = employeesAmount > 0 ? totalSalary.divide(BigDecimal.valueOf(employeesAmount), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
            System.out.println(employeesAmount + ", " + averageSalary);
        }
    }
}
