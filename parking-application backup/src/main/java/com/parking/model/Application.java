package com.parking.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "applications")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Application {

    public enum Status {
        OPEN, APPROVED, CLOSED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "office_id", referencedColumnName = "id", nullable = false)
    private Office office;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Status status;

    @ManyToOne()
    @JoinColumn(name = "updated_by_user_id", referencedColumnName = "id", nullable = false)
    private User updatedBy;

    public Application(Office office, User user, Status status, User updatedBy){
        this.office = office;
        this.user = user;
        this.status = status;
        this.updatedBy = updatedBy;
    }
}
