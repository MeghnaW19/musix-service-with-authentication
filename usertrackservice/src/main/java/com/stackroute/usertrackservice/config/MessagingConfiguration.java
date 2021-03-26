package com.stackroute.usertrackservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration

public class MessagingConfiguration {


  // code for direct exchange

  private String exchnageName = "user_exchange";
  private String registerQueue = "user_queue";
  private String trackregisterqueue = "track_queue";

  @Bean
  DirectExchange exchange(){
    return new DirectExchange(exchnageName);

  }

  @Bean
  Queue queue1(){
    return new Queue(registerQueue , false);
  }
  @Bean
  Queue queue2(){
    return new Queue(trackregisterqueue , false);
  }

  @Bean
  Binding binding(Queue queue1 , DirectExchange exchange){

    return BindingBuilder.bind(queue1()).to(exchange).with("user_routing");
  }

  @Bean
  Binding binding2(Queue queue2 , DirectExchange exchange){

    return BindingBuilder.bind(queue2()).to(exchange).with("track_routing");
  }

  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
    return rabbitTemplate;
  }

//  @Bean
//  public Producer producer(){
//    return new Producer();
//  }

}
