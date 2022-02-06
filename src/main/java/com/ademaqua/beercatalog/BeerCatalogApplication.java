package com.ademaqua.beercatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BeerCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeerCatalogApplication.class, args);
    }

}
