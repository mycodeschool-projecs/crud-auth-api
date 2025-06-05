package com.example.security_test.service;

import com.example.security_test.model.CrudEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service for publishing CRUD events to RabbitMQ.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisherService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name:crud-events-exchange}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key.prefix:crud.events}")
    private String routingKeyPrefix;

    /**
     * Publishes a CRUD event to RabbitMQ.
     * 
     * @param operation The CRUD operation (CREATE, UPDATE, DELETE)
     * @param entityType The type of entity (e.g., "User", "Client")
     * @param entityId The ID of the entity
     * @param details Additional details about the operation
     */
    public void publishEvent(String operation, String entityType, String entityId, String details) {
        try {
            CrudEventMessage message = CrudEventMessage.builder()
                    .operation(operation)
                    .entityType(entityType)
                    .entityId(entityId)
                    .details(details)
                    .sourceService("auth-service")
                    .timestamp(new Date())
                    .status("PUBLISHED")
                    .build();

            String routingKey = String.format("%s.%s.%s", routingKeyPrefix, entityType.toLowerCase(), operation.toLowerCase());

            log.info("Publishing {} event for {} with ID {}, status: {}", operation, entityType, entityId, message.getStatus());
            rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
            log.info("Successfully published event to {}, message status: {}", routingKey, message.getStatus());
        } catch (Exception e) {
            log.error("Failed to publish event: {}", e.getMessage(), e);
        }
    }

    /**
     * Publishes a create event.
     */
    public void publishCreateEvent(String entityType, String entityId, String details) {
        publishEvent("CREATE", entityType, entityId, details);
    }

    /**
     * Publishes an update event.
     */
    public void publishUpdateEvent(String entityType, String entityId, String details) {
        publishEvent("UPDATE", entityType, entityId, details);
    }

    /**
     * Publishes a delete event.
     */
    public void publishDeleteEvent(String entityType, String entityId, String details) {
        publishEvent("DELETE", entityType, entityId, details);
    }

    /**
     * Publishes a login event.
     */
    public void publishLoginEvent(String entityType, String entityId, String details) {
        publishEvent("LOGIN", entityType, entityId, details);
    }
}
