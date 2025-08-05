package org.solace.scholar_ai.ongoing_research_service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Project;
import org.solace.scholar_ai.ongoing_research_service.dto.request.CreateProjectRequestDTO;
import org.solace.scholar_ai.ongoing_research_service.dto.response.ProjectResponseDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T22:13:00+0600",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public Project toEntity(CreateProjectRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Project project = new Project();

        project.setUserId( dto.getUserId() );
        project.setTitle( dto.getTitle() );
        project.setDescription( dto.getDescription() );
        project.setStatus( dto.getStatus() );
        project.setResearchDomain( dto.getResearchDomain() );

        return project;
    }

    @Override
    public ProjectResponseDTO toResponseDTO(Project entity) {
        if ( entity == null ) {
            return null;
        }

        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();

        projectResponseDTO.setId( entity.getId() );
        projectResponseDTO.setUserId( entity.getUserId() );
        projectResponseDTO.setTitle( entity.getTitle() );
        projectResponseDTO.setDescription( entity.getDescription() );
        projectResponseDTO.setStatus( entity.getStatus() );
        projectResponseDTO.setResearchDomain( entity.getResearchDomain() );
        projectResponseDTO.setCreatedAt( entity.getCreatedAt() );
        projectResponseDTO.setUpdatedAt( entity.getUpdatedAt() );

        return projectResponseDTO;
    }

    @Override
    public List<ProjectResponseDTO> toResponseDTOList(List<Project> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProjectResponseDTO> list = new ArrayList<ProjectResponseDTO>( entities.size() );
        for ( Project project : entities ) {
            list.add( toResponseDTO( project ) );
        }

        return list;
    }
}
