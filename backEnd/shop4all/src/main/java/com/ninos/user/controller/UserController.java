package com.ninos.user.controller;

import com.ninos.response.ApiResponse;
import com.ninos.user.dtos.UserDTO;
import com.ninos.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(new ApiResponse("Found!", user));
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.createUser(userDTO);
        return ResponseEntity.ok(new ApiResponse("Create User successfully!", user));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserDTO userDTO, @PathVariable Long userId) {
        UserDTO user = userService.updateUser(userDTO, userId);
        return ResponseEntity.ok(new ApiResponse("Update User successfully!", user));
    }


    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse("Delete User successfully!", null));
    }


}
