package com.itpro.community.controller;

import com.itpro.community.dto.PaginationDTO;
import com.itpro.community.mapper.UserMapper;
import com.itpro.community.pojo.User;
import com.itpro.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    /**
     * 首页跳转
     * @param request
     * @param model
     * @param page  当前页码
     * @param size  每页显示的问题数量
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "2") Integer size){
        Cookie[] cookies = request.getCookies();
        //遍历cookie，查找是否有name=token的cookie
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //根据cookie的token获取token值，根据token获取用户信息
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        //若该用户存在，将用户信息存入session
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        PaginationDTO pagination = questionService.list(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}