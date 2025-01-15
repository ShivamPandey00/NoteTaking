package com.shivam.notes.controller;

import com.shivam.notes.models.Notes;
import com.shivam.notes.repository.NotesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class NotesController {

    @Autowired
    NotesRepo notesRepo ;

    @PostMapping("/notes/create")
    public String createNotes(@RequestBody Notes notes){
        notesRepo.save(notes);
        return "notes created";
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

    @GetMapping("/notes/list")
    public List<Notes> displayNotes() {

        return notesRepo.findAll();
    }
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
