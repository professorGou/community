package com.itpro.community.schedule;

import com.itpro.community.cache.HotTagCache;
import com.itpro.community.mapper.QuestionMapper;
import com.itpro.community.pojo.Question;
import com.itpro.community.pojo.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTagTasks {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    HotTagCache hotTagCache;

    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    public void reportCurrentTime(){
        int offset = 0;
        int limit = 20;
        log.info("hotTagSchedule start {}", new Date());
        List<Question> list = new ArrayList<>();

        Map<String, Integer> priorities = new HashMap<>();
        while(offset == 0 || list.size() == limit){
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for(Question question: list){

                String[] tags = StringUtils.split(question.getTag(), ",");
                for(String tag: tags){
                    Integer priority = priorities.get(tag);
                    if(priority != null){
                        priorities.put(tag, priority + 5 + question.getCommentCount());
                    }else {
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                }
            }
            offset+= limit;
        }
        hotTagCache.updateTags(priorities);
        log.info("hotTagSchedule stop {}", new Date());
    }
}
