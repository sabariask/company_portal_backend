package com.company.company_portal.service;

import com.company.company_portal.dto.PagedResponse;
import com.company.company_portal.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    PagedResponse<UserDTO> getUsers(int page, int size, String sort, String search, String role);

    UserDTO update(Long id, UserDTO dto);

    void delete(Long id);
}
