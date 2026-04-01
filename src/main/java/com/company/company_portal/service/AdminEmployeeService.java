package com.company.company_portal.service;

import com.company.company_portal.dto.EmployeeCreateDTO;
import com.company.company_portal.dto.EmployeeDTO;
import com.company.company_portal.dto.EmployeeUpdateDTO;

public interface AdminEmployeeService {

    EmployeeDTO create(EmployeeCreateDTO dto);

    EmployeeDTO update(Long id, EmployeeUpdateDTO dto);

    void delete(Long id);
}
