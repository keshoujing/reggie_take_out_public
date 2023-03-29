//package com.reggie.controller.interceptor;
//
//import com.alibaba.fastjson.JSON;
//import com.reggie.common.Result;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//@Slf4j
//public class LoginInterceptor implements HandlerInterceptor {
//    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        String requestURI = request.getRequestURI();
//
//        log.info("intercept request: " + requestURI);
//
//        String[] allowedUrls = new String[]{
//                "/employee/login",
//                "/employee/logout",
//                "/backend/**",
//                "/frontend/**"
//        };
//
//        for (String url : allowedUrls) {
//            if(PATH_MATCHER.match(url, requestURI)) {
//                return true;
//            }
//        }
//
//        if (request.getSession().getAttribute("employee") != null) {
//            return true;
//        }
//
//        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
//        log.info("rejected request: " + requestURI);
//        return false;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//    }
//}
