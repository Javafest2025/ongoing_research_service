package org.solace.scholar_ai.ongoing_research_service.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Template;
import org.solace.scholar_ai.ongoing_research_service.dto.request.CreateTemplateRequestDTO;
import org.solace.scholar_ai.ongoing_research_service.dto.response.TemplateResponseDTO;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Template toEntity(CreateTemplateRequestDTO request);

    TemplateResponseDTO toResponseDTO(Template template);

    List<TemplateResponseDTO> toResponseDTOList(List<Template> templates);
}
