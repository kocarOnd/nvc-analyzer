package com.nvc.analyzer.model.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import com.nvc.analyzer.model.NvcValidator;

class ObservationValidatorTest {

    private NvcValidator observationValidator;

    @BeforeEach
    void setUp() throws Exception {
        String path = "com/nvc/analyzer/rule/observation_rules.json";
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        
        assertNotNull(is, "Could not find observation_rules.json in resources");
        
        observationValidator = new NvcValidator("observation", is);
    }

    @Test
    void testExaggerationWarning() {
        String input = "You always leave your shoes in the hallway.";
        List<String> results = observationValidator.analyze(input);
        
        assertFalse(results.isEmpty(), "Expected a warning for the word 'always'");
        assertTrue(results.get(0).contains("exaggeration") || results.get(0).contains("always"),
            "Warning message should explain why 'always' was flagged.");
    }

    @Test
    void testWordBoundaries() {
        String input = "The badger was running around the garden yesterday.";
        List<String> results = observationValidator.analyze(input);
        
        assertTrue(results.isEmpty(), "Should not flag 'badger' just because it contains 'bad'");
    }

    @Test
    void testCaseInsensitivity() {
        String input = "I NEVER get to choose the movie.";
        List<String> results = observationValidator.analyze(input);
        
        assertFalse(results.isEmpty(), "Should detect uppercase 'NEVER'");
    }

    @Test
    void testMultipleWarnings() {
        // If a sentence has both an evaluation and an exaggeration
        String input = "It is wrong that you always forget.";
        List<String> results = observationValidator.analyze(input);
        
        assertTrue(results.size() >= 1, "Should detect at least one NVC violation");
    }
}
