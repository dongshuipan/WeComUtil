package com.example.wechat.web.controller;

import com.example.wechat.annotations.WebLog;
import com.example.wechat.util.LogUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录
 *
 * @author admin
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/logColor")
    @WebLog(des = "测试日志颜色", response = true)
    public String logColor() {
        LogUtils.main(null);
        return "over!!!";
    }
}
