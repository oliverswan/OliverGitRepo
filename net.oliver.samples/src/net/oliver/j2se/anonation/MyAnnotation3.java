package net.oliver.j2se.anonation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)// 表示这个标注在运行时，也是生效的
public @interface MyAnnotation3 {
    public String value();
    public String[] multiValues();
    int number() default 0;
}

