package org.britikov.entity;

import org.britikov.model.Staff;

import java.math.BigDecimal;
import java.util.Objects;

public class Manager extends BaseEmployee {
    private Department department;

    public Manager(long id, String name, Staff position, BigDecimal salary) {
        super(id, name, position, salary);
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
    public final boolean equals(Object o) {
        if (!(o instanceof Manager manager)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(department, manager.department);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(department);
        return result;
    }

    @Override
    public String toString() {
        return "Manager, " +
                super.getId() + ", " +
                super.getName() + ", " +
                super.getSalary();
    }
}
