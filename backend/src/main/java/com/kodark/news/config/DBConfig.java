package com.kodark.news.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/*
 * title : 데이터베이스 환경
 * dec : db 드라이버, url, username, password를 세팅하고 datasource를 만든다.
 * 작성자 : 류제욱
 * 작성일 : 2020-01-06
 */
@Configuration
@PropertySource("classpath:DB.properties")
public class DBConfig {

	@Autowired
    private Environment env;	 

    @Bean//(destroyMethod = "close")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;

    }

}
