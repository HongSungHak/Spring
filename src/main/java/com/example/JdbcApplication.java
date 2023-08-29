package com.example;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


class Customer {
    private long id;
    private String firstName, lastName;

    public Customer(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
public class JdbcApplication {

    @Bean
    ApplicationRunner run(JdbcTemplate jdbcTemplate) {
        return args -> {
            jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
            jdbcTemplate.execute("CREATE TABLE customers(" +
                    "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255)");
            //읽기 전용
            List<Object[]> names = List.of("John woo", "jeff dean", "josh bloch", "josh long")
                    .stream()
                    .map(name -> name.split(" "))
                    .collect(Collectors.toList());

            jdbcTemplate.batchUpdate("INSERT INTO customers(frist_name, last_name) VALUES (?, ?)", names);

            jdbcTemplate.query("SELECT id, first_name, last_name FROM customer WHERE first_name = ?", new Object[]{"josh"},
                    (rs, rowNum) -> new Customer(rs.getLong("id")
                            , rs.getString("frist_name")
                            , rs.getString("last_name")))
                            .forEach(customer -> System.out.println("customer = " + customer));
        };
    }


    public static void main(String[] args) {

    }
}
