package org.britikov.entity;

import lombok.Getter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
public class Company {
    private final Set<Department> departments = new HashSet<>();
    private final Set<BaseEmployee> employees = new HashSet<>();

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public Optional<Department> findDepartmentByName(String departmentName) {
        return departments.stream()
                .filter(department -> department.getName().equals(departmentName))
                .findFirst();
    }

    public Optional<BaseEmployee> findEmployeeById(long id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst();
    }

    public void addEmployee(BaseEmployee employee) {
        employees.add(employee);
    }
}
