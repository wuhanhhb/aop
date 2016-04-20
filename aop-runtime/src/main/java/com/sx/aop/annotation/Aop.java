package com.sx.aop.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by hebing on 2016/4/20.
 */
@Target({METHOD})
@Retention(CLASS)
public @interface Aop {

}
