package com.aa12501.community.interceptor;

import com.aa12501.community.entities.dto.UserDTO;
import com.aa12501.community.inter.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取当前页面的url，用来作为登录之后返回的上一个界面
        String headerUrl = request.getServletPath();
        //如果是登录界面，就不管它
        if (!headerUrl.equals("/login")) {
            request.getSession().setAttribute("headerUrl", headerUrl);

            String token = getToken(request, response);
            if (token == null) {
                //token失效，不存在，则删除session中的user，以免被后续页面读取到
                request.getSession().removeAttribute("user");
                //如果不是主页，则跳转到登录页面
                if (!headerUrl.equals("/")) {
                    response.sendRedirect("/login");
                }
            } else {
                UserDTO checkUser = new UserDTO();
                checkUser.setToken(token);
                UserDTO user = loginService.selectWithoutPwd(checkUser);
                if (user == null) {
                    //token错误，删除token，清空session，跳转回登录页面
                    Cookie cookie = new Cookie("token", null);
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    request.getSession().removeAttribute("user");
                    //如果不是主页，则跳转到登录页面
                    if (!headerUrl.equals("/")) {
                        response.sendRedirect("/login");
                    }
                } else {
                    //一切没问题
                    request.getSession().setAttribute("user", user);
                }
            }

        }
        return true;
    }

    public String getToken(HttpServletRequest request,
                           HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    if (token != null && !token.trim().equals("")) {
                        return token;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
