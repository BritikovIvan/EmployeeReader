package org.britikov;

import org.britikov.entity.Company;

public class EmployeeWriter {
    public static void writeToConsole(Company company) {
        company.getEmployees().forEach(System.out::println);
    }
}
