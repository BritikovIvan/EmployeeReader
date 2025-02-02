package org.britikov.entity;

import java.math.BigDecimal;

public class Employee extends BaseEmployee {
    private Manager manager;

    public Employee(long id, String name, EmployeePosition position, BigDecimal salary, Manager manager) {
        super(id, name, position, salary);
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Employee, " +
                super.getId() + ", " +
                super.getName() + ", " +
                super.getSalary();
    }
}
