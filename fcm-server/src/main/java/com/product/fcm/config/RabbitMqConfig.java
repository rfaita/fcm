package com.product.fcm.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@Profile({"default", "docker-compose"})
@EnableRabbit
public class RabbitMqConfig implements RabbitListenerConfigurer {

    @Value("${fcm.send.topic.exchange}")
    private String topicExchange;
    @Value("${fcm.send.topic.sendDLQQueue}")
    private String sendTopicDLQQueue;
    @Value("${fcm.send.topic.sendQueue}")
    private String sendTopicQueue;

    @Value("${fcm.send.token.exchange}")
    private String tokenExchange;
    @Value("${fcm.send.token.sendDLQQueue}")
    private String sendTokenDLQQueue;
    @Value("${fcm.send.token.sendQueue}")
    private String sendTokenQueue;

    @Value("${fcm.rabbitmq.hostname}")
    private String hostRmq;
    @Value("${fcm.rabbitmq.port}")
    private Integer portRmq;
    @Value("${fcm.rabbitmq.username}")
    private String userRmq;
    @Value("${fcm.rabbitmq.password}")
    private String passRmq;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostRmq, portRmq);
        connectionFactory.setUsername(userRmq);
        connectionFactory.setPassword(passRmq);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public DirectExchange topicExchange() {
        return new DirectExchange(topicExchange, true, false);
    }

    @Bean
    public DirectExchange tokenExchange() {
        return new DirectExchange(tokenExchange, true, false);
    }

    @Bean
    public Queue sendTopicQueue() {
        return QueueBuilder.durable(sendTopicQueue)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", sendTopicDLQQueue)
                .build();
    }

    @Bean
    public Queue sendTokenQueue() {
        return QueueBuilder.durable(sendTokenQueue)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", sendTokenDLQQueue)
                .build();
    }

    @Bean
    public Queue sendTopicDLQQueue() {
        return new Queue(sendTopicDLQQueue, true, false, false);
    }

    @Bean
    public Queue sendTokenDLQQueue() {
        return new Queue(sendTokenDLQQueue, true, false, false);
    }

    @Bean
    public Binding sendTopicBinding() {
        return BindingBuilder.bind(sendTopicQueue()).to(topicExchange()).with(sendTopicQueue);
    }

    @Bean
    public Binding sendTokenBinding() {
        return BindingBuilder.bind(sendTokenQueue()).to(tokenExchange()).with(sendTokenQueue);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory customRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

}
