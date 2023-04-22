package ru.zubov.pizzacloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
public class PizzaCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzaCloudApplication.class, args);
    }

    @Bean
    public DataSource dataSource() {
        var dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName();
        dataSource.setUrl();
        dataSource.setUsername();
        dataSource.setPassword();

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
