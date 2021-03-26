package com.stackroute.usertrackservice.config;

import com.stackroute.rabbitmq.domain.UserDto;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Producer {


  private RabbitTemplate amqpTemplate;


  private DirectExchange directExchange;
 public Producer(){

 }


  @Autowired
  public Producer(RabbitTemplate amqpTemplate , DirectExchange directExchange)
  {
    this.amqpTemplate = amqpTemplate;
    this.directExchange = directExchange;
  }

  public void sendToRabbitMq(UserDto userDto){
    amqpTemplate.convertAndSend(directExchange.getName(), "user_routing", userDto);
  }

  public void sendToRabbitMqTrackObject(UserDto userDto){
    amqpTemplate.convertAndSend(directExchange.getName(), "track_routing", userDto);

  }
}
