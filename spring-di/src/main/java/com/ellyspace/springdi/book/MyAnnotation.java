package com.ellyspace.springdi.book;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //기본값은 RetentionPolicy.CLASS
@Target({ElementType.TYPE, ElementType.FIELD}) //이 어노테이션을 어디에 붙일지
@Inherited // 이 어노테이션은 상속이 됩니다. -> Book에 @MyAnnotation을 붙였을 때, Book을 상속받는 MyBook에서도 @MyAnnotation 조회 가능.
public @interface MyAnnotation { //주석이나 다름 없다. (주석보다는 기능이 많음)

    String name() default "yebin"; //이렇게 기본값을 안 주면 사용하는 쪽에서 @MyAnnotation(name="값값") 이렇게 지정해줘야 함.

    int number() default 100;

    String value(); //이건 기본값을 안 주면 사용하는 쪽에서 @MyAnnotation("값값") 이렇게 사용 가능! (1개 뿐일 때)
}