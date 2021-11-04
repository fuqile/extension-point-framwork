package com.springframework.extensionpoint.annotation;

import com.springframework.extensionpoint.stragety.ExceptionStrategy;
import com.springframework.extensionpoint.stragety.ResultStrategy;
import com.springframework.extensionpoint.stragety.RouterStrategy;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ExtensionPoint {
    String code();

    Class<? extends RouterStrategy> routerStrategy();

    Class<? extends ResultStrategy> resultStrategy();

    Class<? extends ExceptionStrategy> exceptionStrategy();
}