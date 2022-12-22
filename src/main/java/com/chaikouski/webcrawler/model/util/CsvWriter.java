package com.chaikouski.webcrawler.model.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Viktar Chaikouski
 * <p>
 * The type CsvWriter.
 * <p>
 * This is a class that allows to write strings to the file defined in the application.properties.
 */
@Component
public class CsvWriter {
    private static final Logger logger = LogManager.getLogger();
    @Value("${csv.file-name}")
    private String fileName;

    /**
     * Write data to file.
     *
     * @param stringList the list of strings
     */
    public void writeDataToCsvFile(List<String> stringList) {
        Path filePath = Path.of(fileName);

        try {
            Files.write(filePath, stringList);
        } catch (IOException e) {
            logger.error(String.format("Unable to write in the file %S", fileName), e);
        }
    }
}