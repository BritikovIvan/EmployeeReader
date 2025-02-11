package org.britikov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.britikov.entity.Company;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidationResult {
    private Company company;
    private List<String> incorrectData;
}
