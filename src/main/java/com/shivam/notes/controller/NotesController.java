package com.shivam.notes.controller;

import com.shivam.notes.models.Notes;
import com.shivam.notes.models.UserNotes;
import com.shivam.notes.repository.NotesRepo;
import com.shivam.notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class NotesController {

    @Autowired
    private NotesRepo notesRepo;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/notes/create")
    public ResponseEntity<?> createNotes(@RequestBody Notes notes){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<UserNotes> user = userRepository.findByUsername(username);

        if(user.isPresent()) {
            notes.setUser(user.get());
            notesRepo.save(notes);
            return ResponseEntity.ok("Note Created Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");
        }
    }

    @GetMapping("notes/list")
    public ResponseEntity<?> getNotes() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Notes> notes = notesRepo.findByUser_Username(username);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/notes/search/{keyword}")
    public ResponseEntity<?> searchNotes(@PathVariable String keyword) {
        // Get the authenticated user's username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch notes belonging to the user and matching the keyword
        List<Notes> notes = notesRepo.findByUser_UsernameAndTitleContainingOrUser_UsernameAndCategoryContaining(
                username, keyword, username, keyword);

        if (notes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No notes found matching the keyword");
        }

        return ResponseEntity.ok(notes);
    }

    @PutMapping("/notes/update/{id}")
    public String updateNotes(@RequestBody Notes notes,@PathVariable Long id){
        Optional<Notes> note = notesRepo.findById(id);
        notesRepo.save(note.get());
        return "notes updated";
    }

    @DeleteMapping("/notes/delete/{id}")
    public String deleteNotes(@PathVariable Long id){
        notesRepo.deleteById(id);
        return "note with id : " + id + " deleted";
    }

//    @GetMapping("/notes/list")
//    public List<Notes> displayNotes() {
//
//        return notesRepo.findAll();
//    }
    @GetMapping("/notes/list/{id}")
    public Notes getNotes(@PathVariable Long id){
        Optional<Notes> note = notesRepo.findById(id);

        return note.get();

    }


//    @GetMapping("/healthcheck")
//    public String healthCheck(){
//        return "project is running";
//    }

}
