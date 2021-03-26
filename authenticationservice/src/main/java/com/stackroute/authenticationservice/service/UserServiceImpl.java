package com.stackroute.authenticationservice.service;

import com.stackroute.authenticationservice.config.Consumer;
import com.stackroute.authenticationservice.domain.User;
import com.stackroute.authenticationservice.exception.UserNotFoundException;
import com.stackroute.authenticationservice.repository.UserRepository;
import com.stackroute.rabbitmq.domain.UserDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


  private UserRepository userRepository;

//
//  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository){
    this.userRepository = userRepository;


  }


  @Override
//  @RabbitListener(queues = "user_queue")
  public User saveUser(User user) {

    userRepository.save(user);
     return user;

  }

  @Override
  public User findByUsernameAndPassword(String username , String password) throws UserNotFoundException {
         User user =  userRepository.findByUsernameAndPassword(username , password);

         if(user == null){
           throw new UserNotFoundException();
         }
         return user;

  }
}
