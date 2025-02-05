package org.britikov.entity;

import java.util.ArrayList;
import java.util.List;

public class FileReadingResult {
    private Company company;
    private final List<String> incorrectData = new ArrayList<>();

    public FileReadingResult() {}

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void addIncorrectData(String data) {
        incorrectData.add(data);
    }

    public List<String> getIncorrectData() {
        return incorrectData;
    }
}
