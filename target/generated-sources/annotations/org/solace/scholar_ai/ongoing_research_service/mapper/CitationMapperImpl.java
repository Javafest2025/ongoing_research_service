package org.solace.scholar_ai.ongoing_research_service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Citation;
import org.solace.scholar_ai.ongoing_research_service.dto.response.CitationResponseDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T22:14:29+0600",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.50.v20250729-0351, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CitationMapperImpl implements CitationMapper {

    @Override
    public CitationResponseDTO toResponseDTO(Citation entity) {
        if ( entity == null ) {
            return null;
        }

        CitationResponseDTO citationResponseDTO = new CitationResponseDTO();

        citationResponseDTO.setAuthors( entity.getAuthors() );
        citationResponseDTO.setCitationKey( entity.getCitationKey() );
        citationResponseDTO.setCreatedAt( entity.getCreatedAt() );
        citationResponseDTO.setDoi( entity.getDoi() );
        citationResponseDTO.setId( entity.getId() );
        citationResponseDTO.setPaperId( entity.getPaperId() );
        citationResponseDTO.setPositionInText( entity.getPositionInText() );
        citationResponseDTO.setTitle( entity.getTitle() );
        citationResponseDTO.setVenue( entity.getVenue() );
        citationResponseDTO.setYear( entity.getYear() );

        return citationResponseDTO;
    }

    @Override
    public List<CitationResponseDTO> toResponseDTOList(List<Citation> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CitationResponseDTO> list = new ArrayList<CitationResponseDTO>( entities.size() );
        for ( Citation citation : entities ) {
            list.add( toResponseDTO( citation ) );
        }

        return list;
    }
}
