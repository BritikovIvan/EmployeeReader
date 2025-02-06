package org.britikov.entity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Company {
    private final Set<Department> departments = new HashSet<>();
    private final Set<BaseEmployee> employees = new HashSet<>();

    public Company() {
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public Optional<Department> getDepartmentByName(String departmentName) {
        return departments.stream()
                .filter(department -> department.getName().equals(departmentName))
                .findFirst();
    }

    public Optional<BaseEmployee> getEmployeeById(long id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst();
    }

    public void addEmployee(BaseEmployee employee) {
        employees.add(employee);
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
