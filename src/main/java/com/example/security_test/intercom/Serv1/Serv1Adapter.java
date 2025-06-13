package com.example.security_test.intercom.Serv1;

import com.example.security_test.model.MyClient;
import com.example.security_test.service.EventPublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Slf4j
public class Serv1Adapter {
   private final Serv1Client serv1Client;
   private final EventPublisherService eventPublisherService;

   public Serv1Adapter(Serv1Client serv1Client, EventPublisherService eventPublisherService){
      this.serv1Client = serv1Client;
      this.eventPublisherService = eventPublisherService;
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
         ResponseEntity<MyClient> resp = serv1Client.addClient(myClient);
         MyClient createdClient = resp.getBody();

         // Publish client creation event
         try {
            log.info("Publishing client creation event for client: {}", myClient.getEmail());
            eventPublisherService.publishCreateEvent(
               "Client", 
               myClient.getEmail(), 
               String.format("Client created: %s %s (%s)", myClient.getName(), myClient.getSurName(), myClient.getEmail())
            );
         } catch (Exception ex) {
            log.error("Failed to publish client creation event: {}", ex.getMessage(), ex);
            // Don't fail the client creation if event publishing fails
         }

         return createdClient;
      }catch (RuntimeException e){
         throw new RuntimeException("Nu am putut adauga clientul!!");
      }
   }

   public MyClient updClient(MyClient myClient){
      try{
         ResponseEntity<MyClient> resp = serv1Client.updClient(myClient.getId(), myClient);
         MyClient updatedClient = resp.getBody();

         // Publish client update event
         try {
            log.info("Publishing client update event for client: {}", myClient.getEmail());
            eventPublisherService.publishUpdateEvent(
               "Client", 
               myClient.getEmail(), 
               String.format("Client updated: %s %s (%s)", myClient.getName(), myClient.getSurName(), myClient.getEmail())
            );
         } catch (Exception ex) {
            log.error("Failed to publish client update event: {}", ex.getMessage(), ex);
            // Don't fail the client update if event publishing fails
         }

         return updatedClient;
      }catch (RuntimeException e){
         throw new RuntimeException("Nu am putut actualiza clientul!!");
      }
   }


   public boolean delClient(String eml){
      try{
         ResponseEntity<Boolean> resp = serv1Client.delClient(eml);
         Boolean result = !resp.getStatusCode().isError();

         // Only publish event if deletion was successful
         if (result != null && result) {
            // Publish client deletion event
            try {
               log.info("Publishing client deletion event for client: {}", eml);
               eventPublisherService.publishDeleteEvent(
                  "Client", 
                  eml, 
                  String.format("Client deleted: %s", eml)
               );
            } catch (Exception ex) {
               log.error("Failed to publish client deletion event: {}", ex.getMessage(), ex);
               // Don't fail the client deletion if event publishing fails
            }
         }

         return result;
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
