package com.example.onlineExam.configuration;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
public class DataSourceConfiguration {
	@Autowired
	private Environment env;

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource source = new DriverManagerDataSource();
		source.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		source.setUrl(env.getProperty("spring.datasource.url"));
		source.setUsername(env.getProperty("spring.datasource.username"));
		source.setPassword(env.getProperty("spring.datasource.password"));
		return source;
	}

	@Bean
	public SessionFactory getSessionFactory() throws IOException {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(getDataSource());
		factoryBean.setPackagesToScan("com.example.onlineExam.entities");
		
		Properties props = new Properties();
		props.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
		props.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
		props.setProperty("hibernate.current_session_context_class",
				env.getProperty("spring.jpa.properties.hibernate.current-session-context-class"));
		
		factoryBean.setHibernateProperties(props);
		factoryBean.afterPropertiesSet();
		SessionFactory sessionFactory = factoryBean.getObject();
		return sessionFactory;
	}
}
