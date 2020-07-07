package com.itpro.community.advice;

import com.itpro.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于捕获springboot中抛出的异常，并根据情况显示回页面
 * @ControllerAdvice 方式只能处理控制器抛出的异常。此时请求已经进入控制器中
 */
@ControllerAdvice
public class CustomizeExceptionHandler{

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model){
        HttpStatus status = getStatus(request);

        if(e instanceof CustomizeException){
            model.addAttribute("message", e.getMessage());
        }else{
            model.addAttribute("message", "服务冒烟了，程序猿正在紧张修复中！！！");
        }

        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode  = (Integer)request.getAttribute("javax.servlet.error.status.code");
        if(statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
