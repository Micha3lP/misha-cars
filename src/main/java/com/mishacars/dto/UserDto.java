package com.mishacars.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private String username;
    private String password;
    private String email;
    private String rol;
    private boolean locked;
    private boolean disabled;


}
