package com.zayver.simplenotes.mapper;

import com.zayver.simplenotes.dto.entity.CategoryDTO;
import com.zayver.simplenotes.dto.entity.NoteDTO;
import com.zayver.simplenotes.model.Category;
import com.zayver.simplenotes.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring",
        uses = {CategoryMapper.class}
)
public interface NoteMapper {
    @Mapping(target = "categories", qualifiedByName = "mapCategory")
    NoteDTO fromDomainToDTO(Note note);

    @Named("mapCategory")
    @Mapping(target = "notes", ignore = true)
    CategoryDTO toCategoryDTO(Category category);
}
