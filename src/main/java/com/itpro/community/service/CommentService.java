package com.itpro.community.service;

import com.itpro.community.dto.CommentDTO;
import com.itpro.community.enums.CommentTypeEnum;
import com.itpro.community.pojo.Comment;

import java.util.List;

public interface CommentService {

    void insertSelective(Comment comment);

    List<CommentDTO> listByTargetId(Integer id, CommentTypeEnum type);
}
