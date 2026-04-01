package com.company.company_portal.service.impl;


import com.company.company_portal.dto.DepartmentDTO;
import com.company.company_portal.dto.EmployeeDTO;
import com.company.company_portal.entity.Department;
import com.company.company_portal.entity.Employee;
import com.company.company_portal.exception.ResourceNotFoundException;
import com.company.company_portal.repository.DepartmentRepository;
import com.company.company_portal.repository.EmployeeRepository;
import com.company.company_portal.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public EmployeeDTO getById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(("Employee not found !!")));
        return mapToDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAll() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private EmployeeDTO mapToDTO(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .designation(employee.getDesignation())
                .department(
                        DepartmentDTO.builder()
                                .id(employee.getDepartment() != null ? employee.getDepartment().getId() : null)
                                .description(employee.getDepartment() != null ? employee.getDepartment().getDescription() : null)
                                .name(employee.getDepartment() != null ? employee.getDepartment().getName() : null)
                                .build()
                )
                .build();
    }
}
