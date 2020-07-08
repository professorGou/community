package com.itpro.community.controller;

import com.itpro.community.dto.CommentDTO;
import com.itpro.community.mapper.CommentMapper;
import com.itpro.community.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(1);
        commentMapper.insertSelective(comment);
        return null;
    }
}
