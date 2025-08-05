package org.solace.scholar_ai.ongoing_research_service.service;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIAssistanceService {

    /**
     * Review document content for grammar, clarity, and completeness
     */
    public Map<String, Object> reviewDocument(String content) {
        Map<String, Object> review = new HashMap<>();

        // Grammar and style analysis
        review.put("grammarIssues", analyzeGrammar(content));
        review.put("styleSuggestions", analyzeStyle(content));
        review.put("clarityScore", calculateClarityScore(content));
        review.put("completenessScore", calculateCompletenessScore(content));

        return review;
    }

    /**
     * Generate contextual writing suggestions
     */
    public String generateContextualSuggestions(String content, String context) {
        // This would integrate with an AI service in a real implementation
        // For now, return structured suggestions based on content analysis
        StringBuilder suggestions = new StringBuilder();

        if (content.length() < 100) {
            suggestions.append("Consider expanding this section with more details and examples.\n");
        }

        if (!content.contains("\\section{") && !content.contains("\\subsection{")) {
            suggestions.append("Consider adding section headers to improve document structure.\n");
        }

        if (!content.contains("\\cite{") && !content.contains("\\ref{")) {
            suggestions.append("Consider adding citations to support your claims.\n");
        }

        if (context != null && context.toLowerCase().contains("conference")) {
            suggestions.append("For conference papers, ensure your abstract is concise (150-200 words).\n");
            suggestions.append("Focus on clear problem statement and specific contributions.\n");
        }

        return suggestions.toString();
    }

    /**
     * Check compliance with conference/journal rules
     */
    public Map<String, Object> checkCompliance(String content, String venue) {
        Map<String, Object> compliance = new HashMap<>();

        // Basic compliance checks
        compliance.put("wordCount", countWords(content));
        compliance.put("pageCount", estimatePageCount(content));
        compliance.put("hasAbstract", content.contains("\\begin{abstract}"));
        compliance.put(
                "hasReferences", content.contains("\\bibliography{") || content.contains("\\begin{thebibliography}"));

        // Venue-specific checks
        if (venue != null) {
            if (venue.toLowerCase().contains("ieee")) {
                compliance.put("ieeeCompliant", checkIEEERules(content));
            } else if (venue.toLowerCase().contains("acm")) {
                compliance.put("acmCompliant", checkACMRules(content));
            }
        }

        return compliance;
    }

    /**
     * Validate citation format
     */
    public Map<String, Object> validateCitations(String content) {
        Map<String, Object> validation = new HashMap<>();

        // Check for citation patterns
        int citeCount = countOccurrences(content, "\\cite{");
        int refCount = countOccurrences(content, "\\ref{");

        validation.put("citationCount", citeCount);
        validation.put("referenceCount", refCount);
        validation.put(
                "hasBibliography", content.contains("\\bibliography{") || content.contains("\\begin{thebibliography}"));

        // Citation format validation
        validation.put("validCitations", validateCitationFormat(content));

        return validation;
    }

    /**
     * Generate AI corrections with diff visualization
     */
    public Map<String, Object> generateCorrections(String content) {
        Map<String, Object> corrections = new HashMap<>();

        // Grammar corrections
        corrections.put("grammarCorrections", generateGrammarCorrections(content));

        // Style improvements
        corrections.put("styleImprovements", generateStyleImprovements(content));

        // Structure suggestions
        corrections.put("structureSuggestions", generateStructureSuggestions(content));

        return corrections;
    }

    // Helper methods
    private Map<String, String> analyzeGrammar(String content) {
        Map<String, String> issues = new HashMap<>();

        // Basic grammar checks (simplified)
        if (content.contains("  ")) {
            issues.put("doubleSpaces", "Remove double spaces");
        }

        if (content.contains("\\section{") && !content.contains("\\section{Introduction}")) {
            issues.put("missingIntroduction", "Consider adding an Introduction section");
        }

        return issues;
    }

    private Map<String, String> analyzeStyle(String content) {
        Map<String, String> suggestions = new HashMap<>();

        if (content.length() > 0) {
            suggestions.put("readability", "Consider using shorter sentences for better readability");
            suggestions.put("structure", "Ensure logical flow between sections");
        }

        return suggestions;
    }

    private double calculateClarityScore(String content) {
        // Simplified clarity scoring
        if (content.length() == 0) return 0.0;

        double score = 0.8; // Base score

        // Adjust based on content characteristics
        if (content.contains("\\section{")) score += 0.1;
        if (content.contains("\\cite{")) score += 0.05;
        if (content.contains("\\begin{abstract}")) score += 0.05;

        return Math.min(1.0, score);
    }

    private double calculateCompletenessScore(String content) {
        // Simplified completeness scoring
        if (content.length() == 0) return 0.0;

        double score = 0.6; // Base score

        // Adjust based on content characteristics
        if (content.contains("\\begin{abstract}")) score += 0.1;
        if (content.contains("\\section{Introduction}")) score += 0.1;
        if (content.contains("\\section{Conclusion}")) score += 0.1;
        if (content.contains("\\bibliography{") || content.contains("\\begin{thebibliography}")) score += 0.1;

        return Math.min(1.0, score);
    }

    private int countWords(String content) {
        // Remove LaTeX commands and count words
        String textOnly = content.replaceAll("\\\\[a-zA-Z]+\\{[^}]*\\}", "");
        textOnly = textOnly.replaceAll("\\\\[a-zA-Z]+", "");
        return textOnly.split("\\s+").length;
    }

    private int estimatePageCount(String content) {
        // Rough estimation: ~500 words per page
        int wordCount = countWords(content);
        return Math.max(1, wordCount / 500);
    }

    private boolean checkIEEERules(String content) {
        // Basic IEEE compliance check
        return content.contains("\\documentclass[conference]{IEEEtran}")
                || content.contains("\\documentclass[conference]{IEEEtran}");
    }

    private boolean checkACMRules(String content) {
        // Basic ACM compliance check
        return content.contains("\\documentclass[sigconf]{acmart}")
                || content.contains("\\documentclass[acmsmall]{acmart}");
    }

    private int countOccurrences(String content, String pattern) {
        int count = 0;
        int index = 0;
        while ((index = content.indexOf(pattern, index)) != -1) {
            count++;
            index += pattern.length();
        }
        return count;
    }

    private boolean validateCitationFormat(String content) {
        // Basic citation format validation
        return content.contains("\\cite{") || content.contains("\\ref{");
    }

    private Map<String, String> generateGrammarCorrections(String content) {
        Map<String, String> corrections = new HashMap<>();

        // Simple corrections
        if (content.contains("  ")) {
            corrections.put("doubleSpaces", "Replace double spaces with single spaces");
        }

        return corrections;
    }

    private Map<String, String> generateStyleImprovements(String content) {
        Map<String, String> improvements = new HashMap<>();

        if (content.length() > 0) {
            improvements.put("clarity", "Use active voice where possible");
            improvements.put("conciseness", "Remove unnecessary words and phrases");
        }

        return improvements;
    }

    private Map<String, String> generateStructureSuggestions(String content) {
        Map<String, String> suggestions = new HashMap<>();

        if (!content.contains("\\section{Introduction}")) {
            suggestions.put("introduction", "Add an Introduction section");
        }

        if (!content.contains("\\section{Conclusion}")) {
            suggestions.put("conclusion", "Add a Conclusion section");
        }

        return suggestions;
    }
}
