package be.xentricator.meilisearchdemo.dal.interfaces;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class SearchEngineIndexableEntity {
    protected Long id;

    public Long getId() {
        return id;
    }

    private LocalDateTime lastSyncDate;

    public LocalDateTime getLastSyncDate() {
        return lastSyncDate;
    }

    public void setLastSyncDate(LocalDateTime lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }

    private UUID lastSyncVersion;

    public UUID getLastSyncVersion() {
        return lastSyncVersion;
    }

    public void setLastSyncVersion(UUID lastSyncVersion) {
        this.lastSyncVersion = lastSyncVersion;
    }
}
