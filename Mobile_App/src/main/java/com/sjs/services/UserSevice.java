package com.sjs.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sjs.shared.dto.UserDto;

public interface UserSevice extends UserDetailsService{
UserDto createUser(UserDto userDto);
UserDto getUser(String email);
UserDto getUserByUserId(String userId);
UserDto updateUser(String userId,UserDto userDto);
}
