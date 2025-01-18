package com.shivam.notes.repository;

import com.shivam.notes.models.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface NotesRepo extends JpaRepository<Notes, Long> {

    Optional<Notes> findById(Long id);
    List<Notes> findByUser_Username(String username);
    // List<Notes> findByTitleContainingOrCategoryContaining(String title,String category);

    List<Notes> findByUser_UsernameAndTitleContainingOrUser_UsernameAndCategoryContaining(String username, String keyword, String username1, String keyword1);
}
