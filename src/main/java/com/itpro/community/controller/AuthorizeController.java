package com.itpro.community.controller;

import com.itpro.community.dto.AccessTokenDTO;
import com.itpro.community.dto.GithubUser;
import com.itpro.community.mapper.UserMapper;
import com.itpro.community.pojo.User;
import com.itpro.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;
    @Autowired
    UserMapper userMapper;
    @Value("${github.client.id}")
    String clientId;
    @Value("${github.client.secret}")
    String clientSecret;
    @Value("${github.redirect.uri}")
    String redirectUri;

    /**
     * 根据callback传回的信息，实现登录
     * @param code
     * @param state
     * @param session
     * @param response
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name ="state") String state,
                           HttpSession session,
                           HttpServletResponse response){
        //将信息封装到AccessTokenDTO中
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        //通过accessTokenDTO获取token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //根据token，获取用户
        GithubUser githubUser = githubProvider.getUser(accessToken);
        //若获取的用户不为空，将相应信息，封装到user再插入数据库
        if (githubUser != null && githubUser.getId() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.insert(user);

            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }
}
