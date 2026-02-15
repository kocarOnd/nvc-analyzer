package com.nvc.analyzer.model.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import com.nvc.analyzer.model.NvcValidator;

class FeelingValidatorTest {

    private NvcValidator feelingValidator;

    @BeforeEach
    void setUp() throws Exception {
        String path = "com/nvc/analyzer/rule/feeling_rules.json";
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        
        assertNotNull(is, "Could not find feeling_rules.json in resources");
        
        feelingValidator = new NvcValidator("feeling", is);
    }

    @Test
    void testPseudoFeelingDetection() {
        String input = "I feel ignored by my boss.";
        List<String> results = feelingValidator.analyze(input);
    
        assertFalse(results.isEmpty(), "Should have flagged 'ignored' as a pseudo-feeling");
        assertTrue(results.get(0).contains("pseudo-feeling"));
    }

    @Test
    void testThoughtDetection() {
        String input = "I feel like a burden.";
        List<String> results = feelingValidator.analyze(input);
    
        assertFalse(results.isEmpty(), "Should have flagged the use of 'like' after 'feel'");
    }

    @Test
    void testPronounDetection() {
        String input = "I feel it is useless.";
        List<String> results = feelingValidator.analyze(input);
    
        assertFalse(results.isEmpty(), "Should have flagged the use of 'it' after 'feel'");
    }

    @Test
    void testSelfJudgmentLabelDetection() {
        String input = "I feel incompetent when I make mistakes.";
        List<String> results = feelingValidator.analyze(input);
    
        assertFalse(results.isEmpty(), "Should have flagged 'incompetent' as a self-judgment");
        assertTrue(results.get(0).contains("self-judgment"), 
            "The warning should specify that this is a label, not a feeling.");
    }
}

