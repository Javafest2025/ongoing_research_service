package org.solace.scholar_ai.ongoing_research_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.ProjectStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectRequestDTO {

    @NotNull(message = "User ID is required") private UUID userId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String researchDomain;

    private ProjectStatus status = ProjectStatus.DRAFT;
}
