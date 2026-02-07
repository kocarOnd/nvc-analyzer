package com.nvc.analyzer.model;

import java.util.ArrayList;
import java.util.List;

public class NvcValidator {

    public List<String> analyzeObservation(String text) {
        List<String> warnings = new ArrayList<>();
        String lower = text.toLowerCase();

        if (lower.contains("always") || lower.contains("never") || lower.contains("constantly")) {
            warnings.add("⚠️ Warning: Words like 'always' or 'never' are often exaggerations/judgments. Can you be more specific about a single time?");
        }
        if (lower.contains("good") || lower.contains("bad") || lower.contains("wrong") || lower.contains("right")) {
            warnings.add("⚠️ Warning: 'Good/Bad/Right/Wrong' are evaluations. Can you describe the specific action instead?");
        }

        return warnings;
    }

    public List<String> analyzeFeeling(String text) {
        List<String> warnings = new ArrayList<>();
        String lower = text.toLowerCase();

        if (lower.startsWith("that") || lower.contains("feel that") || lower.contains("feel like")) {
            warnings.add("⚠️ Warning: 'I feel that/like...' is usually followed by a thought, not a feeling. Try starting with 'I feel [emotion]'.");
        }
        if (lower.contains("ignored") || lower.contains("betrayed") || lower.contains("attacked") || lower.contains("misunderstood")) {
            warnings.add("⚠️ Warning: Words like 'ignored' or 'attacked' describe what someone did to you, not how you feel. (Try: 'hurt', 'lonely', 'scared').");
        }

        return warnings;
    }
}