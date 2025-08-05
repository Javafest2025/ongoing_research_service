package org.solace.scholar_ai.ongoing_research_service.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Document;
import org.solace.scholar_ai.ongoing_research_service.dto.request.CreateDocumentRequestDTO;
import org.solace.scholar_ai.ongoing_research_service.dto.response.DocumentResponseDTO;

@Mapper(
        componentModel = "spring",
        uses = {CitationMapper.class})
public interface DocumentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project.id", source = "projectId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "templates", ignore = true)
    @Mapping(target = "citations", ignore = true)
    Document toEntity(CreateDocumentRequestDTO dto);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "citations", ignore = true)
    DocumentResponseDTO toResponseDTO(Document entity);

    List<DocumentResponseDTO> toResponseDTOList(List<Document> entities);
}
