package org.britikov.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class Department {
    private String name;
    @EqualsAndHashCode.Exclude
    private Manager manager;
    private List<Employee> employees;

    public Department(String name) {
        this.name = name;
    }
}
