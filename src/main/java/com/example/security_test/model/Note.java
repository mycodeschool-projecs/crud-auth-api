package com.example.security_test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name = "Note")
@Table(name = "notes")
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "note_generator")
    @SequenceGenerator(name = "note_generator",initialValue = 1,allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "myclient_id") // coloana FK în tabela
    private MyClient client;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User loggedUser;
    private String email;
    private String operation;
    private LocalDateTime logTime;
    private NoteStatus status;

}
