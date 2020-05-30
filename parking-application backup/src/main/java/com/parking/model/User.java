package com.parking.model;

import com.parking.dto.UserDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User {

    public enum Role {
        SUPERVISOR, MANAGER, USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications;

    public User(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.email = userDTO.getEmail();
        this.username = userDTO.getUsername().toLowerCase();
        this.password = userDTO.getPassword();
        this.role = userDTO.getRole();
    }
}