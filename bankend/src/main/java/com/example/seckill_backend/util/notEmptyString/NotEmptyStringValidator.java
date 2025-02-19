package com.example.seckill_backend.util.notEmptyString;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyStringValidator implements ConstraintValidator<NotEmptyString, String> {

    @Override
    public void initialize(NotEmptyString constraintAnnotation) {
        // 可以初始化一些资源，但在这个简单的案例中不需要
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 如果值为null，认为是有效的
        if (value == null) {
            return true;
        }

        // 如果字符串为空，返回false，表示验证失败
        return !value.isEmpty();
    }
}
