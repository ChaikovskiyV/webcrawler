package com.chaikouski.webcrawler.model.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Viktar Chaikouski
 * <p>
 * The type CsvWriterTest.
 * <p>
 * This class includes tests for methods of the CsvWriter class.
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CsvWriterTest {
    @Autowired
    private CsvWriter writer;
    private List<String> data;
    private Path filePath;
    @Value("${csv.file-name}")
    private String fileName;

    /**
     * Inits test variables and test data.
     */
    @BeforeAll
    void init() {
        filePath = Path.of(fileName);
        data = List.of("There", "are", "several", "strings", "in", "this", "list");
    }

    /**
     * Removes a file with result if it exists.
     *
     * @throws IOException if an I/O error occurs
     */
    @BeforeEach
    void removeFileResultIfExist() throws IOException {
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }

    /**
     * Tests the writeDataToScvFile method whether it creates a file and writes data.
     *
     * @throws IOException if an I/O error occurs reading from the file
     */
    @Test
    void writeDataToScvFileTest() throws IOException {
        writer.writeDataToCsvFile(data);
        List<String> lines = Files.readAllLines(filePath);

        assertArrayEquals(data.toArray(), lines.toArray());
    }

    /**
     * Tests the writeDataToScvFile method whether it creates a file.
     */
    @Test
    void whetherFileCreatedTest() {
        writer.writeDataToCsvFile(data);

        assertTrue(Files.exists(filePath));
    }
}