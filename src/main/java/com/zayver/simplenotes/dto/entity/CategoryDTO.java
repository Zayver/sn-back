package com.zayver.simplenotes.dto.entity;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class CategoryDTO {
    private UUID uuid;
    private String name;
    private Set<NoteDTO> notes;
}
