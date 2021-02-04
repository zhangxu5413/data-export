package com.data.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Class Description:
 */
@Configuration//注解到spring容器中
@MapperScan(basePackages = {""},sqlSessionFactoryRef = "oracleSqlSessionFactory")
public class DataSourceOracle {
    /**
     * 返回oracle数据库的数据源
     * @return
     */
    @Bean(name = "oracleSource")
    @ConfigurationProperties(prefix = "spring.datasource.oracle")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 返回oracle数据库的会话工厂
     * @param ds
     * @return
     */
    @Bean(name = "oracleSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("oracleSource") DataSource ds) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(ds);
        return bean.getObject();
    }

    /**
     * 返回oracle数据库的会话模板
     * @param sessionFactory
     * @return
     */
    @Bean(name = "oracleSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("oracleSqlSessionFactory") SqlSessionFactory sessionFactory){
        return new SqlSessionTemplate(sessionFactory);
    }

    @Bean(name = "oracleTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("oracleSource") DataSource ds){
        return new DataSourceTransactionManager(ds);
    }
}
