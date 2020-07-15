package com.itpro.community.controller;

import com.itpro.community.dto.ResultDTO;
import com.itpro.community.pojo.Comment;
import com.itpro.community.pojo.Question;
import com.itpro.community.service.CommentService;
import com.itpro.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {

    @Autowired
    CommentService commentService;
    @Autowired
    QuestionService questionService;

    @PostMapping("/likeComment")
    public Object likeComment(@RequestBody Comment comment){
        commentService.incLikeCount(comment);
        return ResultDTO.okOf();
    }

    @PostMapping("/likeQuestion")
    public Object likeQuestion(@RequestBody Question question){
        questionService.incLikeCount(question);
        return ResultDTO.okOf();
    }
}
