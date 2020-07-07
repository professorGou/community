package com.itpro.community.service.impl;

import com.itpro.community.dto.PaginationDTO;
import com.itpro.community.dto.QuestionDTO;
import com.itpro.community.mapper.QuestionMapper;
import com.itpro.community.mapper.UserMapper;
import com.itpro.community.pojo.Question;
import com.itpro.community.pojo.QuestionExample;
import com.itpro.community.pojo.User;
import com.itpro.community.service.QuestionService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//import com.itpro.community.mapper.UserMapper;

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
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());

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
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offSet, size));
        //将查询出的问题数据，封装到questionDTO集合中
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question: questions) {
            User user =userMapper.selectByPrimaryKey(question.getCreator());
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
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);

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
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offSet, size));

        //将查询出的问题数据，封装到questionDTO集合中
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question: questions) {
            User user =userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    @Override
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion, example);
        }
    }


}
