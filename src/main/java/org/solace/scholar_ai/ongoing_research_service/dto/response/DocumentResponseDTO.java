package org.solace.scholar_ai.ongoing_research_service.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.DocumentType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponseDTO {
    private UUID id;
    private UUID projectId;
    private String title;
    private String content;
    private DocumentType documentType;
    private String filePath;
    private List<CitationResponseDTO> citations;
    private Instant createdAt;
    private Instant updatedAt;
}
