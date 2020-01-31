package com.pocketcloud.auth.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Author Zg.Li · 2020/1/26
 */
@Configuration
@MapperScan(basePackages = "com.pocketcloud.auth.mapper", sqlSessionTemplateRef = "pocketCloudSqlSessionTemplate")
@EnableTransactionManagement(proxyTargetClass = true)
public class DataSourceConfiguration {

	@Bean("pocketCloudHikariConfig")
	@ConfigurationProperties(prefix = "pocket.datasource.pocket-cloud")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}

	@Bean("pocketCloudDataSource")
	public DataSource pocketCloudDataSource(@Qualifier("pocketCloudHikariConfig") HikariConfig hikariConfig) {
		return new HikariDataSource(hikariConfig);
	}

	@Bean("pocketCloudSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("pocketCloudDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//		bean.setMapperLocations(
//				resolver.getResource("classpath:mapping/*.xml"));
		return bean.getObject();
	}

	/**
	 * 配置事务管理器
	 */
	@Bean("pocketCloudTransactionManager")
	public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("pocketCloudDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean("pocketCloudSqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("pocketCloudSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
