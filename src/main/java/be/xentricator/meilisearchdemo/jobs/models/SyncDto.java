package be.xentricator.meilisearchdemo.jobs.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncDto implements Serializable {
    private UUID syncIdentifier;
    private SyncOperation syncOperation;
    private Long entityId;
    private String entityClassName;

    @Override
    public String toString() {
        return "SyncDto{" +
                "syncIdentifier=" + syncIdentifier +
                ", syncOperation=" + syncOperation +
                ", entityId=" + entityId +
                ", entityClassName='" + entityClassName + '\'' +
                '}';
    }
}
