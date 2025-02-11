package org.britikov.util;

import org.britikov.dto.EmployeeDto;
import org.britikov.entity.*;
import org.britikov.model.Staff;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileReader {

    private FileReader() {
    }

    public static List<EmployeeDto> readData(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        //            for (Map.Entry<Long, List<Employee>> entry : managerEmployees.entrySet()) {
//                Optional<BaseEmployee> optionalManager = employees.stream()
//                        .filter(e -> e.getId() == entry.getKey())
//                        .findFirst();
//                if (optionalManager.isEmpty()) {
//                    entry.getValue().forEach(company.getEmployees()::remove);
//                    entry.getValue()
//                            .forEach(employee -> fileReadingResult.addIncorrectData(employee.toString() + ", " + entry.getKey()));
//                    continue;
//                }
//                Manager manager = (Manager) optionalManager.get();
//                entry.getValue().forEach(employee -> employee.setManager(manager));
//                manager.getDepartment().setEmployees(entry.getValue());
//            }

        try (var lines = Files.lines(path)) {
            return lines
                    .map(FileReader::parseLine)
                    .toList();
        }
    }

    private static EmployeeDto parseLine(String line) {
        try {
            String[] employeeValues = line.split(",");
            employeeValues = Arrays.stream(employeeValues)
                    .map(String::trim)
                    .toArray(String[]::new);

            // Parse staff
            Staff position = Staff.valueOf(employeeValues[0].toUpperCase());
            // Parse id
            long id = Long.parseLong(employeeValues[1]);
            // Parse name
            String employeeName = employeeValues[2];
            // Parse salary
            BigDecimal salary = new BigDecimal(employeeValues[3]);

            return switch (position) {
                case MANAGER -> {
                    // Parse manager department
                    String departmentName = employeeValues[4];
                    Department department = new Department(departmentName);

                    Manager manager = Manager.builder()
                            .id(id)
                            .name(employeeName)
                            .position(Staff.MANAGER)
                            .salary(salary)
                            .department(department)
                            .build();
                    department.setManager(manager);

                    yield new EmployeeDto(manager, line);
                }
                case EMPLOYEE -> {
                    // Parse employee manager
                    long managerId = Long.parseLong(employeeValues[4]);
                    Manager manager = new Manager(managerId);

                    Employee employee = Employee.builder()
                            .id(id)
                            .name(employeeName)
                            .position(Staff.EMPLOYEE)
                            .salary(salary)
                            .manager(manager)
                            .build();
                    yield new EmployeeDto(employee, line);
                }
            };
        } catch (IllegalArgumentException e) {
            return new EmployeeDto(null, line);
        }
    }
}
