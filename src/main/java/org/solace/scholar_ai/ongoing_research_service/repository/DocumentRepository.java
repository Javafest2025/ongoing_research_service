package org.solace.scholar_ai.ongoing_research_service.repository;

import java.util.List;
import java.util.UUID;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findByProjectId(UUID projectId);

    List<Document> findByProjectIdOrderByUpdatedAtDesc(UUID projectId);
}
