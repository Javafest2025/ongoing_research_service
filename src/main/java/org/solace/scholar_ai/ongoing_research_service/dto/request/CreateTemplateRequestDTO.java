package org.solace.scholar_ai.ongoing_research_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.TemplateType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTemplateRequestDTO {

    @NotBlank(message = "Template name is required")
    private String name;

    @NotBlank(message = "Template description is required")
    private String description;

    @NotNull(message = "Template type is required") private TemplateType templateType;

    @NotBlank(message = "Template content is required")
    private String content;

    private String tags;
}
