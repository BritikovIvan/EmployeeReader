package org.britikov;

import org.britikov.entity.*;
import org.britikov.exception.DataParsingException;
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

    public static FileReadingResult readData(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        try (var lines = Files.lines(path)) {
            FileReadingResult fileReadingResult = new FileReadingResult();
            Company company = new Company();
            fileReadingResult.setCompany(company);
            Map<Long, List<Employee>> managerEmployees = new HashMap<>();

            List<Optional<BaseEmployee>> optionalEmployees = lines
                    .map(s -> parseLine(s, managerEmployees, fileReadingResult))
                    .toList();
            List<BaseEmployee> employees = new ArrayList<>(optionalEmployees.stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList());

            for (Map.Entry<Long, List<Employee>> entry : managerEmployees.entrySet()) {
                Optional<BaseEmployee> optionalManager = employees.stream()
                        .filter(e -> e.getId() == entry.getKey())
                        .findFirst();
                if (optionalManager.isEmpty()) {
                    entry.getValue().forEach(company.getEmployees()::remove);
                    entry.getValue()
                            .forEach(employee -> fileReadingResult.addIncorrectData(employee.toString() + ", " + entry.getKey()));
                    continue;
                }
                Manager manager = (Manager) optionalManager.get();
                entry.getValue().forEach(employee -> employee.setManager(manager));
                manager.getDepartment().setEmployees(entry.getValue());
            }

            return fileReadingResult;
        }
    }

    private static Optional<BaseEmployee> parseLine(String line, Map<Long, List<Employee>> managerEmployees, FileReadingResult fileReadingResult) {
        try {
            String[] employeeValues = line.split(",");
            Company company = fileReadingResult.getCompany();
            // TODO trim values class or func
            employeeValues = Arrays.stream(employeeValues)
                    .map(String::trim)
                    .toArray(String[]::new);

            Staff position = Staff.valueOf(employeeValues[0].toUpperCase());
            // TODO parseValid ...
            long id = Long.parseLong(employeeValues[1]);
            Optional<BaseEmployee> optionalBaseEmployee = company.getEmployeeById(id);
            if (optionalBaseEmployee.isPresent()) {
                throw new DataParsingException("The employee id must be unique. This id is already in use.");
            }
            //
            String name = employeeValues[2];
            BigDecimal salary = new BigDecimal(employeeValues[3]);
            // TODO Zero compareTo
            if (salary.compareTo(BigDecimal.ZERO) <= 0) {
                throw new DataParsingException("The employee's salary must be more than 0.");
            }

            return switch (position) {
                case MANAGER -> {
                    String departmentName = employeeValues[4];
                    Manager manager = new Manager(id, name, Staff.MANAGER, salary);
                    Optional<Department> optionalDepartment = company.getDepartmentByName(departmentName);
                    Department department = optionalDepartment.orElseGet(() -> new Department(departmentName));
                    if (department.getManager() != null) {
                        throw new DataParsingException("The department already has a manager.");
                    }
                    department.setManager(manager);
                    manager.setDepartment(department);
                    company.addDepartment(department);
                    company.addEmployee(manager);
                    yield Optional.of(manager);
                }
                case EMPLOYEE -> {
                    Long managerId = Long.parseLong(employeeValues[4]);
                    Employee employee = new Employee(id, name, Staff.EMPLOYEE, salary);
                    if (managerEmployees.containsKey(managerId)) {
                        managerEmployees.get(managerId)
                                .add(employee);
                    } else {
                        managerEmployees.put(managerId, new ArrayList<>());
                        managerEmployees.get(managerId)
                                .add(employee);
                    }
                    company.addEmployee(employee);
                    yield Optional.of(employee);
                }
            };
        } catch (IllegalArgumentException | DataParsingException e) {
            fileReadingResult.addIncorrectData(line);
        }
        return Optional.empty();
    }
}
