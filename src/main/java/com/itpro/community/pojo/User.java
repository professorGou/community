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
    private String avatarUrl;

    public User() {
    }

    public User(Integer id, String accountId, String name, String token, Long gmtCreate, Long gmtModified, String avatarUrl) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.token = token;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.avatarUrl = avatarUrl;
    }
}
