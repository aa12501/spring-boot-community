package com.aa12501.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LogoutController {

    @RequestMapping("/logout")
    public String logout(HttpServletResponse response,
                         HttpServletRequest request){
        //删除session
        request.getSession().removeAttribute("user");
        //删除cookie
        Cookie token = new Cookie("token", null);
        token.setMaxAge(0);
        response.addCookie(token);
        //重定向回到首页
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
