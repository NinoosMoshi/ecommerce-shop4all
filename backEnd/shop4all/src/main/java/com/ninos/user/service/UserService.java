package com.ninos.user.service;

import com.ninos.user.dtos.UserDTO;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO, Long userId);
    UserDTO getUserById(Long userId);
    void deleteUser(Long userId);

}
