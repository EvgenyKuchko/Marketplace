package com.project.marketplace.service;

import com.project.marketplace.model.Role;
import com.project.marketplace.model.User;
import com.project.marketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }

    @Transactional
    public boolean saveNewUser(User user) {
        boolean isUserExist = userRepository.existsByEmail(user.getEmail());
        boolean isNicknameExist = userRepository.existsByNickname(user.getNickname());
        boolean isPasswordEquals = user.getPassword().equals(user.getConfirmPassword());
        if (isUserExist) {
            return false;
        } else if (isNicknameExist) {
            return false;
        } else if (!isPasswordEquals) {
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    // updateProfile

    @Transactional
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public boolean deleteUser(User user) {
        userRepository.delete(user);
        return true;
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }
}