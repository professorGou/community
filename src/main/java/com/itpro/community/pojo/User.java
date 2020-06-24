package com.itpro.community.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;

    public User() {
    }

    public User(Integer id, String accountId, String name, String token, Long gmtCreate, Long gmtModified) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.token = token;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }
}
