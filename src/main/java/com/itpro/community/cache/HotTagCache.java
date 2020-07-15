package com.itpro.community.cache;

import com.itpro.community.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class HotTagCache {
    private List<String> hots = new ArrayList<>();

    public void updateTags(Map<String, Integer> tags){
        int max = 10;
        //定义优先级队列
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);

        //遍历传入的map
        tags.forEach((name, priority) ->{
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            //当长度<3则直接add
            if(priorityQueue.size() < max){
                priorityQueue.add(hotTagDTO);
            }else { //当长度>=3时
                //查看队列头元素(升序排序，队列头元素最小)
                HotTagDTO minHotTag = priorityQueue.peek();
                //当前的标签比最小标签大
                if(hotTagDTO.compareTo(minHotTag) > 0){
                    //取出最小的，并将当前标签add入
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });
        //避免累加操作，定义一个list用于中间存储
        ArrayList<String> sortedTags = new ArrayList<>();
        //因为队列取出的元素按升序拍戏
        HotTagDTO poll = priorityQueue.poll();
        //循环取出，每次将取出的元素，放入list的第一个位置(循环结束后的list按降序排列)
        while(poll != null){
            sortedTags.add(0, poll.getName());
            poll = priorityQueue.poll();
        }
        hots = sortedTags;
    }
}
