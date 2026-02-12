package com.nvc.analyzer.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NvcValidator {
    private final String type;
    private List<NvcRule> rules;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NvcValidator(String type, InputStream inputStream) throws IOException {
        this.type = type;
        loadRules(inputStream);
    }

    private void loadRules(InputStream inputStream) throws IOException {
        this.rules = objectMapper.readValue(inputStream, new TypeReference<List<NvcRule>>() {});
        for (NvcRule rule : rules) {
            rule.compile();
        }
    }

    public String getType() {
        return type;
    }

    public List<String> analyze(String text) {
        List<String> warnings = new ArrayList<>();
        if (text == null || text.isBlank()) return warnings;

        for (NvcRule rule : rules) {
            if (rule.getCompiledPattern() != null && 
                rule.getCompiledPattern().matcher(text).find()) {
                warnings.add(rule.message);
            }
        }
        return warnings;
    }
}