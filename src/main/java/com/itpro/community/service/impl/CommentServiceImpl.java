package com.itpro.community.service.impl;

import com.itpro.community.enums.CommentTypeEnum;
import com.itpro.community.exception.CustomizeErrorCode;
import com.itpro.community.exception.CustomizeException;
import com.itpro.community.mapper.CommentMapper;
import com.itpro.community.mapper.QuestionExtMapper;
import com.itpro.community.mapper.QuestionMapper;
import com.itpro.community.pojo.Comment;
import com.itpro.community.pojo.Question;
import com.itpro.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;

    /**
     * 发送评论/回复
     * @param comment
     */
    @Override
    public void insertSelective(Comment comment) {
        //若评论的父类id为空or==0,无法评论，抛异常
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        //若评论的类型为空or不存在，抛异常
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        //若类型是评论，则回复评论
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //判断回复的评论是否存在
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){  //不存在直接抛异常
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //存在则直接插入
            commentMapper.insertSelective(comment);
        }else{  //类型是问题，评论问题
            //判断问题是否存在，此处的comment.parentId == question.id
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){   //不存在则抛异常
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //存在则直接插入
            commentMapper.insertSelective(comment);
            question.setCommentCount(1);
            //问题评论数+1
            questionExtMapper.incCommentCount(question);
        }
    }
}
