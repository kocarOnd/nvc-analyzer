package com.nvc.analyzer.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class NvcValidatorTest {

    private final NvcValidator validator = new NvcValidator();

    @Test
    void testObservationValidation() {
        // This contains a judgment ("always")
        List<String> results = validator.analyzeObservation("You always leave the door open.");
        
        // So we expect at least one warning
        assertFalse(results.isEmpty(), "Should detect 'always' as a judgment");
        assertTrue(results.get(0).contains("Warning"));
    }

    @Test
    void testCleanObservation() {
        // This is a neutral observation
        List<String> results = validator.analyzeObservation("You left the door open this morning.");
        
        // So we expect no warnings
        assertTrue(results.isEmpty(), "Clean observation should have no warnings");
    }

    @Test
    void testFauxFeeling() {
        // "I feel that you are not being honest with me" is a thought, even judgement
        List<String> results = validator.analyzeFeeling("I feel that you are not being honest with me");

        assertFalse(results.isEmpty());
        assertTrue(results.get(0).contains("feel that"));
    }
}
