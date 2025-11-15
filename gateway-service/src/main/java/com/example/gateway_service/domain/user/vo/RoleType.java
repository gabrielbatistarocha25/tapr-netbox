package com.example.gateway_service.domain.user.vo;

public enum RoleType {
    CUSTOMER(1),
    ADMIN(4); // Removemos WAITER e CHEF

    private final int level;

    RoleType(int level) {
        this.level = level;
    }

    public boolean covers(RoleType other) {
        return this.level >= other.level;
    }

    public int getLevel() {
        return this.level;
    }
}