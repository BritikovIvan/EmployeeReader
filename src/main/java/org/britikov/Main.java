package org.britikov;

import org.britikov.entity.Company;

public class Main {
    public static void main(String[] args) {
        Company company = FileReader.readData(args[0]);
        EmployeeWriter.writeToConsole(company);
    }
}