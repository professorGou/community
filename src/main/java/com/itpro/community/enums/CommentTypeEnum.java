package com.itpro.community.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    //判断类型是否存在
    public static boolean isExist(Integer type) {
        for(CommentTypeEnum commentTypeEnum: CommentTypeEnum.values()){
            if(commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
