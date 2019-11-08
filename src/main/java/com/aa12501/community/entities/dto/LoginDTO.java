package com.aa12501.community.entities.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer userId;
    private String email;
    private String password;
}
