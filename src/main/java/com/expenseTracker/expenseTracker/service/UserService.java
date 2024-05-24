package com.expenseTracker.expenseTracker.service;

import com.expenseTracker.expenseTracker.entity.User;
import com.expenseTracker.expenseTracker.exception.ItemAlreadyExistException;
import com.expenseTracker.expenseTracker.exception.ResourceNotFoundException;
import com.expenseTracker.expenseTracker.model.UserModel;
import com.expenseTracker.expenseTracker.repository.UserRepository;
import com.fasterxml.jackson.databind.util.BeanUtil;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserModel userModel) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(userModel.getEmail()))) {
            throw new ItemAlreadyExistException("Email already exist : " + userModel.getEmail());
        }
        User user = new User();
        BeanUtils.copyProperties(userModel, user);
        return userRepository.save(user);
    }

    public User readUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User is not present with id : " + id));
    }

    public void deleteUser(Long id) {
        User user = readUser(id);
        userRepository.delete(user);
    }

    public User updateUser(UserModel userModel, Long id) {
        User existingUser = readUser(id);
        existingUser.setName(userModel.getName()!=null ? userModel.getName() : existingUser.getName());
        existingUser.setAge(userModel.getAge()!=null ? userModel.getAge() : existingUser.getAge());
        existingUser.setEmail(userModel.getEmail()!=null ? userModel.getEmail() : existingUser.getEmail());
        existingUser.setPassword(userModel.getPassword()!=null ? userModel.getPassword() :
                           existingUser.getPassword());
        return userRepository.save(existingUser);
    }
}
