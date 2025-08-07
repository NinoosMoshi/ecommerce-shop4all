package com.ninos.user.service;

import com.ninos.user.dtos.UserDTO;
import com.ninos.user.entity.User;
import com.ninos.user.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        boolean existUser = userRepository.existsByEmail(userDTO.getEmail());
        if(existUser){
            throw new EntityExistsException("User with email " + userDTO.getEmail() + " already exists");
        }
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long userId) {
        User existUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        existUser.setFirstName(userDTO.getFirstName());
        existUser.setLastName(userDTO.getLastName());
        User savedUser = userRepository.save(existUser);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        userRepository.delete(user);
    }
}
