package com.zayver.simplenotes.service;

import com.zayver.simplenotes.dto.entity.NoteDTO;
import com.zayver.simplenotes.dto.request.CreateNoteRequest;
import com.zayver.simplenotes.dto.request.ModifyNoteCategoriesRequest;
import com.zayver.simplenotes.mapper.NoteMapper;
import com.zayver.simplenotes.model.Note;
import com.zayver.simplenotes.repository.CategoryRepository;
import com.zayver.simplenotes.repository.NoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final AuthService authService;
    private final NoteMapper noteMapper;
    private final CategoryRepository categoryRepository;

    public List<NoteDTO> getUserNotes(UUID uuid, Boolean archived, List<UUID> categories) {
        Specification<Note> searchParams = (root, _, cb) -> {
            var predicate = cb.conjunction();
            predicate = cb.and(predicate, cb.equal(root.get("user").get("uuid"), uuid));
            if (archived != null) {
                predicate = cb.and(predicate, cb.equal(root.get("archived"), archived));
            }
            if (categories != null && !categories.isEmpty()) {
                predicate = cb.and(predicate, cb.isTrue(root.get("categories").get("uuid").in(categories)));
            }
            return predicate;
        };

        return noteRepository.findAll(searchParams, Sort.by(Sort.Order.by("archived")))
                .stream().map(noteMapper::fromDomainToDTO).collect(Collectors.toList());
    }

    private Note internalGetNoteByUUID(UUID userUUID, UUID noteUUID) {
        return noteRepository
                .findById(noteUUID)
                .or(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No note found by that id");
                })
                .filter(n -> n.getUser().getUuid().equals(userUUID))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized for that note"));
    }

    public NoteDTO getNoteByUUID(UUID userUUID, UUID noteUUID) {
        return noteMapper.fromDomainToDTO(internalGetNoteByUUID(userUUID, noteUUID));
    }

    public void setArchiveStatus(UUID userUUID, UUID noteUUID, Boolean setArchived) {
        val note = internalGetNoteByUUID(userUUID, noteUUID);
        note.setArchived(setArchived);
        noteRepository.save(note);
    }

    @Transactional
    public void createNote(UUID userUUID, CreateNoteRequest request) {
        val user = authService.getUserByUuid(userUUID);
        val categories = categoryRepository.findAllByUuidIn(request.getCategories());
        val note = Note.builder().title(request.getTitle())
                .note(request.getNote())
                .user(user)
                .categories(categories)
                .created_at(LocalDateTime.now())
                .archived(false)
                .build();
        noteRepository.save(note);
    }

    @Transactional
    public void deleteNote(UUID userUUID, UUID noteUuid) {
        val note = internalGetNoteByUUID(userUUID, noteUuid);
        noteRepository.delete(note);
    }

    @Transactional
    public void updateNote(UUID userUUID, UUID noteUUID, CreateNoteRequest request) {
        val note = internalGetNoteByUUID(userUUID, noteUUID);
        note.setTitle(request.getTitle());
        note.setNote(request.getNote());
        val categories = categoryRepository.findAllByUuidIn(request.getCategories());
        note.setCategories(categories);
        noteRepository.save(note);
    }
}
