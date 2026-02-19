package com.nvc.analyzer.model.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.InputStream;

import com.nvc.analyzer.model.NvcValidator;

class NvcValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {
    "com/nvc/analyzer/rule/observation_rules.json",
    "com/nvc/analyzer/rule/feeling_rules.json",
    "com/nvc/analyzer/rule/need_rules.json",
    "com/nvc/analyzer/rule/request_rules.json"
    })
    void testConfigurationFilesLoad(String path) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        
        assertDoesNotThrow(() -> {
            if (is == null) throw new Exception("File not found: " + path);
            new NvcValidator("test", is);
        });
    }
}
