package io.kimmking.rpcfx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RPC 服务提供注解  标明需要注册到ZK
 */
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcProvider {

    /**
     * 版本若无 配置默认为 v:-1
     * @return
     */
    String version() default "v:-1";


}
