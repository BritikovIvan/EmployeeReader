package org.britikov.util;

import org.britikov.dto.EmployeeDto;
import org.britikov.dto.ValidationResult;
import org.britikov.entity.BaseEmployee;
import org.britikov.entity.Company;
import org.britikov.entity.Department;
import org.britikov.entity.Manager;
import org.britikov.exception.ValidationException;
import org.britikov.model.Staff;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeValidator {
    private EmployeeValidator() {}

    public static ValidationResult validateEmployees(List<EmployeeDto> employeeDtos) {
        Company company = new Company();
        List<String> incorrectData = new ArrayList<>();

        employeeDtos.forEach();

        return new ValidationResult(company, incorrectData);
    }

    private static void validateManager(Manager manager, String managerString, Company company, List<String> incorrectData) {
        try {
            // Check if id is already in use
            Optional<BaseEmployee> optionalBaseEmployee = company.findEmployeeById(manager.getId());
            if (optionalBaseEmployee.isPresent()) {
                throw new ValidationException("The employee id must be unique. This id is already in use.", managerString);
            }
            // Check salary is positive
            if (BigDecimal.ZERO.compareTo(manager.getSalary()) <= 0) {
                throw new ValidationException("The employee's salary must be more than 0.", managerString);
            }

            // Check if department is already has manager
            Optional<Department> optionalDepartment = company.findDepartmentByName(manager.getDepartment().getName());
            if (department.getManager() != null) {
                throw new DataParsingException("The department already has a manager.");
            }
        } catch (ValidationException e) {
            incorrectData.add(e.getErrorLine());
        }
    }
}
