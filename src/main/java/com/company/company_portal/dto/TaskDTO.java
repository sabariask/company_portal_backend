package com.company.company_portal.dto;


import com.company.company_portal.entity.TaskPriority;
import com.company.company_portal.entity.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private Long id;

    @NotBlank(message = "Task title is required!!")
    private String title;

    private String description;

    @NotNull(message = "Task status is required!!")
    private TaskStatus status;

    @NotNull(message = "Task priority is required!!")
    private TaskPriority priority;

    @NotNull(message = "Project id is required")
    private Long projectId;

    @JsonProperty("assignedUserId")
    private Long assignedUserId;

    private String assignedUserName;
}
