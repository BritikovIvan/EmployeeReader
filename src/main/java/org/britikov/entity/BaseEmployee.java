package org.britikov.entity;

import org.britikov.model.Staff;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class BaseEmployee {
    private long id;
    private String name;
    private Staff position;
    private BigDecimal salary;

    protected BaseEmployee(long id, String name, Staff position, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    protected BaseEmployee(long id) {
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

    public Staff getPosition() {
        return position;
    }

    public void setPosition(Staff position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BaseEmployee that)) return false;

        return id == that.id && Objects.equals(name, that.name) && position == that.position && Objects.equals(salary, that.salary);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(position);
        result = 31 * result + Objects.hashCode(salary);
        return result;
    }
}
