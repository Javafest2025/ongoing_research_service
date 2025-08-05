package org.solace.scholar_ai.ongoing_research_service.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Project;
import org.solace.scholar_ai.ongoing_research_service.dto.request.CreateProjectRequestDTO;
import org.solace.scholar_ai.ongoing_research_service.dto.response.ProjectResponseDTO;

@Mapper(
        componentModel = "spring",
        uses = {DocumentMapper.class})
public interface ProjectMapper {

    Project toEntity(CreateProjectRequestDTO dto);

    @Mapping(target = "documents", ignore = true)
    ProjectResponseDTO toResponseDTO(Project entity);

    List<ProjectResponseDTO> toResponseDTOList(List<Project> entities);
}
