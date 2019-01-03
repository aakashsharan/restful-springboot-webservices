package com.aakashsharan.app.service.impl;

import com.aakashsharan.app.entity.UserEntity;
import com.aakashsharan.app.exceptions.UserServiceException;
import com.aakashsharan.app.repository.UserRepository;
import com.aakashsharan.app.service.UserService;
import com.aakashsharan.app.shared.Utility;
import com.aakashsharan.app.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utility utils;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto userDto = new UserDto();
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException("UserId: "+userId + " not found");
        BeanUtils.copyProperties(userEntity, userDto);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email + "not found");

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), userEntity.getEmailVerificationStatus(), true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto createUser(UserDto user) {
        if(userRepository.findByEmail(user.getEmail()) != null) throw new UserServiceException("User already exists!");

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity userDetails = userRepository.save(userEntity);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        return userDto;
    }
}
