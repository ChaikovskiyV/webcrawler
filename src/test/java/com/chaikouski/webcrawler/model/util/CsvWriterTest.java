package com.chaikouski.webcrawler.model.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
class CsvWriterTest {
    private CsvWriter writer;
    private List<String> data;
    private Path filePath;

    @BeforeAll
    void init() {
        writer = new CsvWriter();
    }

    @BeforeEach
    void setUp() throws IOException {
        filePath = Path.of("test-data-file");

        if(Files.exists(filePath)) {
            Files.delete(filePath);
        }

        data = List.of("There", "are", "several", "strings", "in", "this", "list");
    }

    @Test
    void writeDataToScvFileTest() throws IOException {
        writer.writeDataToCsvFile(data);
        List<String> lines = Files.readAllLines(filePath);

        assertArrayEquals(data.toArray(), lines.toArray());
    }
}