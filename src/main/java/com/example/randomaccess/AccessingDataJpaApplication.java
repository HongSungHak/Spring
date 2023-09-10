package com.example.randomaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

public class AccessingDataJpaApp {

    private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApp.class);

    public static void main(String[] agrs) {
        SpringApplication.run(AccessingDataJpaApp.class);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {
        return args -> {
            //아이디 저장
            repository.save(new Customer("Jack", "Bauer"));
            repository.save(new Customer("Chloe", "0 Brian"));
            repository.save(new Customer("Kim", "Bauer"));
            repository.save(new Customer("David", "Palmer"));
            repository.save(new Customer("Michelle", "Dessler"));
            //고객 fetch
            log.info("Customers found with findAll():");
            log.info("----------------------------");
            for (Customer customer : repository.findAll()) {

            }
        };
    }

}
