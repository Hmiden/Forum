package com.example.resource_service.entity;

public enum ResourceStatus {
    PENDING,    // Waiting for review
    APPROVED,   // Approved and visible to all
    REJECTED    // Rejected - not visible to regular users
}
