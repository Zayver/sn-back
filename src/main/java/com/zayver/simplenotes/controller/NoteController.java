package com.zayver.simplenotes.controller;

import com.zayver.simplenotes.dto.entity.NoteDTO;
import com.zayver.simplenotes.dto.request.CreateNoteRequest;
import com.zayver.simplenotes.dto.request.ModifyNoteCategoriesRequest;
import com.zayver.simplenotes.model.User;
import com.zayver.simplenotes.service.NoteService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notes")
public class NoteController {
    private final NoteService noteService ;

    @GetMapping
    public List<NoteDTO> getUserNotes(@AuthenticationPrincipal User user,
                                      @RequestParam(value = "sortArchived", required = false) Boolean archived,
                                      @RequestParam(value = "categories", required = false)List<UUID> categories
    ){
        return noteService.getUserNotes(user.getUuid(), archived, categories);
    }
    @GetMapping("/{noteUUID}")
    public NoteDTO getNoteByUUID(@AuthenticationPrincipal User user, @PathVariable("noteUUID") UUID noteUUID){
        return noteService.getNoteByUUID(user.getUuid(), noteUUID);
    }

    @PutMapping("/{noteUUID}/setarchived")
    public void setArchivedStatus(@AuthenticationPrincipal User user,
                                  @PathVariable("noteUUID") UUID noteUUID,
                                  @NonNull @RequestParam("v")Boolean setArchived){
        noteService.setArchiveStatus(user.getUuid(), noteUUID, setArchived);
    }

    @PostMapping
    public void createNote(@AuthenticationPrincipal User user, @Valid @RequestBody CreateNoteRequest request){
        noteService.createNote(user.getUuid(), request);
    }

    @PutMapping("/{noteUUID}")
    public void editNote(@AuthenticationPrincipal User user, @PathVariable("noteUUID")UUID noteUUID, @Valid @RequestBody CreateNoteRequest request){
        noteService.updateNote(user.getUuid(), noteUUID, request);
    }

    @DeleteMapping("/{noteUUID}")
    public void deleteNote(@AuthenticationPrincipal User user, @PathVariable("noteUUID") UUID noteUUID){
        noteService.deleteNote(user.getUuid(), noteUUID);
    }
}
