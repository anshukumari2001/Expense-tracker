package com.expenseTracker.expenseTracker.service;

import com.expenseTracker.expenseTracker.entity.User;
import com.expenseTracker.expenseTracker.exception.ItemAlreadyExistException;
import com.expenseTracker.expenseTracker.exception.ResourceNotFoundException;
import com.expenseTracker.expenseTracker.model.UserModel;
import com.expenseTracker.expenseTracker.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserModel userModel) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(userModel.getEmail()))) {
            throw new ItemAlreadyExistException("Email already exist : " + userModel.getEmail());
        }
        User user = new User();
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        BeanUtils.copyProperties(userModel, user);
        return userRepository.save(user);
    }

    public User readUser() {
        Long id= getLogdInUser().getId();
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User is not present with id : " + id));
    }

    public void deleteUser() {
        User user = readUser();
        userRepository.delete(user);
    }

    public User updateUser(UserModel userModel) {
        User existingUser = readUser();
        existingUser.setName(userModel.getName()!=null ? userModel.getName() : existingUser.getName());
        existingUser.setAge(userModel.getAge()!=null ? userModel.getAge() : existingUser.getAge());
        existingUser.setEmail(userModel.getEmail()!=null ? userModel.getEmail() : existingUser.getEmail());
        existingUser.setPassword(userModel.getPassword()!=null ? passwordEncoder.encode(userModel.getPassword()) : existingUser.getPassword());
        return userRepository.save(existingUser);
    }

    public User getLogdInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(
            "User not present : "+email));
    }
}
