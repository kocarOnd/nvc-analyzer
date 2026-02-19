package com.nvc.analyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Represents a single rule of NVC for analysis
 * This class holds keywords and a compiled regex pattern to identify
 * specific communication categories in text. There is also an optional
 * message component that holds a possible reply.
 * @version 1.0
 * @since 2026-02-17
 * @see <a href="https://en.wikipedia.org/wiki/Nonviolent_Communication">NVC on Wikipedia</a>
 */
public class NvcRule {
    /**The name of the rule (e.g. 'pseudo feeling' or 'exaggeration')*/
    public String category;

    /**List of keywords that trigger the rule*/
    public List<String> words;

    /**The feedback or guidance message associated with this rule*/
    public String message;

    @JsonIgnore
    private Pattern compiledPattern;

    /**
    * Compiles the list of words into a case-insensitive regular expression pattern.
    * If the word list is null or empty, the method returns without 
    * updating the internal pattern.
    */
    public void compile() {
        if (words == null || words.isEmpty()) return;
        
        String regex = "\\b(" + String.join("|", words) + ")\\b";
        this.compiledPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    /**
     * Returns the compiled pattern of rule-specific keywords.
     * @return {@code null} when the pattern has not been compiled yet, 
     * otherwise returns a {@code Pattern} object holding keywords.
     * @see #compile()
     */
    public Pattern getCompiledPattern() {
        return compiledPattern;
    }
}