package be.xentricator.meilisearchdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
public class MeilisearchDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeilisearchDemoApplication.class, args);
    }

}
