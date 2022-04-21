package be.xentricator.meilisearchdemo.jobs;

import be.xentricator.meilisearchdemo.jobs.models.SyncDto;
import be.xentricator.meilisearchdemo.web.models.ProjectListViewDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Index;
import lombok.SneakyThrows;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class SyncSearchIndexableEntityJob implements InitializingBean, DisposableBean {

    private EntityManager entityManager;

    private final Client meiliClient;
    private final EntityManagerFactory entityManagerFactory;
    private final ObjectMapper objectMapper;

    public SyncSearchIndexableEntityJob(Client meiliClient,
                                        EntityManagerFactory entityManagerFactory,
                                        ObjectMapper objectMapper) {
        this.meiliClient = meiliClient;
        this.entityManagerFactory = entityManagerFactory;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public void sync(SyncDto syncDto) {
        // todo probably want handlers (supports by class type, handle) per entity type injected in this class, and want the actual querying somewhere else
        // for now hardcoded for project

        ProjectListViewDto projectListViewDto = getData(syncDto);
        String indexName = ProjectListViewDto.class.getSimpleName();
        Index index = getIndex(indexName);
        String serializedAsJsonString = serializeDocumentToJson(projectListViewDto);
        try {
            index.addDocuments(serializedAsJsonString, "id");
        } catch (Exception e) {
            int i = 5;
        }
    }

    private String serializeDocumentToJson(ProjectListViewDto projectListViewDto) throws JsonProcessingException {
        List<ProjectListViewDto> toSerialize = List.of(projectListViewDto);
        return objectMapper.writeValueAsString(toSerialize);
    }

    private ProjectListViewDto getData(SyncDto syncDto) {
//        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
//        QProject qProject = QProject.project;
//        QContact qContact = QContact.contact;
//        Expression[] expressions = {qProject.id, qProject.title,
//                qContact.id, qContact.firstName, qContact.lastName, qContact.email};

//        JPAQuery<ProjectListViewDto> query = queryFactory
//                .select(Projections.bean(ProjectListViewDto.class, expressions))
//                .from(qProject)
//                .leftJoin(qContact).on(qProject.contact.id.eq(qContact.id))
//                .where(qProject.id.eq(syncDto.getEntityId()));
//
//        ProjectListViewDto projectListViewDto = query.fetchOne();
        TypedQuery<ProjectListViewDto> q = entityManager.createQuery("SELECT new be.xentricator.meilisearchdemo.web.models.ProjectListViewDto(p.id, p.title, c.id, c.firstName, c.lastName, c.email) FROM Project p inner join Contact c on (p.contact.id=c.id) where p.id = ?1",
                ProjectListViewDto.class);
        q.setParameter(1, syncDto.getEntityId());
        ProjectListViewDto projectListViewDto = q.getSingleResult();
        return projectListViewDto;
    }

    private Index getIndex(String name) throws Exception {
        return meiliClient.index(name);
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
}
