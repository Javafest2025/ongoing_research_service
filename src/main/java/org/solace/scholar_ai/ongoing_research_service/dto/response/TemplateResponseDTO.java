package org.solace.scholar_ai.ongoing_research_service.dto.response;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.TemplateType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateResponseDTO {

    private UUID id;
    private String name;
    private String description;
    private TemplateType templateType;
    private String content;
    private String tags;
    private Instant createdAt;
    private Instant updatedAt;
}
