package com.andrzej.bookDatabase.Service;

import com.andrzej.bookDatabase.Model.Role;
import com.andrzej.bookDatabase.Model.User;
import com.andrzej.bookDatabase.Repository.RoleRepository;
import com.andrzej.bookDatabase.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        List<Role> userRole = List.of(roleRepository.findByRole("USER"));
        user.setRoles(userRole);
        return userRepository.save(user);
    }
}
