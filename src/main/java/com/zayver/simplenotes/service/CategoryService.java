package com.zayver.simplenotes.service;

import com.zayver.simplenotes.dto.entity.CategoryDTO;
import com.zayver.simplenotes.dto.request.CreateCategoryRequest;
import com.zayver.simplenotes.mapper.CategoryMapper;
import com.zayver.simplenotes.model.Category;
import com.zayver.simplenotes.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AuthService authService;

    public Set<CategoryDTO> getUserCategories(UUID uuid){
        return categoryRepository.getUserCategories(uuid)
                .stream().map(categoryMapper::fromModelToDTO)
                .collect(Collectors.toSet());
    }

    public CategoryDTO createCategory(UUID userUUID, CreateCategoryRequest request){
        val user = authService.getUserByUuid(userUUID);
        val category = Category.builder().name(request.getName()).user(user).build();
        return categoryMapper.fromModelToDTO(categoryRepository.save(category));
    }

    public void deleteCategory(UUID userUUID, UUID categoryUUID){
        val category = categoryRepository.findById(categoryUUID)
                .or(()-> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
                })
                .filter(n -> n.getUser().getUuid().equals(userUUID))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized for that category"));

        categoryRepository.delete(category);
    }
}
