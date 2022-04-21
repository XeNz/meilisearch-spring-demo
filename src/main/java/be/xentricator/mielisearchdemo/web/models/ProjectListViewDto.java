package be.xentricator.mielisearchdemo.web.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
public class ProjectListViewDto implements Serializable {
    public ProjectListViewDto(Long id,
                              String title,
                              Long contactId,
                              String contactFirstName,
                              String contactLastName,
                              String contactEmail) {
        this.id = id;
        this.title = title;
        this.contactId = contactId;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.contactEmail = contactEmail;
    }

    private Long id;
    private String title;
    private Long contactId;
    private String contactFirstName;
    private String contactLastName;
    private String contactEmail;
}
