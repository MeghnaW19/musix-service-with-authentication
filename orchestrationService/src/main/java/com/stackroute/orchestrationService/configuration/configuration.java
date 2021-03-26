package com.stackroute.orchestrationService.configuration;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configuration {

  private String queues = "registeruser";
  private String exchangeName="registerexchange";

  @Bean
  Queue queue(){
    return new Queue(queues , false);
  }

  @Bean
  TopicExchange exchange(){

    return new TopicExchange(exchangeName);
  }

  @Bean
  Binding binding(Queue queue , TopicExchange exchange){

    return BindingBuilder.bind(queue).to(exchange).with("userroutingKey");
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
