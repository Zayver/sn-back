package com.zayver.simplenotes.repository;

import com.zayver.simplenotes.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query("SELECT c FROM Category c WHERE c.user.uuid = :uuid")
    Set<Category> getUserCategories(@Param("uuid") UUID uuid);
    Set<Category> findAllByUuidIn(List<UUID> uuids);
}
