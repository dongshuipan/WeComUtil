package com.example.wechat.annotations;

import java.lang.annotation.*;

/**
 * @author admin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
public @interface Args {
    boolean print() default true;
    int[] filterNums() default {};
}
