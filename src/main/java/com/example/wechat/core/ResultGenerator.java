package com.example.wechat.core;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    private static final String DEFAULT_FAIL_MESSAGE = "操作失败";

    public static Result<?> genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result<?> genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result<?> genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }

    public static Result<?> genFailResult(Exception ex) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(ex!=null&&ex.getMessage()!=null?ex.getMessage():DEFAULT_FAIL_MESSAGE);
    }

    public static Result<?> genFailResult() {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(DEFAULT_FAIL_MESSAGE);
    }
    public static Result<?> genFailResult(Object data,String msg) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(msg)
                .setData(data);
    }

    /**
     * 异常返回
     * @param ex
     * @return
     */
    public static Result<?> genFailExceptionResult(Exception ex) {
        return new Result()
                .setCode(ResultCode.INTERNAL_SERVER_ERROR)
                .setMessage(ex.getMessage());
    }
}
