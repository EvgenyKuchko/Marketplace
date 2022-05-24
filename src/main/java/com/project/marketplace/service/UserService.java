package com.project.marketplace.service;

import com.project.marketplace.exception.EmailException;
import com.project.marketplace.exception.NicknameException;
import com.project.marketplace.exception.PasswordException;
import com.project.marketplace.model.Role;
import com.project.marketplace.model.User;
import com.project.marketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${upload.path}")
    private String path;

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
    public boolean saveNewUser(User user) throws Exception {
        boolean isUserExist = userRepository.existsByEmail(user.getEmail());
        boolean isNicknameExist = userRepository.existsByNickname(user.getNickname());
        boolean isPasswordEquals = user.getPassword().equals(user.getConfirmPassword());
        if (isUserExist) {
            throw new EmailException("The email already exists");
        } else if (isNicknameExist) {
            throw new NicknameException("The nickname already exists");
        } else if (!isPasswordEquals) {
            throw new PasswordException("The passwords don't match");
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Transactional
    public void updateProfile(User user, MultipartFile multipartFile) throws IOException {
        User userFromDB = userRepository.findByNickname(user.getNickname());
        if (multipartFile != null) {
            if (userFromDB.getProfilePicture() != null) {
                File lastPic = new File(userFromDB.getProfilePicture());
                boolean isDeleted = lastPic.delete();
                if(!isDeleted) {
                    throw new IOException("Delete error");
                }
                userRepository.deleteProfilePicture(userFromDB.getNickname());
            }
            String fileName = path + user.getNickname() + "-profile-picture" + ".png";
            multipartFile.transferTo(new File(fileName));
            user.setProfilePicture(fileName);
            userRepository.updateProfilePicture(fileName, user.getNickname());
        }
        if (!user.getUserDescription().equals(userFromDB.getUserDescription())) {
            userRepository.updateDescription(user.getUserDescription(), user.getNickname());
        }
        if (user.getWallet() != userFromDB.getWallet()) {
            userRepository.updateWallet(user.getWallet(), user.getNickname());
        }
    }
}