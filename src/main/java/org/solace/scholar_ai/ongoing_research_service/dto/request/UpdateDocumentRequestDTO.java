package org.solace.scholar_ai.ongoing_research_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDocumentRequestDTO {

    @NotNull(message = "Document ID is required") private UUID documentId;

    @NotBlank(message = "Content is required")
    private String content;
}
