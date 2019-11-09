package com.aa12501.community.controller;

import com.aa12501.community.entities.dto.UserDTO;
import com.aa12501.community.inter.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String login(HttpServletRequest request,
                        HttpServletResponse response) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        if (user != null) {
            //用户已存在
            try {
                response.sendRedirect("/");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam(value = "userId", required = false) Integer userId,
                          @RequestParam(value = "userPassword", required = false) String password,
                          Model model,
                          HttpServletResponse response,
                          HttpServletRequest request) {
        if (userId == null) {
            String error = "账号未填写！";
            model.addAttribute("error", error);
            return "login";
        }
        if (password.trim().equals("")) {
            String error = "密码未填写！";
            model.addAttribute("error", error);
            model.addAttribute("userId", userId);
            return "login";
        }

        UserDTO checkUser = new UserDTO();
        checkUser.setUserId(userId);
        checkUser.setPassword(password);
        UserDTO userDTO = loginService.selectWithoutPwd(checkUser);
        if (userDTO == null) {
            String error = "账号或密码错误！";
            model.addAttribute("error", error);
            model.addAttribute("userId", userId);
            return "login";
        }

        userDTO.setToken(UUID.randomUUID().toString());
        userDTO.setGmtLastLogin(System.currentTimeMillis());
        loginService.updateUserState(userDTO);
        request.getSession().setAttribute("user", userDTO);
        Cookie cookie = new Cookie("token", userDTO.getToken());
        cookie.setMaxAge(30 * 60);
        response.addCookie(cookie);

        String headerUrl = (String) request.getSession().getAttribute("headerUrl");
        if (headerUrl != null) {
            //如果有上一个页面
            try {
                //重定向回之前页面
                response.sendRedirect(headerUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect("/");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @GetMapping("/registry")
    public String registry() {
        return "registry";
    }

    @PostMapping("/registry")
    public String doRegistry(@RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "userName", required = false) String userName,
                             @RequestParam(value = "userPassword", required = false) String userPassword,
                             @RequestParam(value = "userPasswordConfirm", required = false) String userPasswordConfirm,
                             Model model) {

        //判断各个值为空的情况
        if (email.trim() == "") {
            String error = "请输入邮箱！";
            model.addAttribute("error", error);
            model.addAttribute("userName", userName);
            return "registry";
        } else if (userName.trim() == "") {
            String error = "请输入名称！";
            model.addAttribute("error", error);
            model.addAttribute("email", email);
            return "registry";
        } else if (userPassword.trim() == "") {
            String error = "请输入密码！";
            model.addAttribute("userName", userName);
            model.addAttribute("error", error);
            return "registry";
        } else if (userPasswordConfirm.trim() == "") {
            String error = "请确认密码！";
            model.addAttribute("userName", userName);
            model.addAttribute("email", email);
            model.addAttribute("error", error);
            return "registry";
        }

        //密码不一致
        if (!userPassword.equals(userPasswordConfirm)) {
            String error = "密码前后不一致！";
            model.addAttribute("userName", userName);
            model.addAttribute("email", email);
            model.addAttribute("error", error);
            return "registry";
        }

        UserDTO checkUser = loginService.selectAll(new UserDTO());
        if (checkUser != null) {
            String error = "该邮箱已被注册！";
            model.addAttribute("userName", userName);
            model.addAttribute("email", email);
            model.addAttribute("error", error);
            return "registry";
        } else {
            //一切没问题
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(email);
            userDTO.setAccountType(0);
            userDTO.setName(userName);
            userDTO.setPassword(userPassword);
            userDTO.setGmtCreate(System.currentTimeMillis());
            userDTO.setGmtModified(System.currentTimeMillis());
            loginService.create(userDTO);
        }

        return "login";
    }
}
