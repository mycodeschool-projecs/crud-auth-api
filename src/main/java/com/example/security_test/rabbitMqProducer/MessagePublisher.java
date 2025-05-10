package com.example.security_test.rabbitMqProducer;
import com.example.security_test.model.MyClient;
import com.example.security_test.model.SignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange exchange;




    public MessagePublisher(RabbitTemplate rabbitTemplate, DirectExchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;

    }

    public boolean sendMessageString(MyMessage<String> message) {
        message.setPriority(7);
        try{
            rabbitTemplate.convertAndSend(exchange.getName(), RabbitMQConfig.ROUTING_KEY_ONE, message);
            System.out.println("Sent message: " + message.toString());
            return true;
        }catch (Exception e){
            return false;
        }



    }


    public boolean sendMessageStringNotes(MyMessage<String> message) {
        message.setPriority(7);
        try{
            rabbitTemplate.convertAndSend(exchange.getName(), RabbitMQConfig.ROUTING_KEY_THREE, message);
            System.out.println(message.toString());
            return true;
        }catch (Exception e){
            return false;
        }



    }

    public boolean sendMessageMyClient(MyMessage<SignUpRequest> message) {
        message.setPriority(7);
        try{
            rabbitTemplate.convertAndSend(exchange.getName(), RabbitMQConfig.ROUTING_KEY_TWO, message);
            System.out.println("Sent message: " + message.toString());
            return true;
        }catch (Exception e){
            return false;
        }

    }
//
//    public boolean sendMessageListMapStocOpt(MyMessage<List<MapStocOpt>> message) {
//        message.setPriority(7);
//        try{
//            rabbitTemplate.convertAndSend(exchange.getName(), ProducerConfig.ROUTING_KEY_THREE, message);
//            System.out.println("Sent message: " + message.toString());
//            return true;
//        }catch (Exception e){
//            return false;
//        }
//
//
//
//    }
}

