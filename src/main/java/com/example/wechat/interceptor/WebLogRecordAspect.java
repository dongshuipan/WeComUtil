package com.example.wechat.interceptor;

import com.example.wechat.annotations.Args;
import com.example.wechat.annotations.WebLog;
import com.example.wechat.util.HttpUtil;
import com.example.wechat.util.JsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Component 定义一个切面
 *
 * @author admin
 */
@Aspect
@Component
public class WebLogRecordAspect {
    private static final Logger logger = LoggerFactory.getLogger(WebLogRecordAspect.class);

    /**
     * 定义切点Pointcut
     */
    @Pointcut("@annotation(com.example.wechat.annotations.WebLog)")
    public void controllerLog() {
    }

    /**
     * 通知（环绕）
     *
     * @param point ？
     * @throws Throwable 异常
     */
    @Around("controllerLog()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        //开始时间
        long time = System.currentTimeMillis();
        Object result = null;
        Exception ce = null;
        try {
            //执行方法
            result = point.proceed();
            return result;
        } catch (Exception e) {
            ce = e;
            throw e;
        } finally {
            //执行完成时间
            time = System.currentTimeMillis() - time;
            try {
                //获取方法信息
                MethodSignature signature = (MethodSignature) point.getSignature();
                WebLog annotation = signature.getMethod().getAnnotation(WebLog.class);
                logger.info("------------------------请求日志输出START------------------------");
                logger.info("URL:{}", HttpUtil.getUrl());
                logger.info("des:{}", annotation.des());
                logger.info("执行时间:{}毫秒", time);
                logger.info("clientIp:{}", HttpUtil.getIp());

                Args args = annotation.args();
                if (args.print()) {
                    if (args.filterNums().length == 0) {
                        logger.info("request args:{}", JsonUtil.toJsonString(point.getArgs()));
                    } else {
                        List<Integer> filterArgNumList = Arrays.stream(args.filterNums()).boxed().collect(Collectors.toList());
                        List<Object> argDataList = new ArrayList<>();
                        for (int i = 0; i < point.getArgs().length; i++) {
                            if (!filterArgNumList.contains(i)) {
                                argDataList.add(point.getArgs()[i]);
                            }
                        }
                        logger.info("request args:{}", JsonUtil.toJsonString(argDataList));
                    }
                }

                if (annotation.response()) {
                    logger.info("response Data:{}", JsonUtil.toJsonString(result));
                }
                if (annotation.exception()) {
                    logger.info("excute Exception:", ce);
                }
                logger.info("------------------------请求日志输出END------------------------");
            } catch (Exception e) {
                logger.error("拦截日志输出发生异常", e);
            }
        }
    }
}