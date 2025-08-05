package org.solace.scholar_ai.ongoing_research_service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Template;
import org.solace.scholar_ai.ongoing_research_service.dto.request.CreateTemplateRequestDTO;
import org.solace.scholar_ai.ongoing_research_service.dto.response.TemplateResponseDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T22:13:00+0600",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class TemplateMapperImpl implements TemplateMapper {

    @Override
    public Template toEntity(CreateTemplateRequestDTO request) {
        if ( request == null ) {
            return null;
        }

        Template template = new Template();

        template.setName( request.getName() );
        template.setContent( request.getContent() );
        template.setTemplateType( request.getTemplateType() );
        template.setDescription( request.getDescription() );

        return template;
    }

    @Override
    public TemplateResponseDTO toResponseDTO(Template template) {
        if ( template == null ) {
            return null;
        }

        TemplateResponseDTO.TemplateResponseDTOBuilder templateResponseDTO = TemplateResponseDTO.builder();

        templateResponseDTO.id( template.getId() );
        templateResponseDTO.name( template.getName() );
        templateResponseDTO.description( template.getDescription() );
        templateResponseDTO.templateType( template.getTemplateType() );
        templateResponseDTO.content( template.getContent() );
        templateResponseDTO.createdAt( template.getCreatedAt() );
        templateResponseDTO.updatedAt( template.getUpdatedAt() );

        return templateResponseDTO.build();
    }

    @Override
    public List<TemplateResponseDTO> toResponseDTOList(List<Template> templates) {
        if ( templates == null ) {
            return null;
        }

        List<TemplateResponseDTO> list = new ArrayList<TemplateResponseDTO>( templates.size() );
        for ( Template template : templates ) {
            list.add( toResponseDTO( template ) );
        }

        return list;
    }
}
