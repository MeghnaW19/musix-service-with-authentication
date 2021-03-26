package com.stackroute.orchestrationService.controller;


import com.stackroute.rabbitmq.User;
import com.stackroute.orchestrationService.exception.UserAlreadyExistsException;
import com.stackroute.orchestrationService.service.OrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class OrchestrationController {

  OrchestrationService orchestrationService;

  @Autowired
  OrchestrationController(OrchestrationService orchestrationService){
    this.orchestrationService = orchestrationService;
  }

  @PostMapping("/user")
  public ResponseEntity<?> registerAndSave(@RequestBody User user) throws UserAlreadyExistsException {


    ResponseEntity responseEntity = null;
    try{

      User userobj = this.orchestrationService.registerUser(user);
      responseEntity  = new ResponseEntity(userobj , HttpStatus.CREATED);
    }
    catch (UserAlreadyExistsException e){
      throw new UserAlreadyExistsException();
    }
    return responseEntity;


  }


}
