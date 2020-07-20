package com.macro.mall.tiny.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis的配置类
 */

@Configuration
//@MapperScan可以指定要扫描的Mapper类的包的路径
@MapperScan({"com.macro.mall.tiny.mbg.mapper","com.macro.mall.tiny.dao"})
public class MyBatisConfig {
}
