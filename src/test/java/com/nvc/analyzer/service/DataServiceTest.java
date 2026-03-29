package com.nvc.analyzer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.nvc.analyzer.model.NvcProcess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

class DataServiceTest {

    @TempDir
    Path tempDir;

    private DataService dataService;
    private String testFilePath;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test_nvc_data.json").toString();
        
        dataService = new DataService(testFilePath);
    }

    @Test
    void loadProcesses_fileDoesNotExist_returnsEmptyList() {

        List<NvcProcess> result = null;
        try {
            result = dataService.loadProcesses();
        } catch (IOException e) {
            System.err.println("An IOException occured while loading the processes: " + e.getMessage());
        }
        
        assertNotNull(result, "The list should not be null");
        assertTrue(result.isEmpty(), "The list should be empty when the file does not exist");
    }

    @Test
    void saveAndLoadProcesses_validData_returnsSavedData() {
        NvcProcess process1 = new NvcProcess(); 
        NvcProcess process2 = new NvcProcess();
        List<NvcProcess> processesToSave = Arrays.asList(process1, process2);

        try {
            dataService.saveProcesses(processesToSave);
        } catch (IOException e) {
            System.err.println("An IOException occured while saving the processes: " + e.getMessage());
        }
        List<NvcProcess> loadedProcesses = null;

        try {
            loadedProcesses = dataService.loadProcesses();
        } catch (IOException e) {
            System.err.println("An IOException occured while loading the processes: " + e.getMessage());
        }
        
        
        assertNotNull(loadedProcesses);
        assertEquals(2, loadedProcesses.size(), "There should have been exactly two processes.");
    }

    @Test
    void loadProcesses_invalidJson_returnsEmptyList() throws IOException {
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write("{ invalid JSON ]");
        }

        List<NvcProcess> result = dataService.loadProcesses();
        
        assertNotNull(result);
        assertTrue(result.isEmpty(), "The function should return an empty list when the JSON file was invalid.");
    }
}
