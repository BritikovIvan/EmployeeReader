package org.britikov.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Manager extends BaseEmployee {
    private Department department;

    public Manager(long id) {
        super(id);
    }

    @Override
    public String toString() {
        return "Manager, " +
                super.getId() + ", " +
                super.getName() + ", " +
                super.getSalary();
    }
}
