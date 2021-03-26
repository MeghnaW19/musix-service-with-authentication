package com.stackroute.authenticationservice.config;

import com.stackroute.authenticationservice.domain.User;
import com.stackroute.authenticationservice.service.UserService;
import com.stackroute.rabbitmq.domain.UserDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class Consumer {

  @Autowired
  UserService userService;

  @RabbitListener(queues = "user_queue")
  public void getUserDtofromRabbitmq(UserDto userDto){

    User user = new User();
    user.setUsername(userDto.getUsername());
    user.setPassword(userDto.getPassword());

userService.saveUser(user);

  }

}
