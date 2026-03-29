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

    private final String filePath;
    private final ObjectMapper mapper = new ObjectMapper();

    /**Creates a new DataService that loads and saves data to nvc_data.json */
    public DataService() {
        this.filePath = "nvc_data.json";
    }

    public DataService(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the given processes to a JSON file.
     * @param processes List of NvcProcesses to be saved
     * @throws IOException When a problem with nvc_data.json arrises 
     */
    public void saveProcesses(List<NvcProcess> processes) throws IOException {
        mapper.writeValue(new File(filePath), processes);
        System.out.println("Saved " + processes.size() + " items.");
    }

    /**
     * Loads previously saved processes from nvc_data.json
     * @return List of NvcProcesses or an empty list if the file doesn't exist
     * or no processes have been saved.
     * @throws IOException When the file exists, but the mapper could not map 
     * it's contents properly
     */
    public List<NvcProcess> loadProcesses() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>(); 
        }

        return mapper.readValue(file, new TypeReference<List<NvcProcess>>() {});
    }
}