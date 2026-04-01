package com.company.company_portal.repository;

import com.company.company_portal.entity.Employee;
import com.company.company_portal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByUser(User user);
}
