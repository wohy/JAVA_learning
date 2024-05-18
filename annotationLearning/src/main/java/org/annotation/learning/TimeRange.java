package org.annotation.learning;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeRange {
    int MaxHour() default 23;
    int MaxMinute() default 59;
    int SmallHour() default 0;
    int SmallMinute() default 0;
}
