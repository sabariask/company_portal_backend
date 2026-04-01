package com.company.company_portal.service;

import com.company.company_portal.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO getById(Long id);

    List<EmployeeDTO> getAll();

}
