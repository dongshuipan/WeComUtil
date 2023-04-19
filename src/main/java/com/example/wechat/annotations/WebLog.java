package com.example.wechat.annotations;

import java.lang.annotation.*;

/**
 * 定义执行接口及方法的时间
 *
 * @author admin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface WebLog {

    Args args() default @Args(print = true);

    /**
     * 是否打印返回数据
     */
    boolean response() default false;

    /**
     * 是否打印错误堆栈
     */
    boolean exception() default false;

    /**
     * 形容
     */
    String des() default "";
}
