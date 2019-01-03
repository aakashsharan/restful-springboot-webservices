package com.aakashsharan.app.controller;

import com.aakashsharan.app.model.request.UserDetailsModel;
import com.aakashsharan.app.model.response.UserRest;
import com.aakashsharan.app.service.UserService;
import com.aakashsharan.app.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest getUser(@PathVariable String id){

        UserRest userRest = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        ModelMapper modelMapper = new ModelMapper();
        userRest = modelMapper.map(userDto, UserRest.class);
        return userRest;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest createUser(@RequestBody UserDetailsModel userDetails) throws Exception{

        UserRest userRest = new UserRest();
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createdUser = userService.createUser(userDto);
        userRest = modelMapper.map(createdUser, UserRest.class);
        return userRest;
    }
}
