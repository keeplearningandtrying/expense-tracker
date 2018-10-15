package com.sivalabs.expensetracker.web;

import com.sivalabs.expensetracker.config.CurrentUser;
import com.sivalabs.expensetracker.security.SecurityUser;
import com.sivalabs.expensetracker.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CurrentUserInterceptor implements HandlerInterceptor {
    @Autowired
    CurrentUser currentUser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SecurityUser loginUser = SecurityUtils.getLoginUser();
        if(loginUser != null) {
            currentUser.setId(loginUser.getUser().getId());
        }
        return true;
    }
}
