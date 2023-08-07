package com.example;

import com.example.consumrestful.Quote;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling //추가해야 스케줄 어노테이션이 적용
public class RestServiceApplication {

    @Bean
    ApplicationRunner run(RestTemplate restTemplate)  {
        return args -> {
            restTemplate.getForObject("http://localhost:8080/apu/random", Quote.class);
        };
    }

    @Bean //레스트 템플릿은 빈으로 등록후 주입 받아 사용하는 것을 권장
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

}
