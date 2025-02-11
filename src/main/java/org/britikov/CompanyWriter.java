package org.britikov;

import org.britikov.entity.*;
import org.britikov.model.SortingType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CompanyWriter {

    private CompanyWriter() {}

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

    public static String createCompanyDescription(Company company, SortingType sortingType, boolean isAscending) {
        company.getDepartments().stream()
                .map(Department::getEmployees)
                .forEach(employees -> sortEmployees(employees, sortingType, isAscending));
        return createCompanyDescription(company);
    }

    private static void sortEmployees(List<Employee> employees, SortingType sortingType, boolean isAscending) {
        Comparator<Employee> comparator;
        if (sortingType == SortingType.NAME) {
            comparator = Comparator.comparing(Employee::getName);
        } else if (sortingType == SortingType.SALARY) {
            comparator = Comparator.comparing(Employee::getSalary);
        } else {
            return;
        }

        if (!isAscending) {
            comparator = comparator.reversed();
        }
        employees.sort(comparator);
    }
}
