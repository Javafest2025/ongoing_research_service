package org.solace.scholar_ai.ongoing_research_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompileLatexRequestDTO {

    @NotBlank(message = "LaTeX content is required")
    private String latexContent;
}
