package com.nvc.analyzer.model.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import com.nvc.analyzer.model.NvcValidator;

class NeedValidatorTest {

    private NvcValidator needValidator;

    @BeforeEach
    void setUp() throws Exception {
        String path = "com/nvc/analyzer/rule/need_rules.json";
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        
        assertNotNull(is, "Could not find need_rules.json in resources");
        
        needValidator = new NvcValidator("need", is);
    }

    @Test
    void testStrategyWarning() {
       String input = "I need you to be quiet.";
        List<String> results = needValidator.analyze(input);
    
        assertFalse(results.isEmpty(), "Should have flagged 'you to' as a strategy");
    }   

    @Test
    void testUniversalNeedSuccess() {
        String input = "I have a need for autonomy and peace.";
        List<String> results = needValidator.analyze(input);
    
        assertFalse(results.isEmpty(), "Should have flagged 'autonomy' as a universal need");
    }
}

