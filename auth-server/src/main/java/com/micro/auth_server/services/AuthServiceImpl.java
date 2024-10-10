package com.micro.auth_server.services;

import com.micro.auth_server.dto.TokenDto;
import com.micro.auth_server.dto.UserDto;
import com.micro.auth_server.entitiees.UserEntity;
import com.micro.auth_server.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Transactional
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private static final String USER_EXCEPTION_MSG = "User authentication failed";

    @Override
    public TokenDto login(UserDto user){
        return  null;
    }

    @Override
    public TokenDto validateToken(TokenDto token){
        return null;
    }

    private void validPassword(UserDto userDto, UserEntity userEntity){
        if(!passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MSG);
        }
    }
}
