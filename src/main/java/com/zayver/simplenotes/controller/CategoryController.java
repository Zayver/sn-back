package com.zayver.simplenotes.controller;

import com.zayver.simplenotes.dto.entity.CategoryDTO;
import com.zayver.simplenotes.dto.entity.NoteDTO;
import com.zayver.simplenotes.dto.request.CreateCategoryRequest;
import com.zayver.simplenotes.model.User;
import com.zayver.simplenotes.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public Set<CategoryDTO> getUserCategories(@AuthenticationPrincipal User user){
        return categoryService.getUserCategories(user.getUuid());
    }

    @PostMapping
    public CategoryDTO createCategory(@AuthenticationPrincipal User user, @Valid @RequestBody CreateCategoryRequest request){
        return categoryService.createCategory(user.getUuid(), request);
    }

    @DeleteMapping("/{categoryUUID}")
    public void deleteCategory(@AuthenticationPrincipal User user, @PathVariable("categoryUUID")UUID categoryUUID){
        categoryService.deleteCategory(user.getUuid(), categoryUUID);
    }

}
