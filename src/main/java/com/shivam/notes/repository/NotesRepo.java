package com.shivam.notes.repository;

import com.shivam.notes.models.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface NotesRepo extends JpaRepository<Notes, Long> {

    public Optional<Notes> findById(Long id);

}
