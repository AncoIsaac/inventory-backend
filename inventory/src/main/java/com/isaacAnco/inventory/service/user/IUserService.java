package com.isaacAnco.inventory.service.user;

import com.isaacAnco.inventory.dto.user.UpdateUser;
import com.isaacAnco.inventory.dto.user.UserRequestDto;
import com.isaacAnco.inventory.model.user.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    User createUser(UserRequestDto userRequestDto);
    List<User> getAllUsers();
    User getUserById(String id);
    User updateUser(String id, UpdateUser updateUser);
    void deleteUser(String id);
    Page<User> getUsersByPage(Pageable pageable, String search);
}
