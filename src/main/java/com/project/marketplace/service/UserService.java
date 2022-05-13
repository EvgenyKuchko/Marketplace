package com.project.marketplace.service;

import com.project.marketplace.model.User;
import com.project.marketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // saveNewUser
    // loadUserByUsername security
    // updateProfile

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean deleteUser(User user){
        userRepository.delete(user);
        return true;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}