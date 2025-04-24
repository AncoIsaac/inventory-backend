package com.isaacAnco.inventory.controller.user;

import com.isaacAnco.inventory.dto.user.UpdateUser;
import com.isaacAnco.inventory.dto.user.UserRequestDto;
import com.isaacAnco.inventory.dto.user.UserResponseDto;
import com.isaacAnco.inventory.exception.ResourceNotFoundException;
import com.isaacAnco.inventory.model.user.User;
import com.isaacAnco.inventory.response.CustomApiResponse;
import com.isaacAnco.inventory.response.CustomApiResponsePagination;
import com.isaacAnco.inventory.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
@Tag(name = "Users", description = "User management operations")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping()
    @Operation(summary = "Create a new user",
    description = "Register a new user in the system")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid user data")
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
    @Operation(summary = "Get all users",
    description = "Retrieves a list of all registered users in the system")
    @ApiResponse(responseCode = "200", description = "Users list retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No users found")
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
    @Operation(summary = "Get user by ID",
            description = "Retrieves a specific user by their ID")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    public ResponseEntity<CustomApiResponse> getUserById(@PathVariable String id){
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

    @GetMapping("Pagination")
    @Operation(summary = "Get paginated users",
            description = "Retrieves a paginated list of users with optional search")
    @ApiResponse(responseCode = "200", description = "Users list retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No users found")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    public ResponseEntity<CustomApiResponsePagination> getUsersByPage(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "") String search) {
    
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> usersPage = userService.getUsersByPage(pageable, search);
        
        List<UserResponseDto> userDtos = usersPage.getContent().stream()
            .map(user -> modelMapper.map(user, UserResponseDto.class))
            .collect(Collectors.toList());
         
        return ResponseEntity.ok(new CustomApiResponsePagination(
            "success",
            usersPage.getTotalElements(),
            usersPage.getSize(),
            usersPage.getTotalPages(),
            usersPage.getNumber() + 1,
            userDtos, "users found" ));
    }

    @PatchMapping("updateUser/{id}")
    @Operation(summary = "Update user",
            description = "Updates an existing user's data")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid user data")
    @ApiResponse(responseCode = "404", description = "User not found")
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

    @DeleteMapping("deleteUser/{id}")
    @Operation(summary = "Delete user",
            description = "Deletes a user from the system")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomApiResponse> deleteUser(@PathVariable String id){
        try {
            userService.deleteUser(id);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CustomApiResponse("success", null, "user deleted"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
               .status(HttpStatus.NOT_FOUND)
               .body(new CustomApiResponse("error", e.getMessage(), "user deleted error"));
        }
    }
}
