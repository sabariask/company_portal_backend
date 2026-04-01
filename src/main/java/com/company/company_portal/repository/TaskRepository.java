package com.company.company_portal.repository;

import com.company.company_portal.entity.Employee;
import com.company.company_portal.entity.Task;
import com.company.company_portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedUser(Employee employee);

    boolean existsByProjectId(Long projectId);

    List<Task> findByProjectIdAndAssignedUserId(Long projectId, Long id);

    List<Task> findByProjectId(Long id);
}
