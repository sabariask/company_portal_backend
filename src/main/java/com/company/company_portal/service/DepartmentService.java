package com.company.company_portal.service;


import com.company.company_portal.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO create(DepartmentDTO dto);

    List<DepartmentDTO> getAll();

    DepartmentDTO getById(Long id);

    DepartmentDTO update(Long id, DepartmentDTO dto);

    void delete(Long id);
}
