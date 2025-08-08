package org.solace.scholar_ai.ongoing_research_service.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.solace.scholar_ai.ongoing_research_service.dto.common.APIResponse;
import org.solace.scholar_ai.ongoing_research_service.dto.request.CompileLatexRequestDTO;
import org.solace.scholar_ai.ongoing_research_service.dto.request.CreateDocumentRequestDTO;
import org.solace.scholar_ai.ongoing_research_service.dto.request.UpdateDocumentRequestDTO;
import org.solace.scholar_ai.ongoing_research_service.dto.response.DocumentResponseDTO;
import org.solace.scholar_ai.ongoing_research_service.service.DocumentService;
import org.solace.scholar_ai.ongoing_research_service.service.LaTeXCompilationService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Validated
public class DocumentController {

    private final DocumentService documentService;
    private final LaTeXCompilationService latexCompilationService;

    @PostMapping
    public ResponseEntity<APIResponse<DocumentResponseDTO>> createDocument(
            @Valid @RequestBody CreateDocumentRequestDTO request) {
        DocumentResponseDTO document = documentService.createDocument(request);
        APIResponse<DocumentResponseDTO> response = APIResponse.<DocumentResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Document created successfully")
                .data(document)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<APIResponse<List<DocumentResponseDTO>>> getDocumentsByProjectId(
            @PathVariable UUID projectId) {
        List<DocumentResponseDTO> documents = documentService.getDocumentsByProjectId(projectId);
        APIResponse<List<DocumentResponseDTO>> response = APIResponse.<List<DocumentResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Documents retrieved successfully")
                .data(documents)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<APIResponse<DocumentResponseDTO>> getDocumentById(@PathVariable UUID documentId) {
        DocumentResponseDTO document = documentService.getDocumentById(documentId);
        APIResponse<DocumentResponseDTO> response = APIResponse.<DocumentResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Document retrieved successfully")
                .data(document)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<APIResponse<DocumentResponseDTO>> updateDocument(
            @Valid @RequestBody UpdateDocumentRequestDTO request) {
        DocumentResponseDTO document = documentService.updateDocument(request);
        APIResponse<DocumentResponseDTO> response = APIResponse.<DocumentResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Document updated successfully")
                .data(document)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<APIResponse<Void>> deleteDocument(@PathVariable UUID documentId) {
        documentService.deleteDocument(documentId);
        APIResponse<Void> response = APIResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Document deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{documentId}/compile")
    public ResponseEntity<APIResponse<String>> compileDocument(@PathVariable UUID documentId) {
        DocumentResponseDTO document = documentService.getDocumentById(documentId);
        String compiledContent = documentService.compileLatex(document.getContent());
        APIResponse<String> response = APIResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Document compiled successfully")
                .data(compiledContent)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/compile")
    public ResponseEntity<APIResponse<String>> compileLatex(@Valid @RequestBody CompileLatexRequestDTO request) {
        String compiledContent = documentService.compileLatex(request.getLatexContent());
        APIResponse<String> response = APIResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("LaTeX compiled successfully")
                .data(compiledContent)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<Resource> generatePDF(@RequestBody java.util.Map<String, String> request) {
        String latexContent = request.get("latexContent");
        String filename = request.get("filename");

        if (latexContent == null || latexContent.trim().isEmpty()) {
            throw new RuntimeException("LaTeX content is required");
        }

        if (filename == null || filename.trim().isEmpty()) {
            filename = "document";
        }

        return latexCompilationService.generatePDF(latexContent, filename);
    }
}
