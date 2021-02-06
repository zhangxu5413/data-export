package com.data.config;

import javax.sql.DataSource;

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

@Configuration
@MapperScan(basePackages = "com.data.dao.postgre", sqlSessionFactoryRef = "postgreSqlSessionFactory")
public class DataSourcePG {
	
	@Bean(name = "postgreDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.postgre")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "postgreSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("postgreDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean(name = "postgreSqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("postgreSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	@Bean(name = "postgreTransactionManager")
	public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("postgreDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
