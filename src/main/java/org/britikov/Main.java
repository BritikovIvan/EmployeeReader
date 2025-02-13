package org.britikov;

import org.britikov.dto.EmployeeDto;
import org.britikov.dto.ValidationResult;
import org.britikov.exception.ParameterException;
import org.britikov.model.SortOrder;
import org.britikov.model.SortType;
import org.britikov.util.CompanyStringBuilder;
import org.britikov.util.EmployeeValidator;
import org.britikov.util.FileReader;
import org.britikov.util.ParameterUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        setLogger();

        try {
            // Get EmployeeDto from file
            List<EmployeeDto> employeeDtos = FileReader.readData(args[0]);

            // Validate employees
            ValidationResult validationResult = EmployeeValidator.validateEmployees(employeeDtos);

            // Get sort type and order from args
            Optional<SortType> sortType = Optional.empty();
            Optional<SortOrder> sortOrder = Optional.empty();
            try {
                sortType = ParameterUtil.getSortType(args);
                sortOrder = ParameterUtil.getSortOrder(args);
                if (sortType.isPresent() && sortOrder.isEmpty()) {
                    logger.warning("The sorting order is not set, so the employees are sorted in ascending order.");
                }
                if (sortType.isEmpty() && sortOrder.isPresent()) {
                    logger.warning("The sorting order cannot be used because the sorting type is not specified.");
                }
            } catch (ParameterException e) {
                logger.warning(e.getMessage());
            }

            // Get company description
            String description;
            if (sortType.isPresent()) {
                if (sortOrder.isPresent()) {
                    description = CompanyStringBuilder.createCompanyDescription(validationResult.getCompany(), sortType.get(), sortOrder.get());
                } else {
                    description = CompanyStringBuilder.createCompanyDescription(validationResult.getCompany(), sortType.get(), SortOrder.ASC);
                }
            } else {
                description = CompanyStringBuilder.createCompanyDescription(validationResult.getCompany());
            }

            // Concat company description with incorrect data
            StringBuilder sb = new StringBuilder(description);
            sb.append("Некорректные данные:").append("\n");
            validationResult.getIncorrectData().forEach(s -> sb.append(s).append("\n"));

            // Get output file path
            Optional<Path> optionalPath = Optional.empty();
            try {
                optionalPath = ParameterUtil.getOutputFilePath(args);
            } catch (ParameterException e) {
                logger.warning(e.getMessage());
            }

            // Output of the result
            if (optionalPath.isPresent()) {
                writeToFile(sb.toString(), optionalPath.get());
            } else {
                writeToConsole(sb.toString());
            }

        } catch (InvalidPathException e) {
            logger.warning("Incorrect file path. Please restart the application with the correct file path.");
        } catch (IOException e) {
            logger.warning("Error reading the file content. Please check the file path or correctness of the file contents and restart the application.");
        }
    }

    private static void setLogger() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%n");
    }

    private static void writeToConsole(String output) {
        System.out.print(output);
    }

    private static void writeToFile(String output, Path filePath) {
        try {
            Files.writeString(filePath, output);
        } catch (IOException e) {
            logger.warning("Error writing content to file. Please check the file path and restart the application.");
        }
    }
}