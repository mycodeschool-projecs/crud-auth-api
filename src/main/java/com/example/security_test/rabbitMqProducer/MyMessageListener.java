package com.example.security_test.rabbitMqProducer;
import com.example.security_test.model.Note;
import com.example.security_test.model.NoteStatus;
import com.example.security_test.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class MyMessageListener {

    private NoteRepository noteRepository;

    public MyMessageListener(NoteRepository noteRepository){
        this.noteRepository=noteRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_FOUR)
    public void receiveMessageStringNotes(MyMessage<String> message) {
        Long noteId=Long.parseLong(message.getContent());

        Optional<Note> optNota=noteRepository.findById(noteId);
        if(optNota.isPresent()){
            Note note=optNota.get();
            note.setStatus(NoteStatus.DONE);
            noteRepository.save(note);
        }

    }



}
