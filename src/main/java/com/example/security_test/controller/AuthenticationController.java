package com.example.security_test.controller;

import com.example.security_test.intercom.Serv1.Serv1Adapter;
import com.example.security_test.model.MyClient;
import com.example.security_test.model.SignUpRequest;
import com.example.security_test.model.SigninRequest;
import com.example.security_test.model.User;
import com.example.security_test.rabbitMqProducer.MessagePublisher;
import com.example.security_test.rabbitMqProducer.MyMessage;
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


    private StructuredLogger logger;

    private MessagePublisher messagePublisher;


    public AuthenticationController(AuthenticationService authenticationService,
                                    Serv1Adapter serv1Adapter,
                                    UserRepository userRepository,StructuredLogger logger,MessagePublisher messagePublisher){
        this.authenticationService=authenticationService;
        this.serv1Adapter=serv1Adapter;
        this.userRepository=userRepository;
        this.logger=logger;
        this.messagePublisher=messagePublisher;
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
        if(!userRepository.findByEmail(request.getEmail()).isPresent()){
            logger.logBuilder().withLevel("INFO")
                    .withMessage("USER_SIGNUP")
                    .withField("sgnUpUser",request).log();
            return ResponseEntity.ok(authenticationService.signup(request));
        }
        logger.logBuilder().withLevel("ERROR")
                .withMessage("SIGNUP_ERROR")
                .withField("sgnUpUser",request).log();

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("error", "User already exists!"));

    }

    @PostMapping("/auth/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
//        logger.logBuilder().withMessage("ACTION_SIGN_IN").withLevel("INFO").withField("SIGNIN_EMAIL:","jhjhjh").log();
        try{
            logger.logBuilder().withLevel("INFO")
                    .withMessage("USER_SIGNIN")
                    .withField("sgnUser",request).log();

            return ResponseEntity.ok(authenticationService.signin(request));

        }catch (Exception e){
            logger.logBuilder().withLevel("ERROR").withMessage("BAD_CREDENTIAL").log();
            throw new RuntimeException("Bad credential!");
        }

    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/addclient")
    public ResponseEntity<MyClient> addClient(@RequestBody MyClient client){
        try{
            logger.logBuilder().withLevel("INFO")
                    .withMessage("ADDED_CLIENT_OK")
                    .withField("addedClient",client).log();


            return ResponseEntity.ok(serv1Adapter.addClient(client));

        }catch (RuntimeException e){
            logger.logBuilder().withLevel("ERROR")
                    .withMessage(e.getMessage()).log();
            throw e;
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updclient")
    public ResponseEntity<MyClient> updClient(@RequestBody MyClient client){
        try{
            logger.logBuilder().withLevel("INFO")
                    .withMessage("UPD_CLIENT")
                    .withField("updClient",client).log();
            return ResponseEntity.ok(serv1Adapter.updClient(client));

        }catch (RuntimeException e){
            logger.logBuilder().withLevel("ERROR")
                    .withMessage(e.getMessage()).log();
            throw e;
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delclient/{eml}")
    public ResponseEntity<Boolean> delClient(@PathVariable String eml){
        try{
            logger.logBuilder().withLevel("INFO")
                    .withMessage("DEL_CLIENT")
                    .withField("delClientEml",eml).log();
            return ResponseEntity.ok(serv1Adapter.delClient(eml));

        }catch (RuntimeException e){
            logger.logBuilder().withLevel("ERROR")
                    .withMessage(e.getMessage()).log();

            throw e;
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/findclient/{eml}")
    public ResponseEntity<MyClient> findClient(@PathVariable String eml){
        try{
            logger.logBuilder().withLevel("INFO")
                    .withMessage("FIND_CLIENT")
                    .withField("findClientEml",eml).log();
            return ResponseEntity.ok(serv1Adapter.findClient(eml));

        }catch (RuntimeException e){
            logger.logBuilder().withLevel("ERROR")
                    .withMessage(e.getMessage()).log();
            throw e;
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getclients")
    public ResponseEntity<List<MyClient>> getAllClients(){
        try{
            List<MyClient> list=serv1Adapter.getClients();
            logger.logBuilder().withLevel("INFO")
                    .withMessage("GET_CLIENTS")
                    .withField("getAllClients",list.size());

            return ResponseEntity.ok(list);

        }catch (RuntimeException e){
            logger.logBuilder().withLevel("ERROR")
                    .withMessage(e.getMessage()).log();
            throw e;
        }
    }
}
