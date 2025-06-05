package com.example.security_test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    private Long id;
    private String operation; // CREATE, UPDATE, DELETE
    private String entityType; // The type of entity that was modified (e.g., "User", "Product")
    private String entityId; // The ID of the entity that was modified
    private String details; // Additional details about the operation
    private String sourceService; // The service that performed the operation
    private Date timestamp;
    private boolean read;
}