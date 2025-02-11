package org.britikov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.britikov.entity.BaseEmployee;

@Data
@AllArgsConstructor
public class EmployeeDto {
    private BaseEmployee employee;
    private String employeeString;
}
