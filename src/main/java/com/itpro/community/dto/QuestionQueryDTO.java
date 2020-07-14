package com.itpro.community.dto;

import lombok.Data;

@Data
public class QuestionQueryDTO {

    private String sort;
    private Long time;
    private String tag;
    private String search;
    private Integer page;
    private Integer size;
}
