package com.example.wechat.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试启动服务控制类
 *
 * @author admin
 */
@RestController
@RequestMapping("/hello")
public class HelloWordController {

    @GetMapping("/word")
    public String helloWord() {
        return "hello_word!!!";
    }

    @GetMapping("/error")
    public String error(String url) {
        return "非常可惜呢，没有这个页面：" + url;
    }
}
