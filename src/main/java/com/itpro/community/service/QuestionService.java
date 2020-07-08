package com.itpro.community.service;

import com.itpro.community.dto.PaginationDTO;
import com.itpro.community.dto.QuestionDTO;
import com.itpro.community.pojo.Question;

public interface QuestionService {

    PaginationDTO list(Integer page, Integer size);

    PaginationDTO list(Integer id, Integer page, Integer size);

    QuestionDTO getById(Integer id);

    void createOrUpdate(Question question);

    void incView(Integer id);
}
