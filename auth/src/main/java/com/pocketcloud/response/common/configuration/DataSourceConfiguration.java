package com.pocketcloud.response.common.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author Zg.Li · 2020/1/26
 */
@Configuration
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
}
