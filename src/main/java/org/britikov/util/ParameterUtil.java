package org.britikov.util;

import org.britikov.exception.ParameterException;
import org.britikov.model.SortOrder;
import org.britikov.model.SortType;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ParameterUtil {
    private ParameterUtil() {}

    public static Optional<SortType> getSortType(String[] args) throws ParameterException {
        try {
            for (String arg : args) {
                if (arg.startsWith("--sort=")) {
                    arg = arg.substring(7);
                    return Optional.of(SortType.valueOf(arg.toUpperCase()));
                } else if (arg.contains("-s=")) {
                    arg = arg.substring(3);
                    return Optional.of(SortType.valueOf(arg.toUpperCase()));
                }
            }
        } catch (IllegalArgumentException e) {
            throw new ParameterException("Incorrect sorting type value.");
        }
        return Optional.empty();
    }

    public static Optional<SortOrder> getSortOrder(String[] args) throws ParameterException {
        try {
            for (String arg : args) {
                if (arg.startsWith("--order=")) {
                    arg = arg.substring(8);
                    return Optional.of(SortOrder.valueOf(arg.toUpperCase()));
                }
            }
        } catch (IllegalArgumentException e) {
            throw new ParameterException("The sorting order is not set correctly, so the employees are sorted in ascending order.");
        }
        return Optional.empty();
    }

    public static Optional<Path> getOutputFilePath(String[] args) throws ParameterException {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("--output=file") || arg.equals("-o=file")) {
                if (args.length > i + 1 && args[i + 1].startsWith("--path=")) {
                    String path = args[i + 1].substring(7);
                    return Optional.of(Paths.get(path));
                } else {
                    throw new ParameterException("Error in the output parameters");
                }
            }
            if (arg.startsWith("--path=")) {
                throw new ParameterException("Error in the output parameters");
            }
        }
        return Optional.empty();
    }
}
