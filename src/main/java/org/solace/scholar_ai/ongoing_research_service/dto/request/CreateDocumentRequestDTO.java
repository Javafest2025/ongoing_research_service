package org.solace.scholar_ai.ongoing_research_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.DocumentType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDocumentRequestDTO {

    @NotNull(message = "Project ID is required") private UUID projectId;

    @NotBlank(message = "Title is required")
    private String title;

    private String content;

    private DocumentType documentType = DocumentType.LATEX;
}
