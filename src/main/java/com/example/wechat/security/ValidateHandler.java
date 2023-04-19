package com.example.wechat.security;

import com.example.wechat.core.Result;
import com.example.wechat.core.ResultGenerator;
import com.example.wechat.core.ServiceException;
import com.example.wechat.util.LogUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @author admin
 */
@RestControllerAdvice
public class ValidateHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        StringBuilder errorInfo = new StringBuilder();
        BindingResult bindingResult = exception.getBindingResult();
        for (int i = 0; i < bindingResult.getFieldErrors().size(); i++) {
            if (i > 0) {
                errorInfo.append(",");
            }
            FieldError fieldError = bindingResult.getFieldErrors().get(i);
            errorInfo.append(fieldError.getField()).append(" :").append(fieldError.getDefaultMessage());
        }

        //返回BaseResponse

        return ResultGenerator.genFailResult(errorInfo.toString());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleConstraintViolationException(ConstraintViolationException exception) {
        StringBuilder errorInfo = new StringBuilder();
        String errorMessage;

        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        for (ConstraintViolation<?> item : violations) {
            errorInfo.append(item.getMessage()).append(",");
        }
        errorMessage = errorInfo.toString().substring(0, errorInfo.toString().length() - 1);

        return ResultGenerator.genFailResult(errorMessage);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleDefaultException(Exception exception) {
        LogUtils.error("捕获全局异常：", exception);
        return ResultGenerator.genFailResult("其他错误");
    }

    /**
     * 接收服务抛出异常并进行处理
     *
     * @param serviceException 服务异常
     * @return 处理结果
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleDefaultException(ServiceException serviceException) {
        return ResultGenerator.genFailResult(serviceException.getMessage());
    }
}
