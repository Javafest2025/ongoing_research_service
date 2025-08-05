package org.solace.scholar_ai.ongoing_research_service.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "citations")
public class Citation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "paper_id")
    private String paperId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "authors")
    private String authors;

    @Column(name = "\"year\"")
    private Integer year;

    @Column(name = "venue")
    private String venue;

    @Column(name = "doi")
    private String doi;

    @Column(name = "citation_key")
    private String citationKey;

    @Column(name = "position_in_text")
    private Integer positionInText;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;
}
