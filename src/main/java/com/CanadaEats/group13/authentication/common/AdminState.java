package com.CanadaEats.group13.authentication.common;

import com.CanadaEats.group13.utils.ApplicationConstants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class AdminState implements IUserRoleState{
    @Override
    public void userRoleState(HttpServletResponse response) {
        Cookie cookie = new Cookie(ApplicationConstants.COOKIE_ROLENAME, ApplicationConstants.ADMIN_ROLE);
        response.addCookie(cookie);
    }
}
