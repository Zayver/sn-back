package com.zayver.simplenotes.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class NoteDTO {
    private UUID uuid;
    private String title;
    private String note;
    private LocalDateTime created_at;
    private Boolean archived;
    private Set<CategoryDTO> categories;
}
