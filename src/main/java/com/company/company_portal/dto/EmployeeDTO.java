package com.company.company_portal.dto;


import com.company.company_portal.entity.Department;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String designation;

    private DepartmentDTO department;
}
