package com.micro.auth_server.services;

import com.micro.auth_server.dto.TokenDto;
import com.micro.auth_server.dto.UserDto;

public interface AuthService {
    TokenDto login(UserDto user);
    TokenDto validateToken(TokenDto token);
}
