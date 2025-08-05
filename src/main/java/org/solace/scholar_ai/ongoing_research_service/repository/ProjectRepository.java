package org.solace.scholar_ai.ongoing_research_service.repository;

import java.util.List;
import java.util.UUID;
import org.solace.scholar_ai.ongoing_research_service.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByUserId(UUID userId);

    List<Project> findByUserIdOrderByUpdatedAtDesc(UUID userId);
}
