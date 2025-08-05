package org.solace.scholar_ai.ongoing_research_service.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.TemplateType;
import org.solace.scholar_ai.ongoing_research_service.dto.common.APIResponse;
import org.solace.scholar_ai.ongoing_research_service.dto.request.CreateTemplateRequestDTO;
import org.solace.scholar_ai.ongoing_research_service.dto.response.TemplateResponseDTO;
import org.solace.scholar_ai.ongoing_research_service.service.TemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping
    public ResponseEntity<APIResponse<TemplateResponseDTO>> createTemplate(
            @Valid @RequestBody CreateTemplateRequestDTO request) {
        TemplateResponseDTO template = templateService.createTemplate(request);
        APIResponse<TemplateResponseDTO> response = APIResponse.<TemplateResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Template created successfully")
                .data(template)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<TemplateResponseDTO>>> getAllTemplates() {
        List<TemplateResponseDTO> templates = templateService.getAllTemplates();
        APIResponse<List<TemplateResponseDTO>> response = APIResponse.<List<TemplateResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Templates retrieved successfully")
                .data(templates)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/type/{templateType}")
    public ResponseEntity<APIResponse<List<TemplateResponseDTO>>> getTemplatesByType(
            @PathVariable TemplateType templateType) {
        List<TemplateResponseDTO> templates = templateService.getTemplatesByType(templateType);
        APIResponse<List<TemplateResponseDTO>> response = APIResponse.<List<TemplateResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Templates retrieved successfully")
                .data(templates)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<APIResponse<TemplateResponseDTO>> getTemplateById(@PathVariable UUID templateId) {
        TemplateResponseDTO template = templateService.getTemplateById(templateId);
        APIResponse<TemplateResponseDTO> response = APIResponse.<TemplateResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Template retrieved successfully")
                .data(template)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<APIResponse<Void>> deleteTemplate(@PathVariable UUID templateId) {
        templateService.deleteTemplate(templateId);
        APIResponse<Void> response = APIResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Template deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/suggestions")
    public ResponseEntity<APIResponse<String>> generateWritingSuggestions(
            @RequestParam String topic, @RequestParam TemplateType templateType) {
        String suggestions = templateService.generateWritingSuggestions(topic, templateType);
        APIResponse<String> response = APIResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Writing suggestions generated successfully")
                .data(suggestions)
                .build();
        return ResponseEntity.ok(response);
    }
}
