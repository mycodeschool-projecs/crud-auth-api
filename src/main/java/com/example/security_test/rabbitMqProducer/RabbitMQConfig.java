package com.example.security_test.rabbitMqProducer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME       = "my.direct.exchange";
    public static final String DLX_NAME            = "my.dlx";

    public static final String ROUTING_KEY_ONE     = "routing.key.one";
    public static final String ROUTING_KEY_TWO     = "routing.key.two";
    public static final String ROUTING_KEY_THREE   = "routing.key.three";
    public static final String ROUTING_KEY_FOUR    = "routing.key.four";

    public static final String QUEUE_ONE           = "queue.one";
    public static final String QUEUE_TWO           = "queue.two";
    public static final String QUEUE_THREE         = "queue.three";
    public static final String QUEUE_FOUR          = "queue.four";
    public static final String DLQ_FOUR            = "queue.four.dlq";

    @Bean
    @Primary
    DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX_NAME);
    }

    @Bean
    Queue queueOne() {
        return QueueBuilder.durable(QUEUE_ONE)
                .maxPriority(10)
                .build();
    }

    @Bean
    Queue queueTwo() {
        return QueueBuilder.durable(QUEUE_TWO)
                .maxPriority(10)
                .build();
    }

    @Bean
    Queue queueThree() {
        return QueueBuilder.durable(QUEUE_THREE)
                .maxPriority(10)
                .build();
    }

    @Bean
    Queue queueFour() {
        return QueueBuilder.durable(QUEUE_FOUR)
                .maxPriority(10)
                .deadLetterExchange(DLX_NAME)
                .deadLetterRoutingKey(ROUTING_KEY_FOUR)
                .build();
    }

    @Bean
    Queue deadLetterQueueFour() {
        return QueueBuilder.durable(DLQ_FOUR).build();
    }

    @Bean
    Binding bindingOne(Queue queueOne, DirectExchange directExchange) {
        return BindingBuilder.bind(queueOne).to(directExchange).with(ROUTING_KEY_ONE);
    }

    @Bean
    Binding bindingTwo(Queue queueTwo, DirectExchange directExchange) {
        return BindingBuilder.bind(queueTwo).to(directExchange).with(ROUTING_KEY_TWO);
    }

    @Bean
    Binding bindingThree(Queue queueThree, DirectExchange directExchange) {
        return BindingBuilder.bind(queueThree).to(directExchange).with(ROUTING_KEY_THREE);
    }

    @Bean
    Binding bindingFour(Queue queueFour, DirectExchange directExchange) {
        return BindingBuilder.bind(queueFour).to(directExchange).with(ROUTING_KEY_FOUR);
    }

    @Bean
    Binding dlqBindingFour(Queue deadLetterQueueFour, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueueFour)
                .to(deadLetterExchange)
                .with(ROUTING_KEY_FOUR);
    }

    @Bean
    Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jsonMessageConverter) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                  Jackson2JsonMessageConverter jsonMessageConverter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}
