package be.xentricator.meilisearchdemo.configuration;

import org.jobrunr.configuration.JobRunr;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.server.BackgroundJobServerConfiguration;
import org.jobrunr.server.JobActivator;
import org.jobrunr.storage.sql.common.SqlStorageProviderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JobRunrConfiguration {
    @Bean
    public JobScheduler initJobRunr(DataSource dataSource, JobActivator jobActivator) {
        return JobRunr.configure()
                .useJobActivator(jobActivator)
                .useStorageProvider(SqlStorageProviderFactory
                        .using(dataSource))
                .useBackgroundJobServer(BackgroundJobServerConfiguration.usingStandardBackgroundJobServerConfiguration().andPollIntervalInSeconds(5))
                .useDashboard()
                .initialize()
                .getJobScheduler();
    }
}
