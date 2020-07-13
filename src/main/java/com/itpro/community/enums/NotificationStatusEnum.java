package com.itpro.community.enums;

/**
 * 通知状态枚举类
 */
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1),;

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
