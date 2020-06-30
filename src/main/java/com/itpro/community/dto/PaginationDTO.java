package com.itpro.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页功能的传输类
 */
@Data
public class PaginationDTO {

    private List<QuestionDTO> questions;            //封装所有问题的信息
    private boolean showPrevious;                   //是否有前一页标签
    private boolean showFirstPage;                  //是否有第一页标签
    private boolean showNext;                       //是否有下一页标签
    private boolean showEndPage;                    //是否有最后一页标签
    private Integer page;                           //当前页面
    private List<Integer> pages = new ArrayList<>();//当前显示的页码集合
    private Integer totalPage;                      //总页码数

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.page = page;
        /*若总问题数除以每页问题数没有余数：则直接得到总页数
        * 若总问题数除以每页问题数有余数：则将得到的总页数+1
        */

        pages.add(page);
        //显示的页码集合，只能是当前页码的前3个和后3个 例：当前页码为1 最多只显示：1,2,3,4；当前页码为5 最多只显示：2,3,4,5,6,7,8
        for(int i = 1; i <= 3; i++){
            if(page - i > 0)
                pages.add(0, page - i);
            if(page + i <= totalPage )
                pages.add(page + i);
        }

        //当 当前页为第一页时：不显示上一页标签；否则显示上一页标签
        if(page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //当 当前页为最后一页时：不显示下一页标签；否则显示下一页标签
        if(page == totalPage){
            showNext = false;
        }else {
            showNext = true;
        }
        //当当前所有页面不包含第一页时，显示第一页标签，否则不显示
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }
        //当当前所有页面不包含最后一页时，显示最后一页标签，否则不显示
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
