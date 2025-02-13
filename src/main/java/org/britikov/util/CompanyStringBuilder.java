package org.britikov.util;

import org.britikov.entity.*;
import org.britikov.model.SortOrder;
import org.britikov.model.SortType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CompanyStringBuilder {

    private CompanyStringBuilder() {}

    public static String createCompanyDescription(Company company) {
        List<Department> departments = new ArrayList<>(company.getDepartments());
        departments.sort(Comparator.comparing(Department::getName));
        StringBuilder sb = new StringBuilder();
        for (Department department : departments) {
            sb.append(department.getName()).append("\n");
            sb.append(department.getManager()).append("\n");
            List<Employee> employees = department.getEmployees();
            employees.forEach(employee -> sb.append(employee).append("\n"));
            int employeesAmount = department.getEmployees().size() + 1;
            BigDecimal totalSalary = department.getEmployees().stream()
                    .map(BaseEmployee::getSalary)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalSalary = totalSalary.add(department.getManager().getSalary());
            BigDecimal averageSalary = employeesAmount > 0 ? totalSalary.divide(BigDecimal.valueOf(employeesAmount), 2, RoundingMode.UP) : BigDecimal.ZERO;
            sb.append(employeesAmount).append(", ").append(averageSalary).append("\n");
        }
        return sb.toString();
    }

    public static String createCompanyDescription(Company company, SortType sortType, SortOrder sortOrder) {
        company.getDepartments().stream()
                .map(Department::getEmployees)
                .forEach(employees -> sortEmployees(employees, sortType, sortOrder));
        return createCompanyDescription(company);
    }

    private static void sortEmployees(List<Employee> employees, SortType sortType, SortOrder sortOrder) {
        Comparator<Employee> comparator;
        if (sortType == SortType.NAME) {
            comparator = Comparator.comparing(Employee::getName);
        } else if (sortType == SortType.SALARY) {
            comparator = Comparator.comparing(Employee::getSalary);
        } else {
            return;
        }

        if (sortOrder == SortOrder.DESC) {
            comparator = comparator.reversed();
        }
        employees.sort(comparator);
    }
}
