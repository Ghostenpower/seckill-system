package com.example.seckill_backend.util.notEmptyString;

import com.auth0.jwt.interfaces.Payload;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 定义注解，适用于字段、方法、参数等
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyStringValidator.class)  // 指定验证器类
public @interface NotEmptyString {

    String message() default "String cannot be empty";  // 默认错误消息

    Class<?>[] groups() default {};  // 用于分组验证

    Class<? extends Payload>[] payload() default {};  // 附加信息
}
