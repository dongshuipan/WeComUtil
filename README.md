# 企业微信自建应用登录



## 创建自建应用

[企业微信管理后台登录连接](https://work.weixin.qq.com/wework_admin/loginpage_wx)

### 创建应用

![image-20230418154354429](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418154354429.png)

![image-20230418155319262](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418155319262.png)

### 设置应用页面

![image-20230418155411907](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418155411907.png)

设置自建应用的页面链接

![image-20230418155604188](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418155604188.png)

### 设置网页可信域名

企业微信网页授权时允许通过的回调域名

![image-20230418155942277](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418155942277.png)

![image-20230418155751274](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418155751274.png)

### 设置网页授权可信IP

前后端分离，后端获取用户信息时，允许访问相关接口的IP地址

![image-20230418160020373](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418160020373.png)

![image-20230418160109012](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418160109012.png)

## 获取相关参数

### corpid （企业ID）

![image-20230418161114428](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418161114428.png)

### corpsecret （应用的凭证密钥）

![image-20230418161536410](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418161536410.png)

![image-20230418161554207](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418161554207.png)

这里我用的是在手机企业微信中接收的信息

![image-20230418162133789](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418162133789.png)

![image-20230418162105919](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418162105919.png)

![image-20230418162149144](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418162149144.png)



### AgentId（应用ID）

![image-20230418161438034](https://fastly.jsdelivr.net/gh/dongshuipan/blog-file/blog/企业微信自建应用登录/image-20230418161438034.png)

## 前端

[构造网页授权链接](https://developer.work.weixin.qq.com/document/path/91022)

### 前端页面代码

```html
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>微信登录</title>
</head>

<body>
<script>
  let GWC = {
    version: '1.2.0',
    urlParams: {},

    /**
     * 给url中添加请求参数
     * @param url 请求url
     * @param params 请求参数
     * @returns {string} 拥有参数的url
     */
    appendParams: function(url, params) {
      if (params) {
        let baseWithSearch = url.split('#')[0]
        let hash = url.split('#')[1]
        for (let key in params) {
          let attrValue = params[key]
          if (attrValue !== undefined) {
            let newParam = key + '=' + attrValue
            if (baseWithSearch.indexOf('?') > 0) {
              let oldParamReg = new RegExp('^' + key + '=[-%.!~*\'\(\)\\w]*', 'g')
              if (oldParamReg.test(baseWithSearch)) {
                baseWithSearch = baseWithSearch.replace(oldParamReg, newParam)
              } else {
                baseWithSearch += '&' + newParam
              }
            } else {
              baseWithSearch += '?' + newParam
            }
          }
        }

        if (hash) {
          url = baseWithSearch + '#' + hash
        } else {
          url = baseWithSearch
        }
      }
      return url
    },

    /**
     * 获取url中的参数
     */
    getUrlParams: function() {
      let pairs = location.search.substring(1).split('&')
      for (let i = 0; i < pairs.length; i++) {
        let pos = pairs[i].indexOf('=')
        if (pos === -1) {
          continue
        }
        GWC.urlParams[pairs[i].substring(0, pos)] = decodeURIComponent(pairs[i].substring(pos + 1))
      }
    },

    /**
     * 根据code重定向
     */
    doRedirect: function() {

      //eg:https://open.weixin.qq.com/connect/oauth2/authorize?appid=CORPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE&agentid=AGENTID#wechat_redirect
      let baseUrl = 'https://open.weixin.qq.com/connect/oauth2/authorize#wechat_redirect'

      //微信授权成功回调的用户code
      let code = GWC.urlParams['code']

      //todo 需替换为企业自身corpid
      let appId = 'wwdf6******8edce7'

      //todo 需替换为企业自身AgentId
      let agentid = '1000004'

      //需要手动授权（无需修改）
      let scope = 'snsapi_privateinfo'

      //待定
      let state = GWC.urlParams['state']

      //重定向到的url
      let redirectUri

      //判断是否拥有用户code（通过code获取用户信息）
      if (!code) {
        alert('go')
        //填充请求参数
        redirectUri = GWC.appendParams(baseUrl, {
          'appid': appId,
          'redirect_uri': encodeURIComponent(location.href),
          'response_type': 'code',
          'scope': scope,
          'state': encodeURIComponent(state),
          'agentid': agentid
        })

      } else {

        //请求后端地址
        let url = 'http://********.cloud:8821/login/code?'
        alert('code:' + code)
        //第二步，从微信授权页面跳转回来，已经获取到了code，再次跳转到实际所需页面
        redirectUri = GWC.appendParams(url, {
          'code': code,
          'state': encodeURIComponent(state)
        })

      }

      alert('redirectUri:' + redirectUri)
      location.href = redirectUri
    }
  }

  //获取url中的参数
  GWC.getUrlParams()
  //重定向
  GWC.doRedirect()
</script>
</body>

</html>

```

## 后端

### 接口说明

[获取access_token](https://developer.work.weixin.qq.com/document/path/91039)

[获取访问用户身份](https://developer.work.weixin.qq.com/document/path/91023)

[获取访问用户敏感信息](https://developer.work.weixin.qq.com/document/path/95833)

### Controller

```java
package com.example.wechat.web.controller;

import com.example.wechat.util.WeComUtils;
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
     * 企业微信用户code获取用户信息
     *
     * @param code 编码
     * @return 用户code
     */
    @GetMapping("/code")
    public String code(String code) {
        return WeComUtils.getUserDetail(code);
    }
}
```

### Util

需要将

```java
package com.example.wechat.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 企业微信工具
 *
 * @author renjie
 */
public class WeComUtils {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(WeComUtils.class);

    /**
     * 企业微信核心请求参数
     */
    private enum CORP_WECHAT_CODE {
        /**
         * 企业ID
         * todo 需填写企业自身企业ID
         */
        CORP_ID("corpid", "wwdf6*******dce7"),
        /**
         * 应用的凭证密钥
         * todo 需填写企业自身企业ID
         */
        CORP_SECRET("corpsecret", "Q6de2D421pYbSBM*******A8FFu_PE7KKmVzir0"),
        /**
         * 获取操作TOKEN
         * 例如：GET https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET
         */
        ACCESS_TOKEN_URL("accessTokenUrl", "https://qyapi.weixin.qq.com/cgi-bin/gettoken"),
        /**
         * 获取访问用户身份
         * 例如：GET https://qyapi.weixin.qq.com/cgi-bin/auth/getuserinfo?access_token=ACCESS_TOKEN&code=CODE
         */
        USER_TICKET_URL("userTicketUrl", "https://qyapi.weixin.qq.com/cgi-bin/auth/getuserinfo"),
        /**
         * 获取访问用户敏感信息
         * 例如：POST https://qyapi.weixin.qq.com/cgi-bin/auth/getuserdetail?access_token=ACCESS_TOKEN
         * {
         * "user_ticket": "USER_TICKET"
         * }
         */
        USER_DETAIL_URL("getUserDetail", "https://qyapi.weixin.qq.com/cgi-bin/auth/getuserdetail");
        /**
         * 名
         */
        private final String name;
        /**
         * 值
         */
        private final String value;

        CORP_WECHAT_CODE(String name, String value) {
            this.value = value;
            this.name = name;
        }

        String toParam() {
            return name + "=" + value;
        }
    }

    /**
     * 参数名称
     */
    private enum ParamName {

        /**
         * 错误码
         */
        ERR_CODE("errcode"),

        /**
         * 请求成功 (错误码)
         */
        SUCCESS_ERR_CODE("0"),

        /**
         * 返回openid的参数名称
         */
        OPENID("openid"),
        /**
         * 返回access_token的参数名称
         */
        ACCESS_TOKEN("access_token"),
        /**
         * 返回user_ticket的参数名称
         */
        USER_TICKET("user_ticket"),
        /**
         * 返回 external_userid 的参数名称
         */
        EXTERNAL_USERID("external_userid"),
        /**
         * 返回 phoneNumber 的参数名称
         */
        PHONE_NUMBER("phoneNumber"),
        /**
         * 返回 mobile 的参数名称
         */
        MOBILE("mobile"),
        /**
         * 返回 phone_info 的参数名称
         */
        PHONE_INFO("phone_info");

        /**
         * 代码
         */
        public final String value;

        ParamName(String value) {
            this.value = value;
        }
    }

    /**
     * 通过code获取登录用户的电话
     *
     * @param code 微信code
     * @return 电话号码
     */
    public static String getMobileNumber(String code) {
        String userDetail = getUserDetail(code);

        //获取用户电话
        JSONObject userDetailJson = JSON.parseObject(userDetail);
        //判断是否包含指定参数，若不包含则抛出异常
        if (!userDetailJson.containsKey(ParamName.MOBILE.value)) {
            throw new RuntimeException("微信官方修改了小程序获取用户手机号码相关接口！");
        }
        return userDetailJson.get(ParamName.MOBILE.value).toString();
    }

    /**
     * 通过code获取登录用户信息
     *
     * @param code 微信code
     * @return 电话号码
     */
    public static String getUserDetail(String code) {
        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("请求code不能为空");
        }
        //获取 access_token（允许操作）
        String accessToken = getAccessToken();

        //获取 user_ticket （用户标识）
        String userTicket = getUserTicket(accessToken, code);

        //获取访问用户敏感信息
        return getUserDetail(accessToken, userTicket);
    }

    /**
     * 获取access_token
     *
     * @return access_token
     */
    private static String getAccessToken() {
        String accessTokenUrl = CORP_WECHAT_CODE.ACCESS_TOKEN_URL.value + '?' +
                CORP_WECHAT_CODE.CORP_ID.toParam() + "&" +
                CORP_WECHAT_CODE.CORP_SECRET.toParam();
        return toGet(accessTokenUrl, ParamName.ACCESS_TOKEN.value, "微信官方修改了获取access_token接口！");
    }

    /**
     * GET 获取企业微信用户 user_ticket
     *
     * @param accessToken 请求token
     * @param code        用户code
     * @return 提取的值
     */
    private static String getUserTicket(String accessToken, String code) {
        String url = CORP_WECHAT_CODE.USER_TICKET_URL.value + "?" +
                "access_token=" + accessToken + "&" +
                "code=" + code + "&debug=1";
        HttpResponse response = HttpRequest.get(url).execute();

        logger.info("获取企业微信用户 Ticket：{\"url\":\"{}\",\"data\":{}}", url, response.body());

        //校验错误编码
        checkErrCode(response.body());

        //获取指定值
        JSONObject tokenJson = JSON.parseObject(response.body());
        //若获取成功则判断是否为非本企业用户，若非本企业用户则抛出异常
        if (tokenJson.containsKey(ParamName.EXTERNAL_USERID.value)) {
            throw new RuntimeException("非本企业成员，无法获取用户信息！");
        }

        //结果获取成功则判断是否包含用户票据，不包含则抛出异常
        if (!tokenJson.containsKey(ParamName.USER_TICKET.value)) {
            throw new RuntimeException("微信官方修改了获取访问用户身份接口！");
        }

        return tokenJson.get(ParamName.USER_TICKET.value).toString();
    }

    /**
     * GET 请求数据
     *
     * @param url   请求的url
     * @param value 提取的数据
     * @param error 错误的提示
     * @return 提取的值
     */
    private static String toGet(String url, String value, String error) {
        HttpResponse response = HttpRequest.get(url).execute();

        logger.info("请求数据：{\"url\":\"{}\",\"data\":{}}", url, response.body());

        //校验错误编码
        checkErrCode(response.body());

        //获取指定值
        JSONObject tokenJson = JSON.parseObject(response.body());
        //判断是否包含指定参数，若不包含则抛出异常
        if (!tokenJson.containsKey(value)) {
            throw new RuntimeException(error);
        }
        return tokenJson.get(value).toString();
    }

    /**
     * POST 获取用户敏感信息
     *
     * @param accessToken 请求的token
     * @param userTicket  用户标识
     * @return 提取的值
     */
    private static String getUserDetail(String accessToken, String userTicket) {

        //请求地址
        String url = CORP_WECHAT_CODE.USER_DETAIL_URL.value + "?access_token=" + accessToken;

        //请求包体
        String body = new JSONObject() {{
            put(ParamName.USER_TICKET.value, userTicket);
        }}.toString();

        //请求方式：POST（HTTPS）
        String result = HttpUtil.post(url, body);

        logger.info("获取企业微信用户信息：{\"url\":\"{}\",\"data\":{}}", url, result);

        //校验错误编码
        checkErrCode(result);
        return result;
    }

    /**
     * 校验 errcode 编码
     */
    private static void checkErrCode(String data) {
        //判定非空
        if (null == data || 0 == data.length()) {
            throw new RuntimeException("请求接口无返回数据");
        }

        //将字符串转为json对象
        JSONObject dataJson = JSON.parseObject(data);
        if (!dataJson.containsKey(ParamName.ERR_CODE.value)) {
            throw new RuntimeException("返回数据无错误码");
        }

        //判断编码是否正常
        String errCode = dataJson.get(ParamName.ERR_CODE.value).toString();
        //编码是否存在有效值
        if (null == errCode || 0 == errCode.length()) {
            throw new RuntimeException("错误码无有效值");
        }

        //非正常码
        if (!ParamName.SUCCESS_ERR_CODE.value.equals(errCode)) {
            throw new RuntimeException("请求错误，错误码为:" + errCode);
        }
    }
}
```

