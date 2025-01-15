package com.zayver.simplenotes.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateNoteRequest {
    @NotBlank
    private String title;
    private String note;
    private List<UUID> categories;
}
