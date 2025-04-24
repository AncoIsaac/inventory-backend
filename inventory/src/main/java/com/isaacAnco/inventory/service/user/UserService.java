package com.isaacAnco.inventory.service.user;

import com.isaacAnco.inventory.dto.user.UpdateUser;
import com.isaacAnco.inventory.dto.user.UserRequestDto;
import com.isaacAnco.inventory.exception.ResourceNotFoundException;
import com.isaacAnco.inventory.model.user.User;
import com.isaacAnco.inventory.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<User> getAllUsers() {
        if (userRepository.findAll().isEmpty()){
            throw new ResourceNotFoundException("The list is empty");
        }
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The user not exist")
        );
    }

    @Override
    public User updateUser(String id, UpdateUser updateUser) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The user not exist")
        );
        System.out.println(updateUser.getEmail());

        if (updateUser.getEmail() != null) {
            if (user.getEmail().equals(updateUser.getEmail())) {
                throw new ResourceNotFoundException("The email is already register");    
            }
        }

        user.setIsActive(updateUser.getIsActive());

        if (updateUser.getUserName() != null) {
            user.setUserName(updateUser.getUserName());
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The user not exist")
        );
        user.setIsActive(false);
        userRepository.save(user);
    }

    @Override
    public Page<User> getUsersByPage(Pageable pageable, String search) {
        if (search != null && !search.isEmpty()) {
            return userRepository.findAllWithSearch(search, pageable);
        }
        return userRepository.findAll(pageable);
    }
}
