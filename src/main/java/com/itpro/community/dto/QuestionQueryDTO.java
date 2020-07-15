package com.itpro.community.dto;

import lombok.Data;

@Data
public class QuestionQueryDTO {

    private Long time;
    private String sort;
    private String tag;
    private String search;
    private Integer page;
    private Integer size;
}
