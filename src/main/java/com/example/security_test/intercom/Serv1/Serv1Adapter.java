package com.example.security_test.intercom.Serv1;

import com.example.security_test.model.MyClient;
import com.example.security_test.model.Note;
import com.example.security_test.model.NoteStatus;
import com.example.security_test.model.User;
import com.example.security_test.rabbitMqProducer.MessagePublisher;
import com.example.security_test.rabbitMqProducer.MyMessage;
import com.example.security_test.repository.NoteRepository;
import com.example.security_test.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class Serv1Adapter {
   private final UserRepository userRepository;
   private final NoteRepository noteRepository;
   private Serv1Client serv1Client;


   private MessagePublisher messagePublisher;

//   private SecurityContextHolder securityContextHolder;
//  private Authentication auth; = SecurityContextHolder.getContext().getAuthentication();
   public Serv1Adapter(Serv1Client serv1Client, MessagePublisher messagePublisher,
                       UserRepository userRepository, NoteRepository noteRepository){
      this.serv1Client=serv1Client;
      this.messagePublisher=messagePublisher;
      this.userRepository = userRepository;
      this.noteRepository = noteRepository;
   }

   public List<MyClient> getClients(){
      try {
         ResponseEntity<List<MyClient>> resp=serv1Client.getClients();
         return resp.getBody();
      }catch (RuntimeException e){
         throw new RuntimeException("Nu am reusit preluarea listei de clienti!!");

      }
   }


   public MyClient addClient(MyClient myClient){
      try{
         ResponseEntity<MyClient> resp=serv1Client.addClient(myClient);
         MyMessage myMessage=new MyMessage();
         myMessage.setMessage("ADD_CLIENT");
         myMessage.setContent(myClient);
         myMessage.setPriority(7);

         messagePublisher.sendMessageMyClient(myMessage);
         Note newNote=new Note();

         newNote.setLogTime(LocalDateTime.now());
         newNote.setEmail(myClient.getEmail());
         newNote.setOperation("ADD_CLIENT");
         Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
         String eml=authentication.getName();

         Optional<User> authUser=userRepository.findByEmail(eml);

         if(authUser.isPresent()){
            newNote.setLoggedUser(authUser.get());


         }
         newNote.setStatus(NoteStatus.PENDING);
         LocalDateTime time=LocalDateTime.now();
         newNote.setLogTime(time);

         noteRepository.save(newNote);

         long idNote=noteRepository.findByLogTimeaAndEmail(time,authUser.get().getEmail()).get().getId();
         MyMessage myMessage1=new MyMessage();
         myMessage1.setContent(idNote);
         myMessage1.setPriority(7);
         myMessage1.setMessage("id nota");
         messagePublisher.sendMessageStringNotes(myMessage1);

         return resp.getBody();
      }catch (RuntimeException e){
         throw new RuntimeException("Nu am putut adauga clientul!!");
      }
   }

   public MyClient updClient(MyClient myClient){
      try{
         ResponseEntity<MyClient> resp=serv1Client.updClient(myClient);
         MyMessage myMessage=new MyMessage();
         myMessage.setMessage("UPD_CLIENT");
         myMessage.setContent(myClient);
         myMessage.setPriority(7);
         messagePublisher.sendMessageMyClient(myMessage);
         Note newNote=new Note();

         newNote.setLogTime(LocalDateTime.now());
         newNote.setEmail(myClient.getEmail());
         newNote.setOperation("UPD_CLIENT");
//         User authUser=userRepository.findByEmail(auth.getName()).get();
//         newNote.setLoggedUser(authUser);
         newNote.setStatus(NoteStatus.PENDING);
         noteRepository.save(newNote);
         return resp.getBody();
      }catch (RuntimeException e){
         throw new RuntimeException("Nu am putut adauga clientul!!");
      }
   }


   public boolean delClient(String eml){
      try{

         ResponseEntity<Boolean> resp=serv1Client.delClient(eml);
         MyMessage myMessage=new MyMessage();
         myMessage.setMessage("DEL_CLIENT");
         myMessage.setContent(eml);
         myMessage.setPriority(7);
         messagePublisher.sendMessageMyClient(myMessage);
         Note newNote=new Note();

         newNote.setLogTime(LocalDateTime.now());

         newNote.setEmail(eml);
         newNote.setOperation("DEL_CLIENT");
//         User authUser=userRepository.findByEmail(auth.getName()).get();
//         newNote.setLoggedUser(authUser);
         newNote.setStatus(NoteStatus.PENDING);
         noteRepository.save(newNote);
         return resp.getBody();
      }catch (RuntimeException e){
         throw new RuntimeException("Nu am putut sterge clientul!!");
      }
   }


   public MyClient findClient(String eml){
      try{
         ResponseEntity<MyClient> resp=serv1Client.findClient(eml);
         return resp.getBody();
      }catch (RuntimeException e){
         throw new RuntimeException("Nu am gasit clientul!!");
      }
   }

}
