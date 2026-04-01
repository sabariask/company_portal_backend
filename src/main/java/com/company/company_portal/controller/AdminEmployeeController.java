package com.company.company_portal.controller;


import com.company.company_portal.dto.EmployeeCreateDTO;
import com.company.company_portal.dto.EmployeeDTO;
import com.company.company_portal.dto.EmployeeUpdateDTO;
import com.company.company_portal.service.AdminEmployeeService;
import com.company.company_portal.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/employees")
@RequiredArgsConstructor
public class AdminEmployeeController {
    private final AdminEmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeCreateDTO dto) {
        return ResponseEntity.ok(employeeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @RequestBody EmployeeUpdateDTO dto) {
        return ResponseEntity.ok(employeeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok("Employee deleted successfully!!");
    }
}
