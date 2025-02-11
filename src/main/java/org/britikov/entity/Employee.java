package org.britikov.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Employee extends BaseEmployee {
    private Manager manager;

    @Override
    public String toString() {
        return "Employee, " +
                super.getId() + ", " +
                super.getName() + ", " +
                super.getSalary();
    }
}

