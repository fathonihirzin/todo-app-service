package com.todo.app.service.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.todo.app.service.mapper", sqlSessionTemplateRef = "postgresSqlSessionTemplate")
@Slf4j
public class PostgresDataSourceMapperConfig {
    private static final String POSTGRES_MAPPER_LOCATION = "classpath:com/todo/app/service/mapper/*.xml";

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Primary
    public SqlSessionFactory postgresSqlSessionFactory(@Qualifier("postgresDataSource") DataSource dataSource) {
        try {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(POSTGRES_MAPPER_LOCATION));

            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
            if (sqlSessionFactory != null) {
                sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
                sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
            }

            return sqlSessionFactory;
        }  catch (Exception e) {
            log.error("Error creating postgresSqlSessionFactory bean: {}", e.getMessage());

            return null;
        }
    }

    @Bean
    @Primary
    public SqlSessionTemplate postgresSqlSessionTemplate(@Qualifier("postgresSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Primary
    public DataSourceTransactionManager postgresTransactionManager(@Qualifier("postgresDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
