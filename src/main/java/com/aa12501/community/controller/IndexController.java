package com.aa12501.community.controller;

import com.aa12501.community.entities.dto.UserDTO;
import com.aa12501.community.inter.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/")
    public String index(HttpServletResponse response,
                        HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setToken(cookie.getValue());
                    UserDTO user = loginService.selectWithoutPwd(userDTO);
                    if (user != null) {
                        user.setGmtLastLogin(System.currentTimeMillis());
                        loginService.updateUserState(user);
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        return "index";
    }
}
