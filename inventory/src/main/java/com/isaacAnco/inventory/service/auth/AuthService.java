package com.isaacAnco.inventory.service.auth;

import com.isaacAnco.inventory.dto.auth.SignInRequestDto;
import com.isaacAnco.inventory.exception.ResourceNotFoundException;
import com.isaacAnco.inventory.model.user.User;
import com.isaacAnco.inventory.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User signIn(SignInRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found "));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new ResourceNotFoundException("Error code");
        }
        if (!user.getIsActive()){
            throw new ResourceNotFoundException("User is not active");
        }
        return user;
    }

}
