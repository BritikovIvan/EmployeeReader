package org.britikov;

import org.britikov.entity.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {

    public static Company readData(String filePath) {
        Path path = Paths.get(filePath);

        try(var lines = Files.lines(path)) {
            Company company = new Company();
            lines.forEach(s -> parsingLine(s, company));
            return company;
        } catch (IOException e) {
            // TODO exception
            throw new RuntimeException(e);
        }
    }

    private static void parsingLine(String line, Company company) {
        String[] employeeValues = line.split(",");
        // TODO Exception
        switch (employeeValues[0]) {
            case "Manager" -> createEmployee(employeeValues, EmployeePosition.MANAGER, company);
            case "Employee" -> createEmployee(employeeValues, EmployeePosition.EMPLOYEE, company);
            default -> {
                // TODO Exception
            }
        };
    }

    private static void createEmployee(String[] employeeValues, EmployeePosition position, Company company) {
        long id = Long.parseLong(employeeValues[1].trim());
        // TODO NumberFormatException
        String name = employeeValues[2];
        BigDecimal salary = new BigDecimal(employeeValues[3]);
        switch (position) {
            case MANAGER -> {
                String departmentName = employeeValues[4].trim();
                Department department = company.addDepartment(new Department(departmentName));
                Manager manager = new Manager(id, name, EmployeePosition.MANAGER, salary, department);
                department.setManager(manager);
                company.addEmployee(manager);
            }
            case EMPLOYEE -> {
                long managerId = Long.parseLong(employeeValues[4].trim());
                Manager manager = (Manager) company.addEmployee(new Manager(managerId));
                // TODO check if it is Manager
                Employee employee = new Employee(id, name, EmployeePosition.EMPLOYEE,  salary, manager);
                company.addEmployee(employee);
            }
        }
    }
}
