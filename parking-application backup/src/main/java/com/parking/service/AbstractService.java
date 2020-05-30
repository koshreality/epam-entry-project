package com.parking.service;

import com.parking.dto.UserDTO;
import com.parking.model.User;
import com.parking.repository.UserRepository;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractService {

    protected UserRepository userRepository;

    protected UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AbstractAuthenticationToken) {
            UserDTO user = new UserDTO();
            user.setUsername(authentication.getName());
            authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(User.Role::valueOf)
                    .forEach(user::setRole);
            return user;
        } return null;
    }
}
