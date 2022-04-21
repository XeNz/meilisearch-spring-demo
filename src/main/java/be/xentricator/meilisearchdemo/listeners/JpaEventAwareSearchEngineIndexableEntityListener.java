package be.xentricator.meilisearchdemo.listeners;

import be.xentricator.meilisearchdemo.dal.interfaces.SearchEngineIndexableEntity;
import be.xentricator.meilisearchdemo.jobs.SyncSearchIndexableEntityJob;
import be.xentricator.meilisearchdemo.jobs.models.SyncDto;
import be.xentricator.meilisearchdemo.jobs.models.SyncOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JpaEventAwareSearchEngineIndexableEntityListener {
    private static Log log = LogFactory.getLog(JpaEventAwareSearchEngineIndexableEntityListener.class);

    @Autowired
    private JobScheduler jobScheduler;

    @PostPersist
    private void afterCreate(SearchEngineIndexableEntity entity) {
        log.info("Create complete for entity: " + entity.getId());
        SyncDto dto = new SyncDto(generateSyncIdentifier(), SyncOperation.CREATE, entity.getId(), entity.getClass().getName());
        queueJob(dto);
    }

    @PostUpdate
    private void afterUpdate(SearchEngineIndexableEntity entity) {
        log.info("Update complete for entity: " + entity.getId());
        SyncDto dto = new SyncDto(generateSyncIdentifier(), SyncOperation.UPDATE, entity.getId(), entity.getClass().getName());
        queueJob(dto);
    }

    @PostRemove
    private void afterDelete(SearchEngineIndexableEntity entity) {
        log.info("Delete complete for entity: " + entity.getId());
        SyncDto dto = new SyncDto(generateSyncIdentifier(), SyncOperation.CREATE, entity.getId(), entity.getClass().getName());
        queueJob(dto);
    }

    private UUID generateSyncIdentifier() {
        return UUID.randomUUID();
    }

    private void queueJob(SyncDto dto) {
        jobScheduler.<SyncSearchIndexableEntityJob>enqueue((job) -> job.sync(dto));
    }
}
