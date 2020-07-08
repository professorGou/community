package com.itpro.community.mapper;

import com.itpro.community.pojo.Comment;
import com.itpro.community.pojo.CommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentMapper {
    long countByExample(CommentExample example);

    int deleteByExample(CommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExampleWithRowbounds(CommentExample example, RowBounds rowBounds);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}