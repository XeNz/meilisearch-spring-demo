package be.xentricator.meilisearchdemo.services;

import be.xentricator.meilisearchdemo.dal.Contact;
import be.xentricator.meilisearchdemo.dal.Project;
import be.xentricator.meilisearchdemo.dal.ProjectRepository;
import be.xentricator.meilisearchdemo.web.models.ContactDto;
import be.xentricator.meilisearchdemo.web.models.ProjectDto;
import be.xentricator.meilisearchdemo.web.models.ProjectListViewDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Index;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final Client meiliClient;
    private final ObjectMapper objectMapper;

    public ProjectDto createProject(ProjectDto projectDto) {
        Project project = mapToEntity(projectDto);
        project = projectRepository.save(project);

        return mapFromEntity(project);
    }

    private static Project mapToEntity(ProjectDto projectDto) {
        return Project
                .builder()
                .title(projectDto.getTitle())
                .contact(Contact.
                        builder()
                        .firstName(projectDto.getContact().getFirstName())
                        .lastName(projectDto.getContact().getLastName())
                        .email(projectDto.getContact().getEmail())
                        .build())
                .build();
    }

    private static ProjectDto mapFromEntity(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .title(project.getTitle())
                .contact(ContactDto
                        .builder()
                        .id(project.getContact().getId())
                        .email(project.getContact().getEmail())
                        .firstName(project.getContact().getFirstName())
                        .lastName(project.getContact().getLastName())
                        .build())
                .build();
    }

    public List<ProjectListViewDto> list() throws Exception {
        Index index = meiliClient.getIndex(ProjectListViewDto.class.getSimpleName());
        String documents = index.getDocuments();
        return objectMapper.readValue(documents, objectMapper.getTypeFactory().constructCollectionType(List.class, ProjectListViewDto.class));
    }
}
