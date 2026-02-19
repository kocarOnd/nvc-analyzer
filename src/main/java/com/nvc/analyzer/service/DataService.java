package com.nvc.analyzer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvc.analyzer.model.NvcProcess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the connection between the application and user data.
 * Loads and saves the processes in form of {@code List<NvcProcess>}.
 */
public class DataService {

    private static final String FILE_PATH = "nvc_data.json";
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Saves the given processes to a JSON file.
     * @param processes List of NvcProcesses to be saved
     */
    public void saveProcesses(List<NvcProcess> processes) {
        try {
            mapper.writeValue(new File(FILE_PATH), processes);
            System.out.println("Saved " + processes.size() + " items.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads previously saved processes from nvc_data.json
     * @return List of NvcProcesses or an empty list if the file doesn't exist
     * or no processes have been saved.
     */
    public List<NvcProcess> loadProcesses() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>(); 
        }

        try {
            return mapper.readValue(file, new TypeReference<List<NvcProcess>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}