package com.stackroute.orchestrationService.service;


import com.stackroute.orchestrationService.exception.UserAlreadyExistsException;
import com.stackroute.rabbitmq.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrchestrationServiceImpl implements OrchestrationService {

  @Autowired
  private RabbitTemplate amqpTemplate;

  private String routingkey="userroutingKey";
  private String exchangeName="registerexchange";


  @Override
  public User registerUser(User user) throws UserAlreadyExistsException {
    amqpTemplate.convertAndSend(exchangeName, routingkey, user);
    System.out.println("Send msg = " + user);

    return user;
  }

//  @Autowired
//  RestTemplate restTemplate;
//
//
//  String urlUserTrackService = "http://usertrackservice/api/v1/usertrackservice/register";
//  String urlAuthenticationService = "http://authenticationservice/api/v1/userservice/save";
//
//  @Override
//  public User registerUser(User user) throws UserAlreadyExistsException {
//    User userResponse = null;
//
//    try{
//
//
//      userResponse = restTemplate.postForObject(urlUserTrackService,user,User.class);
//
//         restTemplate.postForObject(urlAuthenticationService,user,User.class);
//
//
//
//    }catch (Exception e){
//
//      throw new UserAlreadyExistsException();
//    }
//    return userResponse;
//  }
}
