package org.solace.scholar_ai.ongoing_research_service.dto.response;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitationResponseDTO {
    private UUID id;
    private String paperId;
    private String title;
    private String authors;
    private Integer year;
    private String venue;
    private String doi;
    private String citationKey;
    private Integer positionInText;
    private Instant createdAt;
}
