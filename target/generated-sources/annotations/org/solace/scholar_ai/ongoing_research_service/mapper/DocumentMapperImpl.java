package org.solace.scholar_ai.ongoing_research_service.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Document;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Project;
import org.solace.scholar_ai.ongoing_research_service.dto.request.CreateDocumentRequestDTO;
import org.solace.scholar_ai.ongoing_research_service.dto.response.DocumentResponseDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T22:13:00+0600",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class DocumentMapperImpl implements DocumentMapper {

    @Override
    public Document toEntity(CreateDocumentRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Document document = new Document();

        document.setProject( createDocumentRequestDTOToProject( dto ) );
        document.setTitle( dto.getTitle() );
        document.setContent( dto.getContent() );
        document.setDocumentType( dto.getDocumentType() );

        return document;
    }

    @Override
    public DocumentResponseDTO toResponseDTO(Document entity) {
        if ( entity == null ) {
            return null;
        }

        DocumentResponseDTO documentResponseDTO = new DocumentResponseDTO();

        documentResponseDTO.setProjectId( entityProjectId( entity ) );
        documentResponseDTO.setId( entity.getId() );
        documentResponseDTO.setTitle( entity.getTitle() );
        documentResponseDTO.setContent( entity.getContent() );
        documentResponseDTO.setDocumentType( entity.getDocumentType() );
        documentResponseDTO.setFilePath( entity.getFilePath() );
        documentResponseDTO.setCreatedAt( entity.getCreatedAt() );
        documentResponseDTO.setUpdatedAt( entity.getUpdatedAt() );

        return documentResponseDTO;
    }

    @Override
    public List<DocumentResponseDTO> toResponseDTOList(List<Document> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DocumentResponseDTO> list = new ArrayList<DocumentResponseDTO>( entities.size() );
        for ( Document document : entities ) {
            list.add( toResponseDTO( document ) );
        }

        return list;
    }

    protected Project createDocumentRequestDTOToProject(CreateDocumentRequestDTO createDocumentRequestDTO) {
        if ( createDocumentRequestDTO == null ) {
            return null;
        }

        Project project = new Project();

        project.setId( createDocumentRequestDTO.getProjectId() );

        return project;
    }

    private UUID entityProjectId(Document document) {
        Project project = document.getProject();
        if ( project == null ) {
            return null;
        }
        return project.getId();
    }
}
