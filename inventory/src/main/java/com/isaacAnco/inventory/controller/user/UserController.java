package com.isaacAnco.inventory.controller.user;

import com.isaacAnco.inventory.dto.user.UserRequestDto;
import com.isaacAnco.inventory.dto.user.UserResponseDto;
import com.isaacAnco.inventory.exception.ResourceNotFoundException;
import com.isaacAnco.inventory.model.user.User;
import com.isaacAnco.inventory.response.CustomApiResponse;
import com.isaacAnco.inventory.service.DtoConverter;
import com.isaacAnco.inventory.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("${api.prefix}/users")
@Tag(name = "Users", description = "User-realted operation")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping()
    @Operation(summary = "Crear un nuevo usuario",
            description = "Registra un nuevo usuario en el sistema")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
    public ResponseEntity<CustomApiResponse> createUser(@RequestBody UserRequestDto userResDto){
        try {
            User newUser = userService.createUser(userResDto);
            UserResponseDto responseDto = modelMapper.map(newUser, UserResponseDto.class);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new CustomApiResponse("succes", responseDto, "user created succesfully"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CustomApiResponse("error", e.getMessage(), "user created error"));
        }
    }
}
