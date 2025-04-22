package com.isaacAnco.inventory.controller.auth;

import com.isaacAnco.inventory.dto.auth.AuthResponseDto;
import com.isaacAnco.inventory.dto.auth.SignInRequestDto;
import com.isaacAnco.inventory.exception.ResourceNotFoundException;
import com.isaacAnco.inventory.response.CustomApiResponse;
import com.isaacAnco.inventory.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
@Tag(name = "Auth", description = "auth operation")
@SecurityRequirements
public class AuthController {

    private final AuthService authService;
    private final ModelMapper modelMapper;

    @PostMapping("signIn")
    @Operation(summary = "Login user")
    @ApiResponse(responseCode = "201", description = "inicio de seccion correctamente")
    @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
    private ResponseEntity<CustomApiResponse> SignIn(@RequestBody SignInRequestDto request){
        try {
            AuthResponseDto signIn = authService.signIn(request);
            AuthResponseDto responseAuth = modelMapper.map(signIn, AuthResponseDto.class);
            return ResponseEntity.ok(new CustomApiResponse("success", responseAuth, "Login success"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CustomApiResponse("error", e.getMessage(), "Error login"));
        }
    }
}
