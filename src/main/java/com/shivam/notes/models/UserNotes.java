package com.shivam.notes.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class UserNotes {

    @Id
    private String username;
    private String password;
    private String name;
    private String email;

}
