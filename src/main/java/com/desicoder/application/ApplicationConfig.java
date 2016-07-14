package com.desicoder.application;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.desicoder.model.DomesticBeer;
import com.desicoder.model.User;
import com.desicoder.model.UserPermission;
import com.desicoder.model.UserRole;


@Configuration
@ComponentScan(basePackages = "com.desicoder", excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@EnableTransactionManagement(proxyTargetClass=true)
@PropertySource("classpath:jdbc.properties")
public class ApplicationConfig implements TransactionManagementConfigurer{
    
	@Autowired
    Environment env;
	
	@Bean(destroyMethod="close")
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("driverClass"));
		dataSource.setUrl(env.getProperty("jdbcUrl"));
		dataSource.setUsername(env.getProperty("user"));
		dataSource.setPassword(env.getProperty("password"));
		return dataSource;
	}
	
	@Bean(destroyMethod="close")
	SessionFactory sessionFactory() throws Exception{
		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
		bean.setDataSource(dataSource());
		bean.setAnnotatedClasses(new Class[]{DomesticBeer.class, User.class, UserPermission.class, UserRole.class});
		bean.setPackagesToScan(new String[]{"com.desicoder"});
//		bean.setExposeTransactionAwareSessionFactory(false);
		Properties hibernateProperties = getHibernateProperties();
		bean.setHibernateProperties(hibernateProperties);
		bean.afterPropertiesSet();
		return bean.getObject();
		
	}
	

	private Properties getHibernateProperties() {
		Properties hibernateProperties = new Properties();
//		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
		hibernateProperties.put("hibernate.show_sq", true);
		hibernateProperties.put("hibernate.connection.autocommit", false);
//		hibernateProperties.put("hibernate.current_session_context_class", "thread");
		hibernateProperties.put("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
		hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
		return hibernateProperties;
	}
	
	
	@Bean
	public HibernateTransactionManager transactionManager() throws Exception {
	    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	    transactionManager.setSessionFactory(sessionFactory());
	    transactionManager.setDataSource(dataSource());
	    return transactionManager;
	}
	
	 public PlatformTransactionManager annotationDrivenTransactionManager() {
	         try {
				return transactionManager();
			} catch (Exception e) {
				throw new RuntimeException("No Valide Transaction manager found", e);
			}
	     }

	

}
