package com.vara.springsecurityplayground.filters;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class RobotFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Hellow from the robot fitler");
        String password = request.getHeader("x-robot-password");
        // only run if header exists
        if (!Collections.list(request.getHeaderNames()).contains("x-robot-password")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!password.equals("beep-boop")) {
            // do
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.getWriter().println("you are not robot");
            response.setHeader("content-type", "text/plain;charset=utf-8");
            return;


        }
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new RobotAuthentication());
        SecurityContextHolder.setContext(securityContext);
        filterChain.doFilter(request, response);

        filterChain.doFilter(request, response);
    }
}
