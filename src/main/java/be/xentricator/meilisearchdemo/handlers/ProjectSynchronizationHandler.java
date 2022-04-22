package be.xentricator.meilisearchdemo.handlers;

import be.xentricator.meilisearchdemo.dal.Project;
import be.xentricator.meilisearchdemo.handlers.interfaces.MeiliSearchSynchronizationHandler;
import be.xentricator.meilisearchdemo.jobs.models.SyncDto;
import be.xentricator.meilisearchdemo.web.models.ProjectListViewDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Index;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jobrunr.jobs.context.JobRunrDashboardLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectSynchronizationHandler implements MeiliSearchSynchronizationHandler, InitializingBean, DisposableBean {

    private final Logger log = new JobRunrDashboardLogger(LoggerFactory.getLogger(ProjectSynchronizationHandler.class));
    private EntityManager entityManager;

    private final Client meiliClient;
    private final EntityManagerFactory entityManagerFactory;
    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(String className) {
        return Project.class.getName().equals(className);
    }

    @SneakyThrows
    @Override
    public void handle(SyncDto syncDto) {

        ProjectListViewDto projectListViewDto = getData(syncDto);
        String indexName = ProjectListViewDto.class.getSimpleName();
        Index index = getIndex(indexName);
        String serializedAsJsonString = serializeDocumentToJson(projectListViewDto);
        try {
            log.info("Adding ProjectListViewDto document with id {}", projectListViewDto.getId());
            index.addDocuments(serializedAsJsonString, "id");
        } catch (Exception e) {
            log.error("Something went wrong trying to add data to index {}", indexName, e);
            throw e;
        }
    }


    @Override
    public void afterPropertiesSet() {
        if (entityManager == null) {
            this.entityManager = SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory, null, true);
        }
    }

    @Override
    public void destroy() {
        if (entityManager != null) {
            entityManager.close();
        }
    }

    private String serializeDocumentToJson(ProjectListViewDto projectListViewDto) throws JsonProcessingException {
        List<ProjectListViewDto> toSerialize = List.of(projectListViewDto);
        return objectMapper.writeValueAsString(toSerialize);
    }

    private ProjectListViewDto getData(SyncDto syncDto) {
        TypedQuery<ProjectListViewDto> q = entityManager.createQuery("SELECT new be.xentricator.meilisearchdemo.web.models.ProjectListViewDto(p.id, p.title, c.id, c.firstName, c.lastName, c.email) " +
                        "FROM Project p inner join Contact c on (p.contact.id=c.id) " +
                        "where p.id = ?1",
                ProjectListViewDto.class);

        q.setParameter(1, syncDto.getEntityId());
        return q.getSingleResult();
    }

    private Index getIndex(String name) throws Exception {
        return meiliClient.index(name);
    }
}
