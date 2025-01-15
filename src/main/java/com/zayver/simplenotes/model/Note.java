package com.zayver.simplenotes.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;


import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "notes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String title;
    private String note;
    @CreatedDate
    private LocalDateTime created_at;
    private Boolean archived;
    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "category_notes",
            joinColumns = @JoinColumn(name = "note_uuid"),
            inverseJoinColumns = @JoinColumn(name = "category_uuid"))
    Set<Category> categories;

}
