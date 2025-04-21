package com.isaacAnco.inventory.service.auth;

import com.isaacAnco.inventory.dto.auth.SignInRequestDto;
import com.isaacAnco.inventory.model.user.User;

public interface IAuthService {
    User signIn(SignInRequestDto signInRequestDto);
}
