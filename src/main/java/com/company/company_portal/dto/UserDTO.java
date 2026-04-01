package com.company.company_portal.dto;


import com.company.company_portal.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Email
    private String email;

    private Role role;
}
