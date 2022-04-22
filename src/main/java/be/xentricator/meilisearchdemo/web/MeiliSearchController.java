package be.xentricator.meilisearchdemo.web;

import be.xentricator.meilisearchdemo.services.ProjectService;
import be.xentricator.meilisearchdemo.web.models.ProjectDto;
import be.xentricator.meilisearchdemo.web.models.ProjectListViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    ResponseEntity<List<ProjectListViewDto>> getProjects(Integer page, Integer pageSize) throws Exception {
        Pageable pageable = getPageable(page, pageSize);
        List<ProjectListViewDto> response = projectService.list(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/project/search")
    ResponseEntity<List<ProjectListViewDto>> searchProjects(Integer page, Integer pageSize, String searchTerm) throws Exception {
        Pageable pageable = getPageable(page, pageSize);
        List<ProjectListViewDto> response = projectService.search(pageable, searchTerm);
        return ResponseEntity.ok(response);
    }

    private Pageable getPageable(Integer page, Integer pageSize) {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 100;
        }
        return PageRequest.of(page, pageSize);
    }
}
