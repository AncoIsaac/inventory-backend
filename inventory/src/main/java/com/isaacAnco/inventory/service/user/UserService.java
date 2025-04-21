package com.isaacAnco.inventory.service.user;

import com.isaacAnco.inventory.dto.user.UpdateUser;
import com.isaacAnco.inventory.dto.user.UserRequestDto;
import com.isaacAnco.inventory.model.user.User;

import java.util.List;

public class UserService implements IUserService {

    @Override
    public User createUser(UserRequestDto userRequestDto) {
        return null;
    }

    @Override
    public List<User> getAllUser() {
        return List.of();
    }

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public User updateUser(String id, UpdateUser updateUser) {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }
}
