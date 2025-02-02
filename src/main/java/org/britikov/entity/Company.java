package org.britikov.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Company {
    private final Set<Department> departments = new HashSet<>();
    private final Set<BaseEmployee> employees = new HashSet<>();

    public Company() {
    }

    public Department addDepartment(Department department) {
        if (departments.add(department)) {
            for (Department existingDepartment : departments) {
                if (existingDepartment.equals(department)) {
                    return existingDepartment;
                }
            }
        }
        return department;
    }

    public BaseEmployee addEmployee(BaseEmployee employee) {
        if (!employees.add(employee)) {
            for (BaseEmployee existingManager : employees) {
                if (existingManager.equals(employee)) {
                    return existingManager;
                }
            }
        }
        return employee;
    }

    public Set<BaseEmployee> getDepartmentEmployees(Department department) {
        return employees.stream().filter(employee -> employee instanceof Employee).collect(Collectors.toSet());
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public Set<BaseEmployee> getEmployees() {
        return employees;
    }
}
