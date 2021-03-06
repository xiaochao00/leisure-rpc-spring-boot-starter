package child.leisure.rpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 被该注解标记的服务可以提供远程RPC访问的功能
 * 注册服务，会把该标记的服务注册到对应的注册中心上
 *
 * @author shichao
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {
    String value() default "";
}
