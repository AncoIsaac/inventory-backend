package com.isaacAnco.inventory.controller.user;

import com.isaacAnco.inventory.dto.user.UpdateUser;
import com.isaacAnco.inventory.dto.user.UserRequestDto;
import com.isaacAnco.inventory.dto.user.UserResponseDto;
import com.isaacAnco.inventory.exception.ResourceNotFoundException;
import com.isaacAnco.inventory.model.user.User;
import com.isaacAnco.inventory.response.CustomApiResponse;
import com.isaacAnco.inventory.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("allUsers")
    @Operation(summary = "Obtener todos los usuarios",
            description = "Obtiene una lista de todos los usuarios registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    @ApiResponse(responseCode = "404", description = "No se encontraron usuarios")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")

    public ResponseEntity<CustomApiResponse> getAllUsers(){
        try {
            List<User> users = userService.getAllUsers();
            List<UserResponseDto> responseDto = users.stream()
                    .map(user -> modelMapper.map(user, UserResponseDto.class))
                    .collect(Collectors.toList());
            return ResponseEntity
                   .status(HttpStatus.OK)
                   .body(new CustomApiResponse("success", responseDto, "users found"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                   .status(HttpStatus.NOT_FOUND)
                   .body(new CustomApiResponse("error", e.getMessage(), "users not found"));
        }
    }

    @GetMapping("getUserById/{id}")
    @Operation(summary = "Obtener un usuario por ID",
            description = "Obtiene un usuario específico por su ID")
    @ApiResponse(responseCode = "200", description = "Usuario obtenido exitosamente")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    public ResponseEntity<CustomApiResponse> getUserById(String id){
        try {
            User user = userService.getUserById(id);
            UserResponseDto responseDto = modelMapper.map(user, UserResponseDto.class);
            return ResponseEntity
                  .status(HttpStatus.OK)
                 .body(new CustomApiResponse("success", responseDto, "user found"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                 .status(HttpStatus.NOT_FOUND)
                 .body(new CustomApiResponse("error", e.getMessage(), "user not found"));
        }
    }

    @PatchMapping("updateUser/{id}")
    @Operation(summary = "Actualizar un usuario",
            description = "Actualiza los datos de un usuario existente")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomApiResponse> updateUser(@PathVariable String id, @RequestBody UpdateUser userResDto){
        try {
            User updateUser = userService.updateUser(id, userResDto);
            UserResponseDto responseDto = modelMapper.map(updateUser, UserResponseDto.class);
            return ResponseEntity
                 .status(HttpStatus.OK)
                 .body(new CustomApiResponse("success", responseDto, "user updated"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CustomApiResponse("error", e.getMessage(), "user updated error"));
        }
    }

}
