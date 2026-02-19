package com.nvc.analyzer.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A generic validator of text for non-violent communication
 * Loads specific set of rules from a file and uses it to analyze 
 * textual input.
 * @version 1.0
 * @since 2026-02-19
 * @see <a href="https://en.wikipedia.org/wiki/Nonviolent_Communication">NVC on Wikipedia</a>
 * @see NvcRule
 */
public class NvcValidator {
    private final String type;
    private List<NvcRule> rules;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     *  Creates a new {@code NvcValidator} object with rules from the inputStream
     * @param type  The type of the validator (e.g. 'need' or 'request')
     * @param inputStream   {@code InputStream} object from which nvc rules are loaded
    */
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

    public String getType() { return type; }

    /**
     * Uses the loaded rules package to look for specific phrases in a given text
     * @param text  Text that we want to be analyzed by the validator
     * @return Empty List<String> object when the input text is blank or null, 
     * otherwise returns {@code List<String>} object holding a message of every
     * rule that was triggered.
     */
    public List<String> analyze(String text) {
        List<String> messages = new ArrayList<>();
        if (text == null || text.isBlank()) return messages;

        for (NvcRule rule : rules) {
            if (rule.getCompiledPattern() != null && 
                rule.getCompiledPattern().matcher(text).find()) {
                messages.add(rule.message);
            }
        }
        return messages;
    }
}