package com.example.security_test.intercom.Serv1;

import com.example.security_test.model.MyClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${param.microserv1-name}",url = "http://"+"${param.microserv1-service}"+":8081/api/v1/client")
//@CrossOrigin(origins = {"http://localhost:3000"},
//        methods = {RequestMethod.GET, RequestMethod.POST,RequestMethod.DELETE},
//        allowedHeaders = "*",
//        allowCredentials = "true",
//        maxAge = 3600)
public interface Serv1Client {


    @PostMapping("/add")
    ResponseEntity<MyClient> addClient(@RequestBody MyClient myClient);

    @PostMapping("/upd")
    ResponseEntity<MyClient> updClient(@RequestBody MyClient client);

    @GetMapping ("/getall")
    ResponseEntity<List<MyClient>> getClients();


    @DeleteMapping("/del/{eml}")
    ResponseEntity<Boolean> delClient(@PathVariable String eml);


    @GetMapping("/find/{eml}")
    ResponseEntity<MyClient> findClient(@PathVariable String eml);

}
