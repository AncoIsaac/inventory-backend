package com.isaacAnco.inventory.service.auth;

import com.isaacAnco.inventory.dto.auth.AuthResponseDto;
import com.isaacAnco.inventory.dto.auth.SignInRequestDto;

public interface IAuthService {
    AuthResponseDto signIn(SignInRequestDto signInRequestDto);
}
