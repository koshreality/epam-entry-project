package com.parking.dto;

import com.parking.model.User;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private String username;

    private String password;

    private User.Role role;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}
