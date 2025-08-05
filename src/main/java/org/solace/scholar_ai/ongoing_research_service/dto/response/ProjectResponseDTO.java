package org.solace.scholar_ai.ongoing_research_service.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.ProjectStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseDTO {
    private UUID id;
    private UUID userId;
    private String title;
    private String description;
    private ProjectStatus status;
    private String researchDomain;
    private List<DocumentResponseDTO> documents;
    private Instant createdAt;
    private Instant updatedAt;
}
