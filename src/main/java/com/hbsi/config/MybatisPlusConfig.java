package com.hbsi.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;


/**
 * mp 配置
 */
@Configuration
@MapperScan(basePackages= {"com.hbsi.*.mapper","com.hbsi.properties.*.mapper"})
public class MybatisPlusConfig {

    /**
     * .分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
