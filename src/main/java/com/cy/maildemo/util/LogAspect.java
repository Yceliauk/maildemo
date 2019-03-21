package com.cy.maildemo.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

public class LogAspect {
    private Logger logger = LogManager.getLogger(LogAspect.class);
    // 保证每个线程都有一个单独的实例
    private ThreadLocal<Long> time = new ThreadLocal<>();
    @Pointcut("execution(* com.cy.maildemo.service..*.*(..))")
    public void pointcut() {
    }
    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        time.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("=====================请求开始======================");
        //记录请求的内容
        logger.info("Request URL: " + request.getRequestURL().toString());
        logger.info("Request Method: " + request.getMethod());
        logger.info("User-Agent: " + request.getHeader("User-Agent"));
        logger.info("Class Method: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("Cookies: " + request.getCookies());
        logger.info("Params: " + Arrays.toString(joinPoint.getArgs()));
        Enumeration<String> enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            String paraName = enums.nextElement();
            logger.info(paraName + ":" + request.getParameter(paraName));
        }
    }
    @AfterReturning("pointcut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        logger.info("耗时 : " + ((System.currentTimeMillis() - time.get())) + "ms");
        logger.info("AppLogAspect.doAfterReturning()");
        logger.info("=====================请求结束======================");
    }
}
