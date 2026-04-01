package com.company.company_portal.service.impl;

import com.company.company_portal.dto.DepartmentDTO;
import com.company.company_portal.dto.EmployeeCreateDTO;
import com.company.company_portal.dto.EmployeeDTO;
import com.company.company_portal.dto.EmployeeUpdateDTO;
import com.company.company_portal.entity.Department;
import com.company.company_portal.entity.Employee;
import com.company.company_portal.entity.User;
import com.company.company_portal.exception.ResourceNotFoundException;
import com.company.company_portal.repository.DepartmentRepository;
import com.company.company_portal.repository.EmployeeRepository;
import com.company.company_portal.repository.UserRepository;
import com.company.company_portal.service.AdminEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminEmployeeServiceImpl implements AdminEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    @Override
    public EmployeeDTO create(EmployeeCreateDTO dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found!!"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!!!"));

        Employee employee = Employee.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .designation(dto.getDesignation())
                .department(department)
                .user(user)
                .build();

        Employee saved = employeeRepository.save(employee);

        return mapToDTO(saved);
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeUpdateDTO dto) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not Found!!"));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(()->new ResourceNotFoundException("Department not found!!"));

        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setPhone(dto.getPhone());
        emp.setDesignation(dto.getDesignation());
        emp.setDepartment(department);

        employeeRepository.save(emp);

        return mapToDTO(emp);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
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
