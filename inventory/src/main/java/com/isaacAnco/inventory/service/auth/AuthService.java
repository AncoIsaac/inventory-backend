package com.isaacAnco.inventory.service.auth;

import com.isaacAnco.inventory.dto.auth.SignInRequestDto;
import com.isaacAnco.inventory.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    @Override
    public User signIn(SignInRequestDto signInRequestDto) {
        return null;
    }

}
