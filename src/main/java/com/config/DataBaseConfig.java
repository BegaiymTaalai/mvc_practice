package com.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class            DataBaseConfig {

    Environment environment;

    @Bean
    public JpaVendorAdapter vendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    //метод который помогает настраивать конфигурацию с базой данных как PostgreSQL
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("db.driver-classname"));
        dataSource.setUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.user"));
        dataSource.setPassword(environment.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    //Настройка Hibernate
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.put("hibernate.show-sql", environment.getProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2dll.auto", environment.getProperty("hibernate.ddl.auto"));
        return properties;
    }

    @Bean
    //Для получения EntityManager
    public LocalContainerEntityManagerFactoryBean factoryBean() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(vendorAdapter());
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com");
        factoryBean.setJpaProperties(getProperties());
        return factoryBean;
    }

    @Bean
    //Метод который помогает настроить транзакцию в нашей программе
    public PlatformTransactionManager getTransaction() {
        return new JpaTransactionManager(Objects.requireNonNull(factoryBean().getObject()));
    }

}
