package com.example.wechat.web.controller;

import com.example.wechat.annotations.WebLog;
import com.example.wechat.core.Result;
import com.example.wechat.core.ResultGenerator;
import com.example.wechat.util.WeComUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录
 *
 * @author admin
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * code登录
     *
     * @param code 编码
     * @return 用户code
     */
    @GetMapping("/code")
    @WebLog(des = "code登录", response = true)
    public Result<?> code(String code) {
        if (StringUtils.isBlank(code)) {
            return ResultGenerator.genFailResult("code不能为空");
        }
        String mobileNum = WeComUtils.getMobileNumber(code);
        if (StringUtils.isNotBlank(mobileNum)) {
            return ResultGenerator.genSuccessResult(mobileNum);
        } else {
            return ResultGenerator.genFailResult("获取电话号码失败");
        }
    }
}
