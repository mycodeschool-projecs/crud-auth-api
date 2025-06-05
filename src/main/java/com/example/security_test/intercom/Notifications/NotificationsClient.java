package com.example.security_test.intercom.Notifications;

import com.example.security_test.configuration.FeignClientConfiguration;
import com.example.security_test.configuration.FeignRetryConfiguration;
import com.example.security_test.model.Notification;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
    name = "${param.notifications-name}",
    url = "http://${param.notifications-service}:8083/api/notifications",
    configuration = {FeignClientConfiguration.class, FeignRetryConfiguration.class}
)
public interface NotificationsClient {

    @GetMapping
    ResponseEntity<List<Notification>> getAllNotifications();

    @GetMapping("/status/{read}")
    ResponseEntity<List<Notification>> getNotificationsByReadStatus(@PathVariable boolean read);

    @GetMapping("/service/{sourceService}")
    ResponseEntity<List<Notification>> getNotificationsBySourceService(@PathVariable String sourceService);

    @GetMapping("/entity-type/{entityType}")
    ResponseEntity<List<Notification>> getNotificationsByEntityType(@PathVariable String entityType);

    @GetMapping("/operation/{operation}")
    ResponseEntity<List<Notification>> getNotificationsByOperation(@PathVariable String operation);

    @GetMapping("/entity-id/{entityId}")
    ResponseEntity<List<Notification>> getNotificationsByEntityId(@PathVariable String entityId);

    @PutMapping("/{id}/mark-read")
    ResponseEntity<Notification> markAsRead(@PathVariable Long id);

    @PutMapping("/mark-all-read")
    ResponseEntity<Void> markAllAsRead();
}
