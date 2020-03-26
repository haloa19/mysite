package com.douzone.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.douzone.mysite.config.web.FileUploadConfig;
import com.douzone.mysite.config.web.MessageConfig;
import com.douzone.mysite.config.web.MvcConfig;
import com.douzone.mysite.config.web.SecurityConfig;

@Configuration
@EnableAspectJAutoProxy  // import하는 클래스에서 별도로 해도 됨
@ComponentScan({"com.douzone.mysite.controller", "com.douzone.mysite.exception"})
@Import({MvcConfig.class, SecurityConfig.class, MessageConfig.class, FileUploadConfig.class})
public class WebConfig {
}
