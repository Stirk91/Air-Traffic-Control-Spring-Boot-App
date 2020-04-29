package com.example.atc.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.crypto.Data;

// Hikari
// http://zetcode.com/springboot/datasourcebuilder/
// baeldung.com/spring-boot-hikari // don't need to add to pom.xml in Spring Boot 2


@Configuration
public class PostgresDatasource {

    @Bean
    @ConfigurationProperties("app.datasource")
    public HikariDataSource hikariDataSource() {
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();
    }

}
