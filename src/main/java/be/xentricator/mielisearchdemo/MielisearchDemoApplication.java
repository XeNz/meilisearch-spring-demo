package be.xentricator.mielisearchdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableWebMvc
public class MielisearchDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MielisearchDemoApplication.class, args);
    }

}
