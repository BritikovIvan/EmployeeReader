package org.britikov.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.britikov.model.Staff;

import java.math.BigDecimal;

@Data
@SuperBuilder
public abstract class BaseEmployee {
    private long id;
    private String name;
    private Staff staff;
    private BigDecimal salary;

    protected BaseEmployee(long id) {
        this.id = id;
    }
}
