package com.itpro.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Integer notifier;
    private Integer outerId;
    private String typeName;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
}
