package org.solace.scholar_ai.ongoing_research_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIAssistanceService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key:AIzaSyD1f5phqFOLvgC4zRnpPCd6EBQOegKHNuw}")
    private String geminiApiKey;

    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent";

    public Map<String, Object> reviewDocument(String content) {
        try {
            String prompt = String.format(
                    """
                Review this LaTeX document for academic writing quality. Provide scores and suggestions for:
                1. Clarity (0-1 score)
                2. Completeness (0-1 score)
                3. Grammar issues (list of specific problems)
                4. Style suggestions (list of improvements)

                Document content:
                %s

                Respond in JSON format with keys: clarityScore, completenessScore, grammarIssues, styleSuggestions
                """,
                    content);

            String response = callGeminiAPI(prompt);
            return parseGeminiResponse(response);
        } catch (Exception e) {
            log.error("Error reviewing document", e);
            return createDefaultReviewResponse();
        }
    }

    public String generateContextualSuggestions(String content, String context) {
        try {
            String prompt = String.format(
                    """
                Generate writing suggestions for this LaTeX document. Context: %s

                Document content:
                %s

                Provide specific, actionable suggestions for improving the document. Focus on:
                - Structure and organization
                - Academic writing style
                - LaTeX formatting
                - Content clarity

                Respond with clear, numbered suggestions.
                """,
                    context, content);

            return callGeminiAPI(prompt);
        } catch (Exception e) {
            log.error("Error generating suggestions", e);
            return "Unable to generate suggestions at this time.";
        }
    }

    public Map<String, Object> checkCompliance(String content, String venue) {
        try {
            String prompt = String.format(
                    """
                Check this LaTeX document for compliance with %s standards. Analyze:
                1. Word count
                2. Page count estimation
                3. Presence of abstract
                4. Presence of references
                5. IEEE compliance (if applicable)

                Document content:
                %s

                Respond in JSON format with keys: wordCount, pageCount, hasAbstract, hasReferences, ieeeCompliant
                """,
                    venue, content);

            String response = callGeminiAPI(prompt);
            return parseGeminiResponse(response);
        } catch (Exception e) {
            log.error("Error checking compliance", e);
            return createDefaultComplianceResponse();
        }
    }

    public Map<String, Object> validateCitations(String content) {
        try {
            String prompt = String.format(
                    """
                Validate citations in this LaTeX document. Check for:
                1. Number of citations found
                2. Number of references found
                3. Presence of bibliography section
                4. Citation format validity

                Document content:
                %s

                Respond in JSON format with keys: citationCount, referenceCount, hasBibliography, validCitations
                """,
                    content);

            String response = callGeminiAPI(prompt);
            return parseGeminiResponse(response);
        } catch (Exception e) {
            log.error("Error validating citations", e);
            return createDefaultCitationsResponse();
        }
    }

    public Map<String, Object> generateCorrections(String content) {
        try {
            String prompt = String.format(
                    """
                Generate corrections for this LaTeX document. Provide:
                1. Grammar corrections (specific fixes)
                2. Style improvements (enhancements)
                3. Structure suggestions (organization)

                Document content:
                %s

                Respond in JSON format with keys: grammarCorrections, styleImprovements, structureSuggestions
                """,
                    content);

            String response = callGeminiAPI(prompt);
            return parseGeminiResponse(response);
        } catch (Exception e) {
            log.error("Error generating corrections", e);
            return createDefaultCorrectionsResponse();
        }
    }

    public String processChatRequest(String selectedText, String userRequest, String fullDocument) {
        try {
            String prompt = String.format(
                    """
                You are an AI assistant for LaTeX document editing. The user has selected some text and made a request.

                Selected text: "%s"
                User request: "%s"
                Full document context: "%s"

                Your task is to:
                1. Understand what the user wants to do with the selected text
                2. Generate appropriate LaTeX code that fits the document structure
                3. Maintain consistency with the existing LaTeX formatting
                4. Provide only the LaTeX code that should replace or be inserted at the cursor position

                Examples:
                - If user says "make this a table", convert the selected data into a proper LaTeX table
                - If user says "add a figure here", create appropriate LaTeX figure code
                - If user says "format this as a list", convert to LaTeX list format
                - If user says "create a dummy table here", create a sample LaTeX table
                - If user says "add math equation", create appropriate LaTeX math code

                Respond with ONLY the LaTeX code, no explanations.
                """,
                    selectedText, userRequest, fullDocument);

            try {
                return callGeminiAPI(prompt);
            } catch (Exception apiError) {
                log.error("Gemini API call failed, using fallback response", apiError);
                // Fallback responses for common requests when API is unavailable
                String lowerRequest = userRequest.toLowerCase();
                if (lowerRequest.contains("table") || lowerRequest.contains("dummy table")) {
                    return "\\begin{table}[h]\n\\centering\n\\begin{tabular}{|c|c|c|}\n\\hline\nColumn 1 & Column 2 & Column 3 \\\\\n\\hline\nData 1 & Data 2 & Data 3 \\\\\nData 4 & Data 5 & Data 6 \\\\\n\\hline\n\\end{tabular}\n\\caption{Sample Table}\n\\label{tab:sample}\n\\end{table}";
                }
                if (lowerRequest.contains("figure")) {
                    return "\\begin{figure}[h]\n\\centering\n\\includegraphics[width=0.8\\textwidth]{figure.png}\n\\caption{Sample Figure}\n\\label{fig:sample}\n\\end{figure}";
                }
                if (lowerRequest.contains("list")) {
                    return "\\begin{itemize}\n\\item First item\n\\item Second item\n\\item Third item\n\\end{itemize}";
                }
                if (lowerRequest.contains("math") || lowerRequest.contains("equation")) {
                    return "$x^2 + y^2 = z^2$";
                }
                if (lowerRequest.contains("section")) {
                    return "\\section{New Section}\n\nYour content here.";
                }
                return "\\begin{comment}\nAI suggestion placeholder\n\\end{comment}\n\nYour requested content here.";
            }
        } catch (Exception e) {
            log.error("Error processing chat request", e);
            return "Unable to process request at this time.";
        }
    }

    private String callGeminiAPI(String prompt) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            Map<String, Object> content = new HashMap<>();
            Map<String, Object> part = new HashMap<>();

            part.put("text", prompt);
            content.put("parts", new Object[] {part});
            requestBody.put("contents", new Object[] {content});

            String url = GEMINI_API_URL + "?key=" + geminiApiKey;

            log.info("Calling Gemini API with URL: {}", url);
            Map<String, Object> response = restTemplate.postForObject(url, requestBody, Map.class);
            log.info("Gemini API response: {}", response);

            if (response != null && response.containsKey("candidates")) {
                Object candidates = response.get("candidates");
                log.info("Candidates type: {}, value: {}", candidates.getClass().getSimpleName(), candidates);

                if (candidates instanceof java.util.List) {
                    java.util.List<?> candidatesList = (java.util.List<?>) candidates;
                    if (!candidatesList.isEmpty()) {
                        Map<String, Object> candidate = (Map<String, Object>) candidatesList.get(0);
                        Map<String, Object> contentResponse = (Map<String, Object>) candidate.get("content");
                        Object parts = contentResponse.get("parts");

                        if (parts instanceof java.util.List) {
                            java.util.List<?> partsList = (java.util.List<?>) parts;
                            if (!partsList.isEmpty()) {
                                Map<String, Object> partResponse = (Map<String, Object>) partsList.get(0);
                                return (String) partResponse.get("text");
                            }
                        }
                    }
                }
            }

            log.warn("No valid response structure found in: {}", response);
            return "No response generated";
        } catch (Exception e) {
            log.error("Error calling Gemini API", e);
            throw new RuntimeException("Failed to call Gemini API", e);
        }
    }

    private Map<String, Object> parseGeminiResponse(String response) {
        try {
            return objectMapper.readValue(response, Map.class);
        } catch (Exception e) {
            log.error("Error parsing Gemini response", e);
            return new HashMap<>();
        }
    }

    private Map<String, Object> createDefaultReviewResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("clarityScore", 0.7);
        response.put("completenessScore", 0.6);
        response.put("grammarIssues", new HashMap<>());
        response.put("styleSuggestions", new HashMap<>());
        return response;
    }

    private Map<String, Object> createDefaultComplianceResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("wordCount", 0);
        response.put("pageCount", 0);
        response.put("hasAbstract", false);
        response.put("hasReferences", false);
        response.put("ieeeCompliant", false);
        return response;
    }

    private Map<String, Object> createDefaultCitationsResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("citationCount", 0);
        response.put("referenceCount", 0);
        response.put("hasBibliography", false);
        response.put("validCitations", false);
        return response;
    }

    private Map<String, Object> createDefaultCorrectionsResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("grammarCorrections", new HashMap<>());
        response.put("styleImprovements", new HashMap<>());
        response.put("structureSuggestions", new HashMap<>());
        return response;
    }
}
