package net.oliver.j2se.anonation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)// ��ʾ�����ע������ʱ��Ҳ����Ч��
public @interface MyAnnotation3 {
    public String value();
    public String[] multiValues();
    int number() default 0;
}

