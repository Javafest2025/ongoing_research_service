package org.solace.scholar_ai.ongoing_research_service.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Template;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.TemplateType;
import org.solace.scholar_ai.ongoing_research_service.dto.request.CreateTemplateRequestDTO;
import org.solace.scholar_ai.ongoing_research_service.dto.response.TemplateResponseDTO;
import org.solace.scholar_ai.ongoing_research_service.mapper.TemplateMapper;
import org.solace.scholar_ai.ongoing_research_service.repository.TemplateRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final TemplateMapper templateMapper;

    public TemplateResponseDTO createTemplate(CreateTemplateRequestDTO request) {
        Template template = new Template();
        template.setName(request.getName());
        template.setDescription(request.getDescription());
        template.setContent(request.getContent());
        template.setTemplateType(request.getTemplateType());

        Template savedTemplate = templateRepository.save(template);
        return templateMapper.toResponseDTO(savedTemplate);
    }

    public List<TemplateResponseDTO> getAllTemplates() {
        List<Template> templates = templateRepository.findAll();
        return templates.stream().map(templateMapper::toResponseDTO).toList();
    }

    public List<TemplateResponseDTO> getTemplatesByType(TemplateType templateType) {
        List<Template> templates = templateRepository.findByTemplateTypeOrderByCreatedAtDesc(templateType);
        return templates.stream().map(templateMapper::toResponseDTO).toList();
    }

    public TemplateResponseDTO getTemplateById(UUID templateId) {
        Template template =
                templateRepository.findById(templateId).orElseThrow(() -> new RuntimeException("Template not found"));
        return templateMapper.toResponseDTO(template);
    }

    public void deleteTemplate(UUID templateId) {
        if (!templateRepository.existsById(templateId)) {
            throw new RuntimeException("Template not found");
        }
        templateRepository.deleteById(templateId);
    }

    public String generateWritingSuggestions(String topic, TemplateType templateType) {
        // Simple placeholder implementation
        return String.format(
                "Writing suggestions for %s using %s template:\n" + "1. Start with a clear introduction\n"
                        + "2. Organize your content logically\n"
                        + "3. Use appropriate citations\n"
                        + "4. Conclude with a summary",
                topic, templateType);
    }
}
