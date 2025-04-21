package com.isaacAnco.inventory.service.user;

import com.isaacAnco.inventory.dto.user.UpdateUser;
import com.isaacAnco.inventory.dto.user.UserRequestDto;
import com.isaacAnco.inventory.model.user.User;

import java.util.List;

public interface IUserService {
    User createUser(UserRequestDto userRequestDto);
    List<User> getAllUser();
    User getUserById(String id);
    User updateUser(String id, UpdateUser updateUser);
    void deleteUser(String id);
}
