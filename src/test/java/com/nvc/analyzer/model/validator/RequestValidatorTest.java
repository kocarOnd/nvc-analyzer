package com.nvc.analyzer.model.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import com.nvc.analyzer.model.NvcValidator;

class RequestValidatorTest {

    private NvcValidator requestValidator;

    @BeforeEach
    void setUp() throws Exception {
        String path = "com/nvc/analyzer/rule/request_rules.json";
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        
        assertNotNull(is, "Could not find request_rules.json in resources");
        
        requestValidator = new NvcValidator("request", is);
    }

    @Test
    void testDemandWarning() {
       String input = "You must clean the kitchen now.";
        List<String> results = requestValidator.analyze(input);
    
        assertFalse(results.isEmpty(), "Should have flagged 'must' as a demand");
    }   

    @Test
    void testNegativeRequestWarning() {
        String input = "Don't be so loud.";
        List<String> results = requestValidator.analyze(input);
    
        assertFalse(results.isEmpty(), "Should have flagged 'don't' as a negative request");
    }

    @Test
    void testVagueRequestWarning() {
        String input = "Could you improve the situation?";
        List<String> results = requestValidator.analyze(input);
    
        assertFalse(results.isEmpty(), "Should have flagged 'improve' as a vague request");
    }
}

