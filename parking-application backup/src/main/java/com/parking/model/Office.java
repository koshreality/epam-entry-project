package com.parking.model;

import com.parking.dto.OfficeDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "offices")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications;

    public Office(OfficeDTO officeDTO) {
        this.title = officeDTO.getTitle();
    }
}
