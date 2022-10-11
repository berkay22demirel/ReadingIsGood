package com.berkay22demirel.readingisgood.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LogExecutionInterceptor implements HandlerInterceptor {

    private final static String REQUEST_START_TIME_ATTRIBUTE = "requestStartTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                request.setAttribute(REQUEST_START_TIME_ATTRIBUTE, System.currentTimeMillis());
                log.info("Starting controller method for {}.{}", handlerMethod.getBeanType().getSimpleName(), handlerMethod.getMethod().getName());
            }
        } catch (Exception e) {
            log.error("Caught an exception while executing handler method", e);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                log.info("Completed controller method for {}.{} takes {} ms", handlerMethod.getBeanType().getSimpleName(),
                        handlerMethod.getMethod().getName(),
                        (System.currentTimeMillis() - (long) request.getAttribute(REQUEST_START_TIME_ATTRIBUTE)));
            }
        } catch (Exception e) {
            log.error("Caught an exception while executing handler method", e);
        }
    }
}
