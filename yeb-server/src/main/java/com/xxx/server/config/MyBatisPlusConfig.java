package com.xxx.server.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Keafmd
 *
 * @ClassName: MybatisPlusConfig
 * @Description: MyBatis分页配置
 * @author: liuchen
 * @date: 2022/6/17 16:58
 * @Blog:
 */
@Configuration
public class MyBatisPlusConfig {
    /**
     * mybatis-plus3.4.0版本后的分页插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;

    }
}
