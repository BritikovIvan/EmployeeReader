package org.britikov.util;

import org.britikov.dto.EmployeeDto;
import org.britikov.dto.ValidationResult;
import org.britikov.entity.*;
import org.britikov.exception.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeValidator {
    private EmployeeValidator() {
    }

    public static ValidationResult validateEmployees(List<EmployeeDto> employeeDtos) {
        Company company = new Company();
        List<String> incorrectData = new ArrayList<>();

        employeeDtos.stream()
                .filter(employeeDto -> employeeDto.getEmployee() instanceof Manager)
                .forEach(employeeDto -> validateManager((Manager) employeeDto.getEmployee(), employeeDto.getEmployeeString(), company, incorrectData));

        employeeDtos.stream()
                .filter(employeeDto -> employeeDto.getEmployee() instanceof Employee)
                .forEach(employeeDto -> validateEmployee((Employee) employeeDto.getEmployee(), employeeDto.getEmployeeString(), company, incorrectData));

        return new ValidationResult(company, incorrectData);
    }

    private static void validateManager(Manager manager, String managerString, Company company, List<String> incorrectData) {
        try {
            // Check if id is already in use
            validateEmployeeId(manager.getId(), company, managerString);

            // Check salary is positive
            validateEmployeeSalary(manager.getSalary(), managerString);

            // Check if department is already has manager
            Optional<Department> optionalDepartment = company.findDepartmentByName(manager.getDepartment().getName());
            if (optionalDepartment.isPresent()) {
                throw new ValidationException("The department already has a manager.", managerString);
            }

            // Add employee and department to company
            company.addDepartment(manager.getDepartment());
            company.addEmployee(manager);

        } catch (ValidationException e) {
            incorrectData.add(e.getErrorLine());
        }
    }

    private static void validateEmployee(Employee employee, String employeeString, Company company, List<String> incorrectData) {
        try {
            // Check if id is already in use
            validateEmployeeId(employee.getId(), company, employeeString);

            // Check salary is positive
            validateEmployeeSalary(employee.getSalary(), employeeString);

            // Check that manager exists
            Optional<BaseEmployee> optionalManager = company.findEmployeeById(employee.getManager().getId());
            if (optionalManager.isEmpty() || !(optionalManager.get() instanceof Manager manager)) {
                throw new ValidationException("The employee does nOt have a manager.", employeeString);
            }
            employee.setManager(manager);

            // Add employee to department and company
            Department department = manager.getDepartment();
            department.addEmployee(employee);
            company.addEmployee(employee);

        } catch (ValidationException e) {
            incorrectData.add(e.getErrorLine());
        }
    }

    private static void validateEmployeeId(long id, Company company, String employeeString) throws ValidationException {
        Optional<BaseEmployee> optionalBaseEmployee = company.findEmployeeById(id);
        if (optionalBaseEmployee.isPresent()) {
            throw new ValidationException("The employee id must be unique. This id is already in use.", employeeString);
        }
    }

    private static void validateEmployeeSalary(BigDecimal salary, String employeeString) throws ValidationException {
        if (BigDecimal.ZERO.compareTo(salary) > 0) {
            throw new ValidationException("The employee's salary must be more than 0.", employeeString);
        }
    }
}
