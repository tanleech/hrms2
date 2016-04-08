package sg.edu.ntu.hrms.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:prod.properties" })
@ComponentScan({ "sg.edu.ntu.hrms" })
public class AppConfig {
	   @Autowired
	   private Environment env;
	   
	   @Bean
	   public LocalSessionFactoryBean sessionFactory()  {
	      LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	      sessionFactory.setDataSource(restDataSource());
              
	      sessionFactory.setHibernateProperties(hibernateProperties());
	      sessionFactory.setPackagesToScan("sg.edu.ntu.hrms.dto");
	      return sessionFactory;
	   }
	 
	   @Bean
	   public DataSource restDataSource() {
	      //BasicDataSource dataSource = new BasicDataSource();
              final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
              dsLookup.setResourceRef(true);
              //DataSource dataSource = dsLookup.getDataSource("jdbc/CI6225");
              DataSource dataSource = dsLookup.getDataSource(env.getProperty("jdbc.jndi"));
              return dataSource;              
              //jndi.afterPropertiesSet();
              /*
	      dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	      dataSource.setUrl(env.getProperty("jdbc.url"));
	      dataSource.setUsername(env.getProperty("jdbc.user"));
	      dataSource.setPassword(env.getProperty("jdbc.pass"));
	      */
              //jndi.
	   }
	 
	   @Bean
	   @Autowired
	   public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
	      HibernateTransactionManager txManager = new HibernateTransactionManager();
	      txManager.setSessionFactory(sessionFactory);
	 
	      return txManager;
	   }
	 
	   @Bean
	   public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	      return new PersistenceExceptionTranslationPostProcessor();
	   }
	 
	@SuppressWarnings("serial")
	Properties hibernateProperties() {
	      return new Properties() {
	         {
	           // setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
                     //setProperty("connection.datasource","jdbc/hrms");
	            setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
	           // setProperty("hibernate.globally_quoted_identifiers", "true");
		        setProperty("hibernate.format_sql", "true");
                    setProperty("hibernate.current_session_context_class","thread");
	         }
	      };
	   }

}
