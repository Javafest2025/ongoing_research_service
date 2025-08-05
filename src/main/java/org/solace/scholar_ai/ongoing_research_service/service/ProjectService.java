package org.solace.scholar_ai.ongoing_research_service.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Project;
import org.solace.scholar_ai.ongoing_research_service.dto.request.CreateProjectRequestDTO;
import org.solace.scholar_ai.ongoing_research_service.dto.response.ProjectResponseDTO;
import org.solace.scholar_ai.ongoing_research_service.mapper.ProjectMapper;
import org.solace.scholar_ai.ongoing_research_service.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectResponseDTO createProject(CreateProjectRequestDTO request) {
        Project project = projectMapper.toEntity(request);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toResponseDTO(savedProject);
    }

    public List<ProjectResponseDTO> getProjectsByUserId(UUID userId) {
        List<Project> projects = projectRepository.findByUserIdOrderByUpdatedAtDesc(userId);
        return projectMapper.toResponseDTOList(projects);
    }

    public ProjectResponseDTO getProjectById(UUID projectId) {
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        return projectMapper.toResponseDTO(project);
    }

    @Transactional
    public void deleteProject(UUID projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("Project not found with id: " + projectId);
        }
        projectRepository.deleteById(projectId);
    }
}
