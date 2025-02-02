package org.britikov.entity;

import java.math.BigDecimal;

public class Manager extends BaseEmployee {
    private Department department;

    public Manager(long id, String name, EmployeePosition position, BigDecimal salary, Department department) {
        super(id, name, position, salary);
        this.department = department;
    }

    public Manager(long id) {
        super(id);
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Manager, " +
                super.getId() + ", " +
                super.getName() + ", " +
                super.getSalary();
    }
}
