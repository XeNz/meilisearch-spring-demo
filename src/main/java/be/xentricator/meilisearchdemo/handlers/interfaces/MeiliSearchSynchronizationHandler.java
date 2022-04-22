package be.xentricator.meilisearchdemo.handlers.interfaces;

import be.xentricator.meilisearchdemo.jobs.models.SyncDto;

public interface MeiliSearchSynchronizationHandler {
    boolean supports(String className);

    void handle(SyncDto syncDto);
}
