package com.company.company_portal.service.impl;


import com.company.company_portal.dto.DepartmentDTO;
import com.company.company_portal.entity.Department;
import com.company.company_portal.exception.ResourceAlreadyExistsException;
import com.company.company_portal.exception.ResourceNotFoundException;
import com.company.company_portal.repository.DepartmentRepository;
import com.company.company_portal.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO create(DepartmentDTO dto) {
        if(departmentRepository.existsByName(dto.getName())) {
            throw new ResourceAlreadyExistsException("Department already exists!!");
        }

        Department department = Department.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        Department saved = departmentRepository.save(department);
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public List<DepartmentDTO> getAll() {
        return departmentRepository.findAll()
                .stream()
                .map(dep -> DepartmentDTO.builder()
                        .id(dep.getId())
                        .name(dep.getName())
                        .description(dep.getDescription())
                        .build())
                .toList();
    }

    @Override
    public DepartmentDTO getById(Long id) {
        Department dep = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found!!"));

        return DepartmentDTO.builder()
                .id(dep.getId())
                .name(dep.getName())
                .description(dep.getDescription())
                .build();
    }

    @Override
    public DepartmentDTO update(Long id, DepartmentDTO dto) {
        Department dep = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found!!"));

        dep.setName(dep.getName());
        dep.setDescription(dto.getDescription());

        departmentRepository.save(dep);

        dep.setId(dep.getId());
        return dto;
    }

    @Override
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }
}
