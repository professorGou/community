package com.itpro.community.service;

import com.itpro.community.dto.CommentDTO;
import com.itpro.community.enums.CommentTypeEnum;
import com.itpro.community.pojo.Comment;
import com.itpro.community.pojo.User;

import java.util.List;

public interface CommentService {

    void insertSelective(Comment comment, User user);

    List<CommentDTO> listByTargetId(Integer id, CommentTypeEnum type);

    void incLikeCount(Comment comment);

}
