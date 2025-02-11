package org.britikov.entity;

import org.britikov.model.Staff;

import java.math.BigDecimal;
import java.util.Objects;

public class Employee extends BaseEmployee {
    private Manager manager;

    public Employee(long id, String name, Staff position, BigDecimal salary) {
        super(id, name, position, salary);
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Employee employee)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(manager, employee.manager);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(manager);
        return result;
    }

    @Override
    public String toString() {
        return "Employee, " +
                super.getId() + ", " +
                super.getName() + ", " +
                super.getSalary();
    }
}
