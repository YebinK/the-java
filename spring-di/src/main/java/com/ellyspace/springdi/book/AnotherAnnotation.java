package com.ellyspace.springdi.book;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AnotherAnnotation {
    String value();
}
