package com.itpro.community.service.impl;

import com.itpro.community.dto.PaginationDTO;
import com.itpro.community.dto.QuestionDTO;
import com.itpro.community.mapper.QuestionMapper;
import com.itpro.community.mapper.UserMapper;
import com.itpro.community.pojo.Question;
import com.itpro.community.pojo.User;
import com.itpro.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        //获得问题总数
        Integer totalPage;
        //获得问题总数
        Integer totalCount = questionMapper.count();

        if(totalCount % size == 0)
            totalPage = totalCount / size;
        else
            totalPage = totalCount / size + 1;
        //错误校验：若输入的当前页码<1 则=1；若>总页数 则=总页数
        if(page < 1)
            page = 1;
        if(page > totalPage)
            page = totalPage;

        paginationDTO.setPagination(totalPage, page);
        //计算页码起点 例：每页显示5条问题，当前为第1页，则页码起点= 0
        Integer offSet = size * (page - 1);
        List<Question> questions = questionMapper.list(offSet, size);
        //将查询出的问题数据，封装到questionDTO集合中
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question: questions) {
            User user =userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    @Override
    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        //获得问题总数
        Integer totalCount = questionMapper.countByUserId(userId);

        if(totalCount % size == 0)
            totalPage = totalCount / size;
        else
            totalPage = totalCount / size + 1;

        //错误校验：若输入的当前页码<1 则=1；若>总页数 则=总页数
        if(page < 1)
            page = 1;
        if(page > totalPage)
            page = totalPage;

        paginationDTO.setPagination(totalPage, page);

        //计算页码起点 例：每页显示5条问题，当前为第1页，则页码起点= 0
        Integer offSet = size * (page - 1);
        List<Question> questions = questionMapper.listByUserId(userId, offSet, size);
        //将查询出的问题数据，封装到questionDTO集合中
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question: questions) {
            User user =userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }
}
