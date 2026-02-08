package com.nvc.analyzer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvc.analyzer.model.NvcProcess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataService {

    private static final String FILE_PATH = "nvc_data.json";
    private final ObjectMapper mapper = new ObjectMapper();

    public void saveProcesses(List<NvcProcess> processes) {
        try {
            mapper.writeValue(new File(FILE_PATH), processes);
            System.out.println("Saved " + processes.size() + " items.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<NvcProcess> loadProcesses() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>(); 
        }

        try {
            // We need TypeReference to tell Jackson we want a List of NvcProcess objects - what's more, the TypeReference here is an annonymous subclass
            return mapper.readValue(file, new TypeReference<List<NvcProcess>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}