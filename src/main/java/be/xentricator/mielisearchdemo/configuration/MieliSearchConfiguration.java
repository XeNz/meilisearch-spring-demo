package be.xentricator.mielisearchdemo.configuration;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MieliSearchConfiguration {
    @Bean
    public com.meilisearch.sdk.Client mieliClient() {
        Config config = new Config("http://localhost:7700", "masterKey");
        return new Client(config);
    }
}
