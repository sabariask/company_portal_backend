package com.company.company_portal.service.impl;


import com.company.company_portal.dto.DepartmentDTO;
import com.company.company_portal.dto.ProjectDTO;
import com.company.company_portal.entity.Department;
import com.company.company_portal.entity.Project;
import com.company.company_portal.exception.BadRequestException;
import com.company.company_portal.exception.ResourceAlreadyExistsException;
import com.company.company_portal.exception.ResourceNotFoundException;
import com.company.company_portal.repository.DepartmentRepository;
import com.company.company_portal.repository.ProjectRepository;
import com.company.company_portal.repository.TaskRepository;
import com.company.company_portal.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final DepartmentRepository departmentRepository;
    private final TaskRepository taskRepository;

    @Override
    public ProjectDTO create(ProjectDTO dto) {

        if (projectRepository.existsByName(dto.getName())) {
            throw new ResourceAlreadyExistsException("Project Already exists!!");
        }

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found!!"));

        Project project = Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .department(department)
                .build();

        Project saved = projectRepository.save(project);

        dto.setId(saved.getId());

        return mapToDTO(saved);
    }

    @Override
    public List<ProjectDTO> getAll() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public ProjectDTO getById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found!!"));

        return mapToDTO(project);
    }

    @Override
    public ProjectDTO update(Long id, ProjectDTO dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found!!"));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found!!!"));

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setDepartment(department);

        Project updated = projectRepository.save(project);

        dto.setId(project.getId());
        return mapToDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Project project = projectRepository.findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("Project not found!!"));

        if(taskRepository.existsByProjectId(id)) {
            throw new BadRequestException(
                    "Cannot delete project. Tasks are still assigned to this project."
            );
        }
        projectRepository.deleteById(id);
    }

    private ProjectDTO mapToDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .department(
                        DepartmentDTO.builder()
                                .id(project.getDepartment() != null ? project.getDepartment().getId() : null)
                                .name(project.getDepartment() != null ? project.getDepartment().getName() : null)
                                .description(project.getDepartment() != null ? project.getDepartment().getDescription() : null)
                                .build()
                ).build();
    }
}
