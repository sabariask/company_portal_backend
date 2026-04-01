package com.company.company_portal.service;

import com.company.company_portal.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO create(ProjectDTO dto);

    List<ProjectDTO> getAll();

    ProjectDTO getById(Long id);

    ProjectDTO update(Long id, ProjectDTO dto);

    void delete(Long id);
}
