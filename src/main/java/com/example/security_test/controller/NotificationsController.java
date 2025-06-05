package com.example.security_test.controller;

import com.example.security_test.intercom.Notifications.NotificationsAdapter;
import com.example.security_test.model.Notification;
import com.example.security_test.system.logs.StructuredLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@CrossOrigin
@Slf4j
public class NotificationsController {
    private final NotificationsAdapter notificationsAdapter;
    private final StructuredLogger logger;

    public NotificationsController(NotificationsAdapter notificationsAdapter, StructuredLogger logger) {
        this.notificationsAdapter = notificationsAdapter;
        this.logger = logger;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        try {
            logger.logBuilder().withLevel("INFO")
                    .withMessage("GET_ALL_NOTIFICATIONS")
                    .log();
            return ResponseEntity.ok(notificationsAdapter.getAllNotifications());
        } catch (RuntimeException e) {
            logger.logBuilder().withLevel("ERROR")
                    .withMessage(e.getMessage()).log();
            throw e;
        }
    }

    @GetMapping("/status/{read}")
    public ResponseEntity<List<Notification>> getNotificationsByReadStatus(@PathVariable boolean read) {
        try {
            logger.logBuilder().withLevel("INFO")
                    .withMessage("GET_NOTIFICATIONS_BY_READ_STATUS")
                    .withField("readStatus", read)
                    .log();
            return ResponseEntity.ok(notificationsAdapter.getNotificationsByReadStatus(read));
        } catch (RuntimeException e) {
            logger.logBuilder().withLevel("ERROR")
                    .withMessage(e.getMessage()).log();
            throw e;
        }
    }

    @GetMapping("/service/{sourceService}")
    public ResponseEntity<List<Notification>> getNotificationsBySourceService(@PathVariable String sourceService) {
        try {
            logger.logBuilder().withLevel("INFO")
                    .withMessage("GET_NOTIFICATIONS_BY_SOURCE_SERVICE")
                    .withField("sourceService", sourceService)
                    .log();
            return ResponseEntity.ok(notificationsAdapter.getNotificationsBySourceService(sourceService));
        } catch (RuntimeException e) {
            logger.logBuilder().withLevel("ERROR")
                    .withMessage(e.getMessage()).log();
            throw e;
        }
    }

    @GetMapping("/entity-type/{entityType}")
    public ResponseEntity<List<Notification>> getNotificationsByEntityType(@PathVariable String entityType) {
        try {
            logger.logBuilder().withLevel("INFO")
                    .withMessage("GET_NOTIFICATIONS_BY_ENTITY_TYPE")
                    .withField("entityType", entityType)
                    .log();
            return ResponseEntity.ok(notificationsAdapter.getNotificationsByEntityType(entityType));
        } catch (RuntimeException e) {
            logger.logBuilder().withLevel("ERROR")
                    .withMessage(e.getMessage()).log();
            throw e;
        }
    }

    @GetMapping("/operation/{operation}")
    public ResponseEntity<List<Notification>> getNotificationsByOperation(@PathVariable String operation) {
        try {
            logger.logBuilder().withLevel("INFO")
                    .withMessage("GET_NOTIFICATIONS_BY_OPERATION")
                    .withField("operation", operation)
                    .log();
            return ResponseEntity.ok(notificationsAdapter.getNotificationsByOperation(operation));
        } catch (RuntimeException e) {
            logger.logBuilder().withLevel("ERROR")
                    .withMessage(e.getMessage()).log();
            throw e;
        }
    }

    @GetMapping("/entity-id/{entityId}")
    public ResponseEntity<List<Notification>> getNotificationsByEntityId(@PathVariable String entityId) {
        try {
            logger.logBuilder().withLevel("INFO")
                    .withMessage("GET_NOTIFICATIONS_BY_ENTITY_ID")
                    .withField("entityId", entityId)
                    .log();
            return ResponseEntity.ok(notificationsAdapter.getNotificationsByEntityId(entityId));
        } catch (RuntimeException e) {
            logger.logBuilder().withLevel("ERROR")
                    .withMessage(e.getMessage()).log();
            throw e;
        }
    }

    @PutMapping("/{id}/mark-read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        try {
            logger.logBuilder().withLevel("INFO")
                    .withMessage("MARK_NOTIFICATION_AS_READ")
                    .withField("notificationId", id)
                    .log();
            return ResponseEntity.ok(notificationsAdapter.markAsRead(id));
        } catch (RuntimeException e) {
            logger.logBuilder().withLevel("ERROR")
                    .withMessage(e.getMessage()).log();
            throw e;
        }
    }

    @PutMapping("/mark-all-read")
    public ResponseEntity<Void> markAllAsRead() {
        try {
            logger.logBuilder().withLevel("INFO")
                    .withMessage("MARK_ALL_NOTIFICATIONS_AS_READ")
                    .log();
            notificationsAdapter.markAllAsRead();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.logBuilder().withLevel("ERROR")
                    .withMessage(e.getMessage()).log();
            throw e;
        }
    }
}