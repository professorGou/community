package com.itpro.community.pojo;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private String tag;

    public Question() {
    }

    public Question(Integer id, String title, String description, Long gmtCreate, Long gmtModified, Integer creator, Integer viewCount, Integer commentCount, Integer likeCount, String tag) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.creator = creator;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.tag = tag;
    }
}
