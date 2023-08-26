package com.example;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Map;

@SpringBootApplication
//@EnableScheduling //추가해야 스케줄 어노테이션이 적용
public class RestServiceApplication {

    @Bean
    ApplicationRunner init(ErApi api) {
        //환율 정보를 가져오는 Api
        // https://open.er-api.com/v6/latest
        return  args -> {
            RestTemplate rt = new RestTemplate();
            Map<String, Map<String, Double>> res = rt.getForObject("https://open.er-api.com/v6/latest", Map.class);
            System.out.println("res = " + res.get("rates").get("KRW"));

            //webClient
            WebClient client = WebClient.create("https://open.er-api.com");
            Map<String, Map<String, Double>> res2 = client.get().uri("/v6/latest").retrieve().bodyToMono(Map.class).block();
            System.out.println("res2 = " + res2.get("rates").get("KRW"));

            HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                    .builder(WebClientAdapter.forClient(client))
                    .build();
            ErApi erApi = httpServiceProxyFactory.createClient(ErApi.class);
            Map<String, Map<String, Double>> res3 = erApi.getLatest();
            System.out.println("res3 = " + res3.get("rates").get("KRW"));

            Map<String, Map<String, Double>> res4 = api.getLatest();
            System.out.println("res4 = " + res4.get("rates").get("KRW"));
        };
    }

    //프록시를 활용한 방법
    @Bean
    ErApi erApi() {
        WebClient client = WebClient.create("https://open.er-api.com");
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .build();
        return httpServiceProxyFactory.createClient(ErApi.class);
    }
    interface ErApi {
        @GetExchange("/v6/latest") //@HttpExchange(method = "GET") 갖고 있음
        Map getLatest();
    }

   /* @Bean
    ApplicationRunner run(RestTemplate restTemplate)  {
        return args -> {
            restTemplate.getForObject("http://localhost:8080/apu/random", Quote.class);
        };
    }*/

    @Bean //레스트 템플릿은 빈으로 등록 후 주입 받아 사용 하는 것을 권장
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

}
