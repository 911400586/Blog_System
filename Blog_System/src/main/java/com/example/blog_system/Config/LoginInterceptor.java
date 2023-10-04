package com.example.blog_system.Config;

import com.example.blog_system.Common.AjaxResult;
import com.example.blog_system.Common.ApplicationVariable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    ObjectMapper mapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if(session!=null && session.getAttribute(ApplicationVariable.SESSION_KEY_USERINFO)!=null)
        {
            return true;
        }
        //未登录
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        mapper.writeValue(response.getWriter(), AjaxResult.fail(300,"未登录"));
        return false;
    }
}
