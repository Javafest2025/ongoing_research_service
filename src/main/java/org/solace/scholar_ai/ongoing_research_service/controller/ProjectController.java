package org.solace.scholar_ai.ongoing_research_service.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.solace.scholar_ai.ongoing_research_service.dto.common.APIResponse;
import org.solace.scholar_ai.ongoing_research_service.dto.request.CreateProjectRequestDTO;
import org.solace.scholar_ai.ongoing_research_service.dto.response.ProjectResponseDTO;
import org.solace.scholar_ai.ongoing_research_service.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<APIResponse<ProjectResponseDTO>> createProject(
            @Valid @RequestBody CreateProjectRequestDTO request) {
        ProjectResponseDTO project = projectService.createProject(request);
        APIResponse<ProjectResponseDTO> response = APIResponse.<ProjectResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Project created successfully")
                .data(project)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse<List<ProjectResponseDTO>>> getProjectsByUserId(@PathVariable UUID userId) {
        List<ProjectResponseDTO> projects = projectService.getProjectsByUserId(userId);
        APIResponse<List<ProjectResponseDTO>> response = APIResponse.<List<ProjectResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Projects retrieved successfully")
                .data(projects)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<APIResponse<ProjectResponseDTO>> getProjectById(@PathVariable UUID projectId) {
        ProjectResponseDTO project = projectService.getProjectById(projectId);
        APIResponse<ProjectResponseDTO> response = APIResponse.<ProjectResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Project retrieved successfully")
                .data(project)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<APIResponse<Void>> deleteProject(@PathVariable UUID projectId) {
        projectService.deleteProject(projectId);
        APIResponse<Void> response = APIResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Project deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
