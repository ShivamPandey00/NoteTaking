package com.shivam.notes.repository;

import com.shivam.notes.models.UserNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserNotes, String> {

    public Optional<UserNotes> findByUsername(String username);
}
