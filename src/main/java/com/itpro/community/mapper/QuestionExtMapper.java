package com.itpro.community.mapper;


import com.itpro.community.pojo.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);

    int incCommentCount(Question record);

    List<Question> selectRelated(Question question);

/*    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);*/
}