package ont.paarma.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

//This declares it as a Spring configuration class
@Configuration
//This enables Spring's ability to receive and process web requests
@EnableWebMvc
//This scans the com.springcookbook.controller package for Spring components
@ComponentScan(basePackages = "ont.paarma")

public class AppConfig {
	 @Bean
	    public ViewResolver viewResolver() {
	        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	        viewResolver.setViewClass(JstlView.class);
	        viewResolver.setPrefix("/WEB-INF/views/");
	        viewResolver.setSuffix(".jsp");
	 
	        return viewResolver;
	    }
	 
	 @Bean
	 public DataSource dataSource() {
	 DriverManagerDataSource dataSource = new
	 DriverManagerDataSource();
	 dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	 dataSource.setUrl("jdbc:mysql://localhost:3306/db1");
	 dataSource.setUsername("user1");
	 dataSource.setPassword("pass1");
	 return dataSource;
	 }
	 
	 @Bean
	 public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	 return new JdbcTemplate(dataSource);
	 }
}