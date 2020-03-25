package com.douzone.mysite.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:com/douzone/mysite/config/app/properties/jdbc.properties")
public class DBConfig {
   
   @Autowired
   private Environment env;      // properties의 값들을 가져와 저장해줌
   
   @Bean
   public DataSource basicDataSource() {
      BasicDataSource basicDataSource = new BasicDataSource();
      
      // getProperty : 기본적으로 string return => 다른 type은 형변환 필요
      basicDataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
      basicDataSource.setUrl(env.getProperty("jdbc.url"));
      basicDataSource.setUsername(env.getProperty("jdbc.username"));
      basicDataSource.setPassword(env.getProperty("jdbc.password"));
      basicDataSource.setInitialSize(env.getProperty("jdbc.initialSize", Integer.class));
      basicDataSource.setMaxActive(env.getProperty("jdbc.maxActive", Integer.class)); 
      
      return basicDataSource;
   }
   
   // DataSource 주입
   @Bean
   public PlatformTransactionManager transactionManager(DataSource dataSource) {
      return new DataSourceTransactionManager(dataSource);
   }
   
}