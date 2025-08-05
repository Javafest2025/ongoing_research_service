package org.solace.scholar_ai.ongoing_research_service.repository;

import java.util.List;
import java.util.UUID;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Template;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.TemplateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, UUID> {

    List<Template> findAllByOrderByCreatedAtDesc();

    List<Template> findByTemplateTypeOrderByCreatedAtDesc(TemplateType templateType);

    List<Template> findByIsPublicTrueOrderByCreatedAtDesc();
}
