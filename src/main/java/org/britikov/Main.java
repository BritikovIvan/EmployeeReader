package org.britikov;

import org.britikov.dto.EmployeeDto;
import org.britikov.dto.ValidationResult;
import org.britikov.model.SortingType;
import org.britikov.util.CompanyWriter;
import org.britikov.util.EmployeeValidator;
import org.britikov.util.FileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        setLogger();

        try {
            // Get EmployeeDto from file
            List<EmployeeDto> employeeDtos = FileReader.readData(args[0]);
            ValidationResult validationResult = EmployeeValidator.validateEmployees(employeeDtos);

            SortingType sortingType = getSortingType(args);
            Boolean isAscending = isAscendingOrder(args);
            String description;

            if (sortingType != null && isAscending != null) {
                description = CompanyWriter.createCompanyDescription(validationResult.getCompany(), sortingType, Boolean.TRUE.equals(isAscending));
            } else if (sortingType == null && isAscending != null) {
                logger.warning("Error in the sorting parameters");
                description = CompanyWriter.createCompanyDescription(validationResult.getCompany());
            } else if (sortingType != null) {
                logger.warning("The sorting order is not set or is not set correctly, so the employees are sorted in ascending order.");
                description = CompanyWriter.createCompanyDescription(validationResult.getCompany(), sortingType, Boolean.TRUE);
            } else {
                description = CompanyWriter.createCompanyDescription(validationResult.getCompany());
            }

            StringBuilder sb = new StringBuilder(description);
            sb.append("Некорректные данные:").append("\n");
            validationResult.getIncorrectData().forEach(s -> sb.append(s).append("\n"));

            Path path = getOutputFile(args);
            if (path != null) {
                writeToFile(sb.toString(), path);
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

    private static SortingType getSortingType(String[] args) {
        try {
            for (String arg : args) {
                if (arg.startsWith("--sort=")) {
                    arg = arg.substring(7);
                    return SortingType.valueOf(arg.toUpperCase());
                } else if (arg.contains("-s=")) {
                    arg = arg.substring(3);
                    return SortingType.valueOf(arg.toUpperCase());
                }
            }
        } catch (IllegalArgumentException e) {
            logger.warning("Incorrect sorting type value.");
        }
        return null;
    }

    private static Boolean isAscendingOrder(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--order=")) {
                arg = arg.substring(8);
                if (arg.equalsIgnoreCase("asc")) {
                    return true;
                }
                if (arg.equalsIgnoreCase("desc")) {
                    return false;
                }
            }
        }
        return null;
    }

    private static Path getOutputFile(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("--output=file") || arg.startsWith("-o=file")) {
                if (args.length > i + 1 && args[i + 1].startsWith("--path=")) {
                    String path = args[i + 1].substring(7);
                    return Paths.get(path);
                } else {
                    logger.warning("Error in the output parameters");
                }
                break;
            }
        }
        return null;
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