package be.xentricator.meilisearchdemo.jobs;

import be.xentricator.meilisearchdemo.handlers.interfaces.MeiliSearchSynchronizationHandler;
import be.xentricator.meilisearchdemo.jobs.models.SyncDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncSearchIndexableEntityJob {

    private final List<MeiliSearchSynchronizationHandler> synchronizationHandlers;

    @SneakyThrows
    @Job(name = "SyncSearchIndexableEntityJob for %1", retries = 0)
    public void sync(SyncDto syncDto) {
        for (MeiliSearchSynchronizationHandler synchronizationHandler : synchronizationHandlers) {
            boolean supports = synchronizationHandler.supports(syncDto.getEntityClassName());

            if (supports) {
                synchronizationHandler.handle(syncDto);
            }
        }
    }
}
