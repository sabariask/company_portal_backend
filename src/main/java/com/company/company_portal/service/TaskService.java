package com.company.company_portal.service;

import com.company.company_portal.dto.TaskDTO;
import com.company.company_portal.entity.TaskStatus;

import java.util.List;

public interface TaskService {
    TaskDTO create(TaskDTO dto);

    List<TaskDTO> getAll();

    TaskDTO getById(Long id);

    TaskDTO update(Long id, TaskDTO dto);

    void delete(Long id);

    List<TaskDTO> getAllForCurrentUser();

    TaskDTO updateStatus(Long taskId, TaskStatus status);

    List<TaskDTO> getByProject(Long id);
}
