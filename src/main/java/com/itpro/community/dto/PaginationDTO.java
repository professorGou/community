package com.itpro.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {

    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        this.totalPage = totalPage;
        this.page = page;
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }

        pages.add(page);
        for(int i = 1; i < 3; i++){
            if(page - i > 0){
                pages.add(0, page - i);
            }
            if(page + i <= totalPage ){
                pages.add(page + i);
            }
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
