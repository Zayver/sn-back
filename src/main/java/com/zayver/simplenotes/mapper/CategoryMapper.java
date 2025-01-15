package com.zayver.simplenotes.mapper;

import com.zayver.simplenotes.dto.entity.CategoryDTO;
import com.zayver.simplenotes.dto.entity.NoteDTO;
import com.zayver.simplenotes.model.Category;
import com.zayver.simplenotes.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring"
)
public interface CategoryMapper {
    @Mapping(target = "notes", qualifiedByName = "mapNote")
    CategoryDTO fromModelToDTO(Category category);

    @Named("mapNote")
    @Mapping(target = "categories", ignore = true)
    NoteDTO toNoteDTO(Note note);
}
