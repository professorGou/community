package com.itpro.community.enums;

/**
 * 通知类型枚举类
 */
public enum NotificationTypeEnum {
    REPLY_QUESTION(1, "回复了问题"),
    REPLY_COMMENT(2, "回复了评论"),;

    private int type;
    private String name;

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据type返回对应的typeName  用于前端直接输出
     * @param type  通知类型
     * @return
     */
    public static String nameOfType(int type){
        for(NotificationTypeEnum notificationTypeEnum: NotificationTypeEnum.values()){
            if(notificationTypeEnum.getType() == type);
                return notificationTypeEnum.getName();
        }
        return "";
    }
}
