package com.zayver.simplenotes.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ModifyNoteCategoriesRequest {
    @NotNull
    private UUID noteUUID;
    @NotEmpty
    private List<UUID> categoryUUIDs;
}
