package com.company.company_portal.controller;


import com.company.company_portal.dto.PagedResponse;
import com.company.company_portal.dto.UserDTO;
import com.company.company_portal.repository.UserRepository;
import com.company.company_portal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users")
    public PagedResponse<UserDTO> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String sort,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role
    ) {
        return userService.getUsers(page, size, sort, search, role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@RequestParam Long id, @Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@RequestParam Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully!!");
    }
}
