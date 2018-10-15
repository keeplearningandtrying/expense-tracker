package com.sivalabs.expensetracker.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public static SecurityUser getLoginUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal()!= null &&
                authentication.getPrincipal() instanceof SecurityUser)
        {
            return (SecurityUser) authentication.getPrincipal();
        }
        return null;
    }
}