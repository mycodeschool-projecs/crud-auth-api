package com.example.security_test.controller;

import com.example.security_test.intercom.Serv1.Serv1Adapter;
import com.example.security_test.model.MyClient;
import com.example.security_test.model.SignUpRequest;
import com.example.security_test.model.SigninRequest;
import com.example.security_test.repository.UserRepository;
import com.example.security_test.service.AuthenticationService;
import com.example.security_test.service.JwtAuthenticationResponse;
import com.example.security_test.system.logs.StructuredLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@Slf4j
//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:8082"},
//        methods = {RequestMethod.GET, RequestMethod.POST,RequestMethod.DELETE},
//        allowedHeaders = "*",
//        allowCredentials = "true",
//        maxAge = 3600)
public class AuthenticationController {
    private AuthenticationService authenticationService;

    private Serv1Adapter serv1Adapter;

    private UserRepository userRepository;


    @Autowired
    private StructuredLogger logger;

    public AuthenticationController(AuthenticationService authenticationService,
                                    Serv1Adapter serv1Adapter,
                                    UserRepository userRepository){
        this.authenticationService=authenticationService;
        this.serv1Adapter=serv1Adapter;
        this.userRepository=userRepository;
//        this.logger=logger;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
        if(!userRepository.findByEmail(request.getEmail()).isPresent()){

            return ResponseEntity.ok(authenticationService.signup(request));
        }
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "User already exists!"));

    }

    @PostMapping("/auth/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
//        logger.logBuilder().withMessage("ACTION_SIGN_IN").withLevel("INFO").withField("SIGNIN_EMAIL:","jhjhjh").log();
        try{
            logger.logBuilder().withLevel("INFO").withMessage("USER_SIGNIN").withField("sgnUser",request);

            return ResponseEntity.ok(authenticationService.signin(request));

        }catch (Exception e){
            logger.logBuilder().withLevel("ERROR").withMessage("BAD CREDENTIAL");
            throw new RuntimeException("Bad credential!");
        }

    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addclient")
    public ResponseEntity<MyClient> addClient(@RequestBody MyClient client){
        try{
            System.out.println(client);
            return ResponseEntity.ok(serv1Adapter.addClient(client));

        }catch (RuntimeException e){
            throw e;
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updclient")
    public ResponseEntity<MyClient> updClient(@RequestBody MyClient client){
        try{

            return ResponseEntity.ok(serv1Adapter.updClient(client));

        }catch (RuntimeException e){
            throw e;
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delclient/{eml}")
    public ResponseEntity<Boolean> delClient(@PathVariable String eml){
        try{

            return ResponseEntity.ok(serv1Adapter.delClient(eml));

        }catch (RuntimeException e){
            throw e;
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/findclient/{eml}")
    public ResponseEntity<MyClient> findClient(@PathVariable String eml){
        try{

            return ResponseEntity.ok(serv1Adapter.findClient(eml));

        }catch (RuntimeException e){
            throw e;
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getclients")
    public ResponseEntity<List<MyClient>> getAllClients(){
        try{

            return ResponseEntity.ok(serv1Adapter.getClients());

        }catch (RuntimeException e){
            throw e;
        }
    }
}
