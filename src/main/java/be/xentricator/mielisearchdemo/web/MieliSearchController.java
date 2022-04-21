package be.xentricator.mielisearchdemo.web;

import be.xentricator.mielisearchdemo.services.ProjectService;
import be.xentricator.mielisearchdemo.web.models.ProjectDto;
import be.xentricator.mielisearchdemo.web.models.ProjectListViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class MieliSearchController {

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
