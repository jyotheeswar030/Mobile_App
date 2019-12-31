package com.sjs.services.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sjs.entity.UserEntity;
import com.sjs.exception.UserServiceException;
import com.sjs.model.response.ErrorMessages;
import com.sjs.respository.UserRepository;
import com.sjs.services.UserSevice;
import com.sjs.shared.Util;
import com.sjs.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserSevice {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	Util util;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		if (userRepository.findByEmail(userDto.getEmail()) != null)
			throw new RuntimeException("Record already Avaliable !!!");
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDto, userEntity);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		userEntity.setUserId(util.generateUserID(30));
		UserEntity save = userRepository.save(userEntity);
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(save, returnValue);
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username);
	    if(userEntity ==null) throw new UsernameNotFoundException(username);
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
	    if(userEntity ==null) throw new UsernameNotFoundException(email);
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
	    if(userEntity ==null) throw new UsernameNotFoundException(userId);
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
	    if(userEntity ==null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
	    userEntity.setFirstName(userDto.getFirstName());
	    userEntity.setLastName(userDto.getLastName());
	    UserEntity update = userRepository.save(userEntity);
	    BeanUtils.copyProperties(update, returnValue);
		return returnValue;
	    
	}

}
