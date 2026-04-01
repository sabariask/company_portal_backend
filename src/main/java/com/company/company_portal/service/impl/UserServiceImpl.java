package com.company.company_portal.service.impl;


import com.company.company_portal.dto.PagedResponse;
import com.company.company_portal.dto.UserDTO;
import com.company.company_portal.entity.Role;
import com.company.company_portal.entity.User;
import com.company.company_portal.exception.ResourceNotFoundException;
import com.company.company_portal.repository.UserRepository;
import com.company.company_portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(
                user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole());
                    return dto;
                }
        ).toList();
    }

    @Override
    public PagedResponse<UserDTO> getUsers(int page, int size, String sort, String search, String role) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        String sortDirection = sortParams.length > 1 ? sortParams[1] : "asc";

        Sort sortObj = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        PageRequest pageRequest = PageRequest.of(page, size, sortObj);

        Page<User> pageResult;

        if(role != null && !role.isBlank()) {
            Role roleEnum = Role.valueOf(role.toUpperCase());

            pageResult = userRepository.findByRoleAndUsernameContainingIgnoreCaseOrRoleAndEmailContainingIgnoreCase(
                    roleEnum, search,
                    roleEnum, search,
                    pageRequest
            );
        } else if(search !=null && !search.isBlank()) {
            pageResult = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    search,
                    search,
                    pageRequest
            );
        } else {
            pageResult = userRepository.findAll(pageRequest);
        }

        List<UserDTO> users = pageResult.getContent().stream().map(
                user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole());
                    return dto;
                }
        ).toList();

        PagedResponse<UserDTO> response = new PagedResponse<>();
        response.setContent(users);
        response.setPageNumber(pageResult.getNumber());
        response.setPageSize(pageResult.getSize());
        response.setTotalElements(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setLast(pageResult.isLast());

        return response;
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found!!"));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        user.setPassword(dto.getPassword());

        userRepository.save(user);

        dto.setId(user.getId());

        return dto;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
