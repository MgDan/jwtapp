package org.dandroid.jwtapp.service;

import jakarta.inject.Inject;
import org.dandroid.jwtapp.entity.User;
import org.dandroid.jwtapp.repository.UserRepository;

public class AuthService {

    @Inject
    private UserRepository userRepository;

    public boolean validateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

}
