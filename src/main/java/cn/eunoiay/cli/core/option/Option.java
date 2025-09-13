package cn.eunoiay.cli.core.option;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Option {
    
    String[] names() default {};
    
    String defaultValue() default "";
    
    /**
     * 参数数量: 0=无参数, 1=单参数
     */
    int arity() default 1;
    
}
