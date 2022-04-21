package be.xentricator.meilisearchdemo.configuration;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeiliSearchConfiguration {
    @Bean
    public com.meilisearch.sdk.Client meiliClient() {
        Config config = new Config("http://localhost:7700", "masterKey");
        return new Client(config);
    }
}
