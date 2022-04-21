package be.xentricator.meilisearchdemo.web;

import be.xentricator.meilisearchdemo.services.ProjectService;
import be.xentricator.meilisearchdemo.web.models.ProjectDto;
import be.xentricator.meilisearchdemo.web.models.ProjectListViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class MeiliSearchController {

    private final ProjectService projectService;

    @PostMapping("/project")
    ResponseEntity<ProjectDto> createProjectWithAuthor(@RequestBody ProjectDto projectDto) {
        ProjectDto project = projectService.createProject(projectDto);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/project/list")
    ResponseEntity<List<ProjectListViewDto>> createProjectWithAuthor() throws Exception {
        List<ProjectListViewDto> dtos = projectService.list();
        return ResponseEntity.ok(dtos);
    }
}
