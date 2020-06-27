package com.itpro.community.service;

import com.itpro.community.dto.PaginationDTO;

public interface QuestionService {

    PaginationDTO list(Integer page, Integer size);
}
