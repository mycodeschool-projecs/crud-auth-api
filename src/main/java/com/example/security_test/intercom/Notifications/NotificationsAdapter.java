package com.example.security_test.intercom.Notifications;

import com.example.security_test.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationsAdapter {
    private final NotificationsClient notificationsClient;

    public NotificationsAdapter(NotificationsClient notificationsClient) {
        this.notificationsClient = notificationsClient;
    }

    public List<Notification> getAllNotifications() {
        try {
            log.info("Attempting to get all notifications from Notifications Service");
            ResponseEntity<List<Notification>> resp = notificationsClient.getAllNotifications();
            if (resp == null) {
                log.error("Received null response from Notifications Service");
                throw new RuntimeException("Received null response from Notifications Service");
            }
            if (resp.getBody() == null) {
                log.error("Received null body in response from Notifications Service");
                throw new RuntimeException("Received null body in response from Notifications Service");
            }
            log.info("Successfully retrieved {} notifications", resp.getBody().size());
            return resp.getBody();
        } catch (Exception e) {
            log.error("Failed to get all notifications: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve notifications: " + e.getMessage(), e);
        }
    }

    public List<Notification> getNotificationsByReadStatus(boolean read) {
        try {
            log.info("Attempting to get notifications with read status: {}", read);
            ResponseEntity<List<Notification>> resp = notificationsClient.getNotificationsByReadStatus(read);
            if (resp == null) {
                log.error("Received null response from Notifications Service");
                throw new RuntimeException("Received null response from Notifications Service");
            }
            if (resp.getBody() == null) {
                log.error("Received null body in response from Notifications Service");
                throw new RuntimeException("Received null body in response from Notifications Service");
            }
            log.info("Successfully retrieved {} notifications with read status: {}", resp.getBody().size(), read);
            return resp.getBody();
        } catch (Exception e) {
            log.error("Failed to get notifications by read status: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve notifications by read status: " + e.getMessage(), e);
        }
    }

    public List<Notification> getNotificationsBySourceService(String sourceService) {
        try {
            log.info("Attempting to get notifications from source service: {}", sourceService);
            ResponseEntity<List<Notification>> resp = notificationsClient.getNotificationsBySourceService(sourceService);
            if (resp == null) {
                log.error("Received null response from Notifications Service");
                throw new RuntimeException("Received null response from Notifications Service");
            }
            if (resp.getBody() == null) {
                log.error("Received null body in response from Notifications Service");
                throw new RuntimeException("Received null body in response from Notifications Service");
            }
            log.info("Successfully retrieved {} notifications from source service: {}", resp.getBody().size(), sourceService);
            return resp.getBody();
        } catch (Exception e) {
            log.error("Failed to get notifications by source service: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve notifications by source service: " + e.getMessage(), e);
        }
    }

    public List<Notification> getNotificationsByEntityType(String entityType) {
        try {
            log.info("Attempting to get notifications for entity type: {}", entityType);
            ResponseEntity<List<Notification>> resp = notificationsClient.getNotificationsByEntityType(entityType);
            if (resp == null) {
                log.error("Received null response from Notifications Service");
                throw new RuntimeException("Received null response from Notifications Service");
            }
            if (resp.getBody() == null) {
                log.error("Received null body in response from Notifications Service");
                throw new RuntimeException("Received null body in response from Notifications Service");
            }
            log.info("Successfully retrieved {} notifications for entity type: {}", resp.getBody().size(), entityType);
            return resp.getBody();
        } catch (Exception e) {
            log.error("Failed to get notifications by entity type: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve notifications by entity type: " + e.getMessage(), e);
        }
    }

    public List<Notification> getNotificationsByOperation(String operation) {
        try {
            log.info("Attempting to get notifications for operation: {}", operation);
            ResponseEntity<List<Notification>> resp = notificationsClient.getNotificationsByOperation(operation);
            if (resp == null) {
                log.error("Received null response from Notifications Service");
                throw new RuntimeException("Received null response from Notifications Service");
            }
            if (resp.getBody() == null) {
                log.error("Received null body in response from Notifications Service");
                throw new RuntimeException("Received null body in response from Notifications Service");
            }
            log.info("Successfully retrieved {} notifications for operation: {}", resp.getBody().size(), operation);
            return resp.getBody();
        } catch (Exception e) {
            log.error("Failed to get notifications by operation: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve notifications by operation: " + e.getMessage(), e);
        }
    }

    public List<Notification> getNotificationsByEntityId(String entityId) {
        try {
            log.info("Attempting to get notifications for entity ID: {}", entityId);
            ResponseEntity<List<Notification>> resp = notificationsClient.getNotificationsByEntityId(entityId);
            if (resp == null) {
                log.error("Received null response from Notifications Service");
                throw new RuntimeException("Received null response from Notifications Service");
            }
            if (resp.getBody() == null) {
                log.error("Received null body in response from Notifications Service");
                throw new RuntimeException("Received null body in response from Notifications Service");
            }
            log.info("Successfully retrieved {} notifications for entity ID: {}", resp.getBody().size(), entityId);
            return resp.getBody();
        } catch (Exception e) {
            log.error("Failed to get notifications by entity ID: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve notifications by entity ID: " + e.getMessage(), e);
        }
    }

    public Notification markAsRead(Long id) {
        try {
            log.info("Attempting to mark notification with ID {} as read", id);
            ResponseEntity<Notification> resp = notificationsClient.markAsRead(id);
            if (resp == null) {
                log.error("Received null response from Notifications Service");
                throw new RuntimeException("Received null response from Notifications Service");
            }
            if (resp.getBody() == null) {
                log.error("Received null body in response from Notifications Service");
                throw new RuntimeException("Received null body in response from Notifications Service");
            }
            log.info("Successfully marked notification with ID {} as read", id);
            return resp.getBody();
        } catch (Exception e) {
            log.error("Failed to mark notification as read: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to mark notification as read: " + e.getMessage(), e);
        }
    }

    public void markAllAsRead() {
        try {
            log.info("Attempting to mark all notifications as read");
            ResponseEntity<Void> resp = notificationsClient.markAllAsRead();
            if (resp == null) {
                log.error("Received null response from Notifications Service");
                throw new RuntimeException("Received null response from Notifications Service");
            }
            log.info("Successfully marked all notifications as read");
        } catch (Exception e) {
            log.error("Failed to mark all notifications as read: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to mark all notifications as read: " + e.getMessage(), e);
        }
    }
}
