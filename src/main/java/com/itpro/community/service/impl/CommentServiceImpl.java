package com.itpro.community.service.impl;

import com.itpro.community.dto.CommentDTO;
import com.itpro.community.enums.CommentTypeEnum;
import com.itpro.community.exception.CustomizeErrorCode;
import com.itpro.community.exception.CustomizeException;
import com.itpro.community.mapper.*;
import com.itpro.community.pojo.*;
import com.itpro.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;

    /**
     * 发送评论/回复
     * @param comment
     */
    @Transactional
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

            // 增加评论数
            /*Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);*/
            dbComment.setCommentCount(1);
            commentExtMapper.incCommentCount(dbComment);
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

    /**
     * 根据对应的id，查出该问题/评论的所有回复
     * @param id    questionId == parentId
     * @param type
     * @return
     */
    @Override
    public List<CommentDTO> listByTargetId(Integer id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        //根据parentId和type，查出所有评论
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //stream流遍历comments，并只获取里面的commentator(Set集合不重复的)，再将commentator放入set中
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //查出用户id在userIds里面的所有用户信息
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        //将users的内容封装成map
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //循环将comments的内容放入commentDTO,同时根据userMap的key(id)的值，将user信息放入commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
