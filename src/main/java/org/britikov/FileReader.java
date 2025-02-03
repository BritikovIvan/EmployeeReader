package org.britikov;

import org.britikov.entity.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileReader {

    private FileReader() {
    }

    public static Company readData(String filePath) {
        Path path = Paths.get(filePath);

        try (var lines = Files.lines(path)) {
            Company company = new Company();
            Map<Long, List<Employee>> managerEmployees = new HashMap<>();
            List<BaseEmployee> employees = lines.map(s -> parsingLine(s, managerEmployees, company)).toList();

            for (Map.Entry<Long, List<Employee>> entry : managerEmployees.entrySet()) {
                Optional<BaseEmployee> optionalManager = employees.stream().filter(e -> e.getId() == entry.getKey()).findFirst();
                if (optionalManager.isEmpty()) {
                    throw new RuntimeException();
                }
                Manager manager = (Manager) optionalManager.get();
                entry.getValue().forEach(employee -> employee.setManager(manager));
                manager.getDepartment().setEmployees(entry.getValue());
            }

            employees.forEach(company::addEmployee);

            return company;
        } catch (IOException e) {
            // TODO exception
            throw new RuntimeException(e);
        }
    }

    private static BaseEmployee parsingLine(String line, Map<Long, List<Employee>> managerEmployees, Company company) {
        String[] employeeValues = line.split(",");
        // TODO Exception
        return createEmployee(employeeValues, EmployeePosition.valueOf(employeeValues[0].toUpperCase()), managerEmployees, company);
    }

    private static BaseEmployee createEmployee(String[] employeeValues, EmployeePosition position, Map<Long, List<Employee>> managerEmployees, Company company) {
        long id = Long.parseLong(employeeValues[1].trim());
        // TODO NumberFormatException
        String name = employeeValues[2];
        BigDecimal salary = new BigDecimal(employeeValues[3]);
        return switch (position) {
            case MANAGER -> {
                String departmentName = employeeValues[4].trim();
                Department department = new Department(departmentName);
                Manager manager = new Manager(id, name, EmployeePosition.MANAGER, salary, department);
                department.setManager(manager);
                company.addDepartment(department);
                yield manager;
            }
            case EMPLOYEE -> {
                Long managerId = Long.parseLong(employeeValues[4].trim());
                Employee employee = new Employee(id, name, EmployeePosition.EMPLOYEE, salary);
                if (managerEmployees.containsKey(managerId)) {
                    managerEmployees.get(managerId).add(employee);
                } else {
                    managerEmployees.put(managerId, new ArrayList<>());
                    managerEmployees.get(managerId).add(employee);
                }
                yield employee;
            }
        };
    }
}
