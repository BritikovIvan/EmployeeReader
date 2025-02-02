package org.britikov.entity;

import java.math.BigDecimal;

public abstract class BaseEmployee {
    private long id;
    private String name;
    private EmployeePosition position;
    private BigDecimal salary;

    public BaseEmployee(long id, String name, EmployeePosition position, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public BaseEmployee(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof BaseEmployee that)) return false;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
