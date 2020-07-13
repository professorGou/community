package com.itpro.community.controller;

import com.itpro.community.dto.NotificationDTO;
import com.itpro.community.enums.NotificationTypeEnum;
import com.itpro.community.pojo.User;
import com.itpro.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    /**
     * 跳转到指定的问题页面
     * @param id        问题id
     * @param request
     * @return
     */
    @GetMapping("/notification/{id}")
    public String profile(@PathVariable("id") Integer id,
                          HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }
    }

}
