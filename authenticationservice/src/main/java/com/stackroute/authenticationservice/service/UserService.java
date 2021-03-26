package com.stackroute.authenticationservice.service;

import com.stackroute.authenticationservice.domain.User;
import com.stackroute.authenticationservice.exception.UserNotFoundException;
import com.stackroute.rabbitmq.domain.UserDto;

public interface UserService {

  public User saveUser(User user);
  public User findByUsernameAndPassword(String username , String password) throws UserNotFoundException;
}
