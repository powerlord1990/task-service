package ru.company.taskmanagement.common.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
