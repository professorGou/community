package com.itpro.community.exception;

/**
 * 常见错误码
 */
public enum  CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001, "你找的问题不在了，换一个试试吧！"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复！"),
    NO_LOGIN(2003, "当前操作需要登录，请先登录重试！"),
    SYS_ERROR(2004, "服务冒烟了，程序猿正在紧张修复中！"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在!"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在，换一个试试吧！"),
    CONTENT_IS_EMPTY(2007, "输入的内容不能为空！"),
    READ_NOTIFICATION_FAIL(2008, "这不是你的信息！"),
    NOTIFICATION_NOT_FOUND(2009, "消息不存在了！"),
    ;


    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
