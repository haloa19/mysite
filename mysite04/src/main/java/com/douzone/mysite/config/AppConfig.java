package com.douzone.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.douzone.mysite.config.app.DBConfig;
import com.douzone.mysite.config.app.MyBatisConfig;

@Configuration
@EnableAspectJAutoProxy  // @aspect proxy만드는 역할
@ComponentScan({"com.douzone.mysite.service","com.douzone.mysite.repository", "com.douzone.mysite.aspect"})
@Import({DBConfig.class, MyBatisConfig.class})  // DBConig와 MyBatisConfig를 한번에 작성해도 됨
public class AppConfig {
}
