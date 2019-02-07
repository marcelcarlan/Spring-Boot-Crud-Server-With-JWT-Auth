package com.mobileserver.demo.service;

import com.mobileserver.demo.domain.Role;
import com.mobileserver.demo.domain.User;
import com.mobileserver.demo.repo.RoleRepository;
import com.mobileserver.demo.repo.UserRepository;
import com.mobileserver.demo.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    private JwtProvider jwtProvider;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                       RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        Role role = new Role();
        role.setRoleName("ROLE_USER");
        this.roleRepository.save(role);
    }

    @PostConstruct
    private void initDatabase() {
        Optional<Role> role = roleRepository.findByRoleName("ROLE_USER");
        User saveUser = new User("marcel", passwordEncoder.encode("password"), role.get());
        role.get().getUsers().add(saveUser);
        userRepository.save(saveUser);
    }

    public Optional<String> signin(String username, String password) {
        LOGGER.info("New user attempting to sign in");
        Optional<String> token = Optional.empty();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
            } catch (AuthenticationException e) {
                LOGGER.info("Log in failed for user {}", username);
            }
        }
        return token;
    }

    public Optional<User> signup(String username, String password, String firstName, String lastName) {
        LOGGER.info("New user attempting to sign in");
        Optional<User> user = Optional.empty();
        if (!userRepository.findByUsername(username).isPresent()) {
            Optional<Role> role = roleRepository.findByRoleName("ROLE_USER");

            User saveUser = new User(username, passwordEncoder.encode(password), role.get());
            role.get().getUsers().add(saveUser);
            user = Optional.of(userRepository.save(saveUser));

        }
        return user;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}