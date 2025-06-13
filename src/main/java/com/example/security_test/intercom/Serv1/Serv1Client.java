package com.example.security_test.intercom.Serv1;

import com.example.security_test.configuration.FeignClientConfiguration;
import com.example.security_test.model.MyClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
    name = "${param.microserv1-name}",
    url = "http://"+"${param.microserv1-service}"+":8081/api/v1/clients",
    configuration = FeignClientConfiguration.class
)
public interface Serv1Client {


    @PostMapping
    ResponseEntity<MyClient> addClient(@RequestBody MyClient myClient);

    @PutMapping("/{id}")
    ResponseEntity<MyClient> updClient(@PathVariable("id") Long id, @RequestBody MyClient client);

    @GetMapping
    ResponseEntity<List<MyClient>> getClients();


    @DeleteMapping("/delete/email/{eml}")
    ResponseEntity<Boolean> delClient(@PathVariable String eml);


    @GetMapping("/find/email/{eml}")
    ResponseEntity<MyClient> findClient(@PathVariable String eml);

}
