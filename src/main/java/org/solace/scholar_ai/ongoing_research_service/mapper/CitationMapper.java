package org.solace.scholar_ai.ongoing_research_service.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Citation;
import org.solace.scholar_ai.ongoing_research_service.dto.response.CitationResponseDTO;

@Mapper(componentModel = "spring")
public interface CitationMapper {

    CitationResponseDTO toResponseDTO(Citation entity);

    List<CitationResponseDTO> toResponseDTOList(List<Citation> entities);
}
