package damowang.base.annotation;

import java.lang.annotation.*;

/**
 * 接口必须登录的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface Login {

    String value() default "";
}
