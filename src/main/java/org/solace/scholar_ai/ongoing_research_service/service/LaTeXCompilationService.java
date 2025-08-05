package org.solace.scholar_ai.ongoing_research_service.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LaTeXCompilationService {

    private static final String PANDOC_PATH =
            System.getProperty("pandoc.path", "C:\\Users\\alami\\AppData\\Local\\Pandoc\\pandoc.exe");

    /**
     * Compile LaTeX to HTML using pandoc
     */
    public String compileLatexToHtml(String latexContent) {
        try {
            // Create temporary directory
            String tempDir = System.getProperty("java.io.tmpdir");
            String uniqueId = UUID.randomUUID().toString();
            Path workDir = Paths.get(tempDir, "latex_compile_" + uniqueId);
            Files.createDirectories(workDir);

            // Write LaTeX content to file
            Path texFile = workDir.resolve("document.tex");
            Files.write(texFile, latexContent.getBytes());

            // Try pandoc first
            try {
                ProcessBuilder pb = new ProcessBuilder(
                        PANDOC_PATH,
                        texFile.toString(),
                        "-f",
                        "latex",
                        "-t",
                        "html",
                        "--standalone",
                        "--mathjax",
                        "--css",
                        "https://cdn.jsdelivr.net/npm/katex@0.16.0/dist/katex.min.css");
                pb.directory(workDir.toFile());
                pb.redirectErrorStream(true);

                Process process = pb.start();
                String output = readProcessOutput(process);
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    // Read the generated HTML file
                    Path htmlFile = workDir.resolve("document.html");
                    if (Files.exists(htmlFile)) {
                        String html = Files.readString(htmlFile, StandardCharsets.UTF_8);
                        cleanupDirectory(workDir);
                        return enhanceHtmlOutput(html);
                    }
                }
            } catch (Exception pandocError) {
                // Pandoc failed, will fall back to manual conversion
                System.out.println("Pandoc compilation failed, using fallback: " + pandocError.getMessage());
            }

            // Clean up and fall back to manual conversion
            cleanupDirectory(workDir);
            return compileLatexFallback(latexContent);

        } catch (Exception e) {
            return createErrorHtml("Compilation error: " + e.getMessage());
        }
    }

    /**
     * Generate PDF from LaTeX using pandoc
     */
    public ResponseEntity<Resource> generatePDF(String latexContent, String filename) {
        try {
            // Create temporary directory
            String tempDir = System.getProperty("java.io.tmpdir");
            String uniqueId = UUID.randomUUID().toString();
            Path workDir = Paths.get(tempDir, "latex_pdf_" + uniqueId);
            Files.createDirectories(workDir);

            // Write LaTeX content to file
            Path texFile = workDir.resolve("document.tex");
            Files.write(texFile, latexContent.getBytes());

            // Generate PDF using pandoc
            ProcessBuilder pb = new ProcessBuilder(
                    PANDOC_PATH,
                    texFile.toString(),
                    "-f",
                    "latex",
                    "-t",
                    "pdf",
                    "--pdf-engine=xelatex",
                    "-o",
                    workDir.resolve("document.pdf").toString());
            pb.directory(workDir.toFile());
            pb.redirectErrorStream(true);

            Process process = pb.start();
            String output = readProcessOutput(process);
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                Path pdfFile = workDir.resolve("document.pdf");
                if (Files.exists(pdfFile)) {
                    byte[] pdfBytes = Files.readAllBytes(pdfFile);
                    ByteArrayResource resource = new ByteArrayResource(pdfBytes) {
                        @Override
                        public String getFilename() {
                            return filename + ".pdf";
                        }
                    };

                    // Clean up in background
                    cleanupDirectoryAsync(workDir);

                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + ".pdf\"")
                            .contentType(MediaType.APPLICATION_PDF)
                            .body(resource);
                }
            }

            // Clean up
            cleanupDirectory(workDir);
            throw new RuntimeException("PDF generation failed: " + output);

        } catch (Exception e) {
            // If pandoc is not available, use fallback
            try {
                return generatePDFFallback(latexContent, filename);
            } catch (Exception fallbackError) {
                throw new RuntimeException("PDF generation error: " + e.getMessage() + ". Fallback also failed: "
                        + fallbackError.getMessage());
            }
        }
    }

    /**
     * Fallback LaTeX to HTML conversion (when pandoc is not available)
     */
    public String compileLatexFallback(String latexContent) {
        if (latexContent == null || latexContent.trim().isEmpty()) {
            return "<div style='padding: 20px; color: #666;'>No content to compile</div>";
        }

        try {
            String html = latexContent;

            // Remove document setup
            html = html.replaceAll("\\\\documentclass(?:\\[.*?\\])?\\{.*?\\}", "");
            html = html.replaceAll("\\\\usepackage(?:\\[.*?\\])?\\{.*?\\}", "");
            html = html.replace("\\begin{document}", "");
            html = html.replace("\\end{document}", "");

            // Handle sections
            html = html.replaceAll(
                    "\\\\section\\*?\\{([^}]+)\\}",
                    "<h2 style='color: #2c3e50; font-size: 18px; margin: 30px 0 15px 0; font-weight: bold;'>$1</h2>");
            html = html.replaceAll(
                    "\\\\subsection\\*?\\{([^}]+)\\}",
                    "<h3 style='color: #34495e; font-size: 16px; margin: 25px 0 10px 0; font-weight: bold;'>$1</h3>");

            // Handle abstract
            html = html.replaceAll(
                    "\\\\begin\\{abstract\\}([\\s\\S]*?)\\\\end\\{abstract\\}",
                    "<div style='background: #f8f9fa; padding: 15px; margin: 20px 0; border-left: 4px solid #007bff;'><strong>Abstract:</strong>$1</div>");

            // Handle title and author
            html = html.replaceAll(
                    "\\\\title\\{([^}]+)\\}",
                    "<h1 style='color: #2c3e50; font-size: 24px; margin: 20px 0; text-align: center;'>$1</h1>");
            html = html.replaceAll(
                    "\\\\author\\{([^}]+)\\}",
                    "<p style='text-align: center; color: #7f8c8d; margin: 10px 0 30px 0;'>$1</p>");

            // Handle text formatting
            html = html.replaceAll("\\\\textbf\\{([^}]+)\\}", "<strong>$1</strong>");
            html = html.replaceAll("\\\\textit\\{([^}]+)\\}", "<em>$1</em>");
            html = html.replaceAll("\\\\emph\\{([^}]+)\\}", "<em>$1</em>");

            // Handle lists
            html = html.replaceAll("\\\\begin\\{itemize\\}", "<ul style='margin: 10px 0; padding-left: 20px;'>");
            html = html.replaceAll("\\\\end\\{itemize\\}", "</ul>");
            html = html.replaceAll("\\\\item\\s*", "<li style='margin: 5px 0;'>");

            // Handle equations
            html = html.replaceAll(
                    "\\\\begin\\{equation\\}([\\s\\S]*?)\\\\end\\{equation\\}",
                    "<div style='text-align: center; margin: 20px 0; padding: 10px; background: #f8f9fa; border-radius: 5px;'>$$1$</div>");
            html = html.replaceAll("\\$([^$]+)\\$", "<span style='font-style: italic;'>$1</span>");

            // Handle line breaks
            html = html.replaceAll("\\\\\\\\", "<br>");
            html = html.replaceAll("\\n\\s*\\n", "</p><p>");

            // Clean up
            html = html.replaceAll("\\\\[a-zA-Z]+(?:\\[.*?\\])?(?:\\{[^}]*\\})*", "");
            html = html.replaceAll("%[^\\n]*", "");

            // Wrap in paragraphs
            html = "<p>" + html + "</p>";

            return "<div style='padding: 30px; background: white; color: #212529; font-family: Georgia, serif; line-height: 1.6; max-width: 100%; overflow-wrap: break-word;'>"
                    + html + "</div>";

        } catch (Exception e) {
            return "<div style='padding: 20px; color: red;'>Compilation error: " + e.getMessage() + "</div>";
        }
    }

    private String readProcessOutput(Process process) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
    }

    private void cleanupDirectory(Path directory) {
        try {
            Files.walk(directory).sorted((a, b) -> b.compareTo(a)).forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    // Ignore cleanup errors
                }
            });
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }

    private void cleanupDirectoryAsync(Path directory) {
        new Thread(() -> {
                    try {
                        Thread.sleep(5000); // Wait 5 seconds before cleanup
                        cleanupDirectory(directory);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                })
                .start();
    }

    private String enhanceHtmlOutput(String html) {
        // Add custom CSS for better styling
        String enhancedHtml = html.replace(
                "</head>",
                "<style>" + "body { font-family: Georgia, serif; line-height: 1.6; color: #212529; }"
                        + "h1, h2, h3 { color: #2c3e50; }"
                        + "h1 { font-size: 24px; margin: 20px 0; }"
                        + "h2 { font-size: 18px; margin: 30px 0 15px 0; }"
                        + "h3 { font-size: 16px; margin: 25px 0 10px 0; }"
                        + "p { margin: 10px 0; }"
                        + "ul, ol { margin: 10px 0; padding-left: 20px; }"
                        + "li { margin: 5px 0; }"
                        + ".math { font-style: italic; }"
                        + "</style></head>");

        return enhancedHtml;
    }

    private String createErrorHtml(String errorMessage) {
        return "<div style='padding: 20px; color: red; background: #ffe6e6; border: 1px solid #ff9999; border-radius: 5px;'>"
                + "<strong>Compilation Error:</strong><br>" + errorMessage + "</div>";
    }

    /**
     * Fallback PDF generation method (when pandoc is not available)
     */
    private ResponseEntity<Resource> generatePDFFallback(String latexContent, String filename) {
        try {
            // Convert LaTeX to HTML first
            String htmlContent = compileLatexFallback(latexContent);

            // Create a simple HTML document
            String fullHtml = "<!DOCTYPE html>\n" + "<html>\n"
                    + "<head>\n"
                    + "    <meta charset=\"UTF-8\">\n"
                    + "    <title>"
                    + filename + "</title>\n" + "    <style>\n"
                    + "        body { font-family: 'Times New Roman', serif; margin: 40px; line-height: 1.6; }\n"
                    + "        h1, h2, h3 { color: #2c3e50; }\n"
                    + "        .abstract { background: #f8f9fa; padding: 15px; margin: 20px 0; border-left: 4px solid #007bff; }\n"
                    + "    </style>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + htmlContent
                    + "\n" + "</body>\n"
                    + "</html>";

            // For now, return HTML as a downloadable file since we can't generate PDF without external tools
            byte[] htmlBytes = fullHtml.getBytes(StandardCharsets.UTF_8);
            ByteArrayResource resource = new ByteArrayResource(htmlBytes) {
                @Override
                public String getFilename() {
                    return filename + ".html";
                }
            };

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + ".html\"")
                    .contentType(MediaType.TEXT_HTML)
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException("Fallback PDF generation failed: " + e.getMessage());
        }
    }
}
