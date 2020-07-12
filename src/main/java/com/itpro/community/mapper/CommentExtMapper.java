package com.itpro.community.mapper;

import com.itpro.community.pojo.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}