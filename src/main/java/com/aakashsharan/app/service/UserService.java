package com.aakashsharan.app.service;

import com.aakashsharan.app.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto getUserByUserId(String userId);
    UserDto createUser(UserDto user);
}
