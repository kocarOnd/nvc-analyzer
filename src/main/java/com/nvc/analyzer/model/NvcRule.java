package com.nvc.analyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.regex.Pattern;

public class NvcRule {
    public String category;
    public List<String> words;
    public String message;

    @JsonIgnore
    private Pattern compiledPattern;

    public void compile() {
        if (words == null || words.isEmpty()) return;
        
        String regex = "\\b(" + String.join("|", words) + ")\\b";
        this.compiledPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public Pattern getCompiledPattern() {
        return compiledPattern;
    }
}