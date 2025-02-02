package org.britikov.entity;

import java.util.List;

public class Department {
    private String name;
    private Manager manager;
    private List<Employee> employees;

    public Department(String name) {
        this.name = name;
    }

    public Department(String name, Manager manager, List<Employee> employees) {
        this.name = name;
        this.manager = manager;
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Department that)) return false;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
