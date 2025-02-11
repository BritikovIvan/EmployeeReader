package org.britikov.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
public class Department {
    private String name;
    @EqualsAndHashCode.Exclude
    private Manager manager;
    @EqualsAndHashCode.Exclude
    private final List<Employee> employees = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
}
