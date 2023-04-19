package com.example.wechat.util;

import com.example.wechat.WeChatApplication;
import com.example.wechat.core.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具
 *
 * @author admin
 */
public class LogUtils {

    private static final Logger logger = LoggerFactory.getLogger(WeChatApplication.class);

    public static void debug(String msg) {
        logger.debug(msg);
    }

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }

    public static void error(String msg) {
        logger.error(msg);
    }

    public static void error(String msg, Exception e) {
        logger.error(msg, e);
    }

    public static void main(String[] args) {
        debug("debug");
        info("info");
        warn("warn");
        error("error-1");
        error("error-2", new Exception(new ServiceException("文件异常")));
    }
}
