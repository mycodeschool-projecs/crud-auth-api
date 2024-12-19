package com.example.security_test.intercom.Serv1;

import com.example.security_test.model.MyClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class Serv1Adapter {
   private Serv1Client serv1Client;

   public Serv1Adapter(Serv1Client serv1Client){
      this.serv1Client=serv1Client;

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
         return resp.getBody();
      }catch (RuntimeException e){
         throw new RuntimeException("Nu am putut adauga clientul!!");
      }
   }

   public MyClient updClient(MyClient myClient){
      try{
         ResponseEntity<MyClient> resp=serv1Client.updClient(myClient);
         return resp.getBody();
      }catch (RuntimeException e){
         throw new RuntimeException("Nu am putut adauga clientul!!");
      }
   }


   public boolean delClient(String eml){
      try{
         ResponseEntity<Boolean> resp=serv1Client.delClient(eml);
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
