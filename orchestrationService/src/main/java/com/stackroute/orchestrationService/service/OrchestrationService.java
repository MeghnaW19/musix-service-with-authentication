package com.stackroute.orchestrationService.service;


import com.stackroute.orchestrationService.exception.UserAlreadyExistsException;
import com.stackroute.rabbitmq.User;

public interface OrchestrationService {

  User registerUser(User user) throws UserAlreadyExistsException;
}
