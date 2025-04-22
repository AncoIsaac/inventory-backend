package com.isaacAnco.inventory.service.user;

import com.isaacAnco.inventory.dto.user.UpdateUser;
import com.isaacAnco.inventory.dto.user.UserRequestDto;
import com.isaacAnco.inventory.exception.ResourceNotFoundException;
import com.isaacAnco.inventory.model.user.User;
import com.isaacAnco.inventory.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())){
            throw new ResourceNotFoundException("The email is already register");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserName(request.getUserName());

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return List.of();
    }

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public User updateUser(String id, UpdateUser updateUser) {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }
}
