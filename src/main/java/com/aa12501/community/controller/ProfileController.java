package com.aa12501.community.controller;

import com.aa12501.community.entities.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profile(HttpServletRequest request,
                          HttpServletResponse response){
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        if(user == null){
            try {
                response.sendRedirect("/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "profile";
    }
}
