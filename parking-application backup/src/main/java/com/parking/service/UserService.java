package com.parking.service;

import com.parking.model.User;
import com.parking.dto.UserDTO;
import com.parking.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService extends AbstractService {

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {
        return new UserDTO(userRepository.save(new User(userDTO)));
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {

        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getUserByName(String name) {
        return userRepository.findByName(name).stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(UserDTO::new);
    }

    public Optional<UserDTO> updateUser(Long id, UserDTO updateUserData) {
        return userRepository.findById(id).map(foundUser -> {
            if (updateUserData.getName() != null)
                foundUser.setName(updateUserData.getName());
            if (updateUserData.getUsername() != null)
                foundUser.setUsername(updateUserData.getUsername().toLowerCase());
            if (updateUserData.getEmail() != null)
                foundUser.setEmail(updateUserData.getEmail());
            if (updateUserData.getPassword() != null)
                foundUser.setPassword(updateUserData.getPassword());
            if (updateUserData.getRole() != null)
                foundUser.setRole(updateUserData.getRole());
            return new UserDTO(userRepository.save(foundUser));
        });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
}
