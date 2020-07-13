package com.itpro.community.service.impl;

import com.itpro.community.dto.PaginationDTO;
import com.itpro.community.dto.QuestionDTO;
import com.itpro.community.exception.CustomizeErrorCode;
import com.itpro.community.exception.CustomizeException;
import com.itpro.community.mapper.QuestionExtMapper;
import com.itpro.community.mapper.QuestionMapper;
import com.itpro.community.mapper.UserMapper;
import com.itpro.community.pojo.Question;
import com.itpro.community.pojo.QuestionExample;
import com.itpro.community.pojo.User;
import com.itpro.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//import com.itpro.community.mapper.UserMapper;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;

    /**
     * 查询所有问题，分页展示
     * @param page
     * @param size
     * @return
     */
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
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offSet, size));
        //将查询出的问题数据，封装到questionDTO集合中
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question: questions) {
            User user =userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    /**
     * 查询某个用户的所有问题，分页展示
     * @param userId
     * @param page
     * @param size
     * @return
     */
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
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    /**
     * 根据id获取某条问题及发布者信息
     * @param id
     * @return
     */
    @Override
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    /**
     * 创建/修改问题(判断question.id是否为空)
     * @param question
     */
    @Override
    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
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
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    /**
     * 增加问题阅读数
     * @param id
     */
    @Override
    public void incView(Integer id) {
        Question updateQuestion = new Question();
        updateQuestion.setId(id);
        updateQuestion.setViewCount(1);
        questionExtMapper.incView(updateQuestion);
    }

    /**
     *
     * @param queryDTO
     * @return
     */
    @Override
    public List<QuestionDTO> selectRelate(QuestionDTO queryDTO) {
        if(StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        //将tags分解成多个单独标签，放入数组中
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        //将tags转成正则表达式格式，用于sql语句判断是否符合条件
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }


}
