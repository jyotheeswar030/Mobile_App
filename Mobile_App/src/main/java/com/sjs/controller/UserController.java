package com.sjs.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjs.exception.UserServiceException;
import com.sjs.model.request.UserDetailsRequestModel;
import com.sjs.model.response.ErrorMessages;
import com.sjs.model.response.OperationStatusModel;
import com.sjs.model.response.RequestOprationStatus;
import com.sjs.model.response.UserRest;
import com.sjs.services.UserSevice;
import com.sjs.shared.dto.UserDto;

@RestController
@RequestMapping("users")
public class UserController {
	@Autowired
	UserSevice userSevice;
	
	@GetMapping(path="/{id}",produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public UserRest getUser(@PathVariable String id) {
		 UserRest returnValue =new UserRest();
		 UserDto createUser=userSevice.getUserByUserId(id);
		 BeanUtils.copyProperties(createUser, returnValue);
		return returnValue;
	}

	@PostMapping(consumes = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
		         produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
	if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
	 UserRest returnValue =new UserRest();
	 UserDto userDto =new UserDto();
	 BeanUtils.copyProperties(userDetails, userDto);
	 UserDto createUser=userSevice.createUser(userDto);
	 BeanUtils.copyProperties(createUser, returnValue);
		return returnValue;
	}

	@PutMapping(path="/{id}",
			consumes = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
	         produces = {MediaType.APPLICATION_ATOM_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}
			)
	public UserRest updateUser(@PathVariable String id,@RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue =new UserRest();
		 UserDto userDto =new UserDto();
		 BeanUtils.copyProperties(userDetails, userDto);
		 UserDto updateUser=userSevice.updateUser(id,userDto);
		 BeanUtils.copyProperties(updateUser, returnValue);
			return returnValue;
	}

	@DeleteMapping
	public OperationStatusModel deleteUser() {
		OperationStatusModel returnValue =new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		returnValue.setOperationResult(RequestOprationStatus.SUCCESS.name());
		
		return returnValue;
	}
}
