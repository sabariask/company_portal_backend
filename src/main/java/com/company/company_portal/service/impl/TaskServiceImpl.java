package com.company.company_portal.service.impl;


import com.company.company_portal.dto.TaskDTO;
import com.company.company_portal.entity.*;
import com.company.company_portal.exception.ResourceNotFoundException;
import com.company.company_portal.repository.EmployeeRepository;
import com.company.company_portal.repository.ProjectRepository;
import com.company.company_portal.repository.TaskRepository;
import com.company.company_portal.repository.UserRepository;
import com.company.company_portal.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public TaskDTO create(TaskDTO dto) {
        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project id is not found!!"));

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus() == null ? TaskStatus.TODO : dto.getStatus())
                .priority(dto.getPriority())
                .project(project)
                .build();

        if (dto.getAssignedUserId() != null) {
            Employee user = employeeRepository.findById(dto.getAssignedUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found!!"));
            task.setAssignedUser(user);
        }

        Task saved = taskRepository.save(task);

        return mapToDTO(saved);
    }

    @Override
    public List<TaskDTO> getAll() {
        return taskRepository.findAll()
                .stream()
                .map(task -> TaskDTO.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .status(task.getStatus())
                        .priority(task.getPriority())
                        .projectId(task.getProject().getId())
                        .build())
                .toList();
    }

    @Override
    public TaskDTO getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task id is not found!!"));

        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .projectId(task.getProject().getId())
                .build();
    }

    @Override
    public TaskDTO update(Long id, TaskDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if (dto.getProjectId() != null) {
            Project project = projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
            task.setProject(project);
        }
        if (dto.getTitle() != null)
            task.setTitle(dto.getTitle());

        if (dto.getDescription() != null)
            task.setDescription(dto.getDescription());

        if (dto.getPriority() != null)
            task.setPriority(dto.getPriority());

        if (dto.getStatus() != null)
            task.setStatus(dto.getStatus());
        else if (task.getStatus() == null)
            task.setStatus(TaskStatus.TODO);

        if (dto.getAssignedUserId() != null) {
            Employee assignedUser = employeeRepository.findById(dto.getAssignedUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found!!!"));
            task.setAssignedUser(assignedUser);
        }
        taskRepository.save(task);
        return mapToDTO(task);
    }


    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDTO> getAllForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!!"));

        boolean isAdminOrManager = user.getRole() == Role.ADMIN || user.getRole() == Role.MANAGER;
        List<Task> tasks;
        if (isAdminOrManager) {
            tasks = taskRepository.findAll();
        } else {
            Employee employee = employeeRepository.findByUser(user).orElseThrow((() -> new ResourceNotFoundException("Employee not Found!!")));

            tasks = taskRepository.findByAssignedUser(employee);
        }

        return tasks.stream().map(this::mapToDTO).toList();
    }

    private TaskDTO mapToDTO(Task task) {
        TaskDTO dto = TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .projectId(task.getProject().getId())
                .build();
        if (task.getAssignedUser() != null) {
            dto.setAssignedUserId(task.getAssignedUser().getId());
            dto.setAssignedUserName(task.getAssignedUser().getName());
        }

        return dto;
    }

    @Override
    public TaskDTO updateStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task Id not Found!!"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User current = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found"));

        boolean adminOrManager = current.getRole() == Role.ADMIN || current.getRole() == Role.MANAGER;

        if (!adminOrManager) {
            if (task.getAssignedUser() == null || !task.getAssignedUser().getId().equals(current.getId())) {
                throw new AccessDeniedException("You can only update your own tasks");
            }
        }

        task.setStatus(status);
        taskRepository.save(task);

        return mapToDTO(task);
    }

    @Override
    public List<TaskDTO> getByProject(Long projectId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!!"));

        if (user.getRole() == Role.EMPLOYEE) {
            return taskRepository.findByProjectIdAndAssignedUserId(projectId, user.getId())
                    .stream()
                    .map(this::mapToDTO)
                    .toList();

        }

        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }
}
